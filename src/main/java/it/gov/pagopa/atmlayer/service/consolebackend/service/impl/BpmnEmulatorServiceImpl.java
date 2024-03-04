package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.BpmnEmulatorWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BpmnEmulatorService;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BpmnService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@ApplicationScoped
@Slf4j
public class BpmnEmulatorServiceImpl implements BpmnEmulatorService {

    static final BankConfigTripletDto defaultTripletAssociation = new BankConfigTripletDto("06789", "12345", "87654321");

    @Inject
    @RestClient
    BpmnEmulatorWebClient bpmnEmulatorWebClient;

    @Inject
    BpmnService bpmnService;

    @Override
    public Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(int pageIndex, int pageSize, String functionType, String modelVersion, String definitionVersionCamunda, UUID bpmnId, UUID deploymentId, String camundaDefinitionId, String definitionKey, String deployedFileName, String resource, String sha256, StatusEnum status, String acquirerId, String branchId, String terminalId, String fileName) {
        return bpmnEmulatorWebClient.getBpmnFiltered(pageIndex, pageSize, functionType, modelVersion, definitionVersionCamunda, bpmnId, deploymentId, camundaDefinitionId, definitionKey, deployedFileName, resource, sha256, status, acquirerId, branchId, terminalId, fileName);
    }

    @Override
    public Uni<BpmnDTO> createBpmn(BpmnCreationDto bpmnCreationDto) {
        return bpmnEmulatorWebClient.createBpmn(bpmnCreationDto);
    }

    @Override
    public Uni<BpmnBankConfigDTO> addSingleAssociation(UUID bpmnId, Long version, BankConfigTripletDto bankConfigTripletDto) {
        return bpmnEmulatorWebClient.addSingleAssociation(bpmnId, version, bankConfigTripletDto);
    }

    @Override
    public Uni<BpmnBankConfigDTO> replaceSingleAssociation(UUID bpmnId, Long version, BankConfigTripletDto bankConfigTripletDto) {
        return bpmnEmulatorWebClient.replaceSingleAssociation(bpmnId, version, bankConfigTripletDto);
    }

    @Override
    public Uni<BpmnDTO> deployBPMN(UUID uuid, Long version) {
        return bpmnEmulatorWebClient.deployBPMN(uuid, version);
    }

    @Override
    public Uni<FileS3Dto> downloadBpmnFrontEnd(UUID bpmnId, Long version) {
        return bpmnEmulatorWebClient.downloadBpmnFrontEnd(bpmnId, version);
    }

    @Override
    public Uni<BpmnBankConfigDTO> deployEmulatorBpmn(BpmEmulatorUpgradeDto dto){
        return getBpmnFiltered(0, 1, null, dto.getVersion().toString(), null, dto.getUuid(),
                        null, null,null,null,null,null,null,null,null,null,null)
                .onItem()
                .transformToUni(results -> {
                    if(results.getItemsFound() == 0){
                        return bpmnService.downloadBpmnFrontEnd(dto.getUuid(), dto.getVersion())
                                .onItem()
                                .transformToUni(downloadedFile -> {
                                    byte[] filebyte = Base64.getDecoder().decode(downloadedFile.getFileContent());
                                    File file = null;
                                    try {
                                        file = File.createTempFile("tempFile", ".bpmn");
                                        FileOutputStream fos = new FileOutputStream(file);
                                        fos.write(filebyte);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                    BpmnCreationDto newFileBpmn = new BpmnCreationDto();
                                    newFileBpmn.setFilename("Test");
                                    newFileBpmn.setFile(file);
                                    newFileBpmn.setFunctionType(dto.getFunctionType());
                                    return createBpmn(newFileBpmn)
                                            .onItem()
                                            .transformToUni(created -> {
                                                return deployBPMN(created.getBpmnId(), created.getModelVersion())
                                                        .onItem()
                                                        .transformToUni(deployed -> addSingleAssociation(deployed.getBpmnId(), deployed.getModelVersion(), defaultTripletAssociation))
                                                        .onFailure()
                                                        .recoverWithUni( ex -> replaceSingleAssociation(created.getBpmnId(), created.getModelVersion(), defaultTripletAssociation));
                                            });
                                });
                    }
                    return deployBPMN(dto.getUuid(), dto.getVersion())
                            .onItem()
                            .transformToUni(deployed -> addSingleAssociation(deployed.getBpmnId(), deployed.getModelVersion(), defaultTripletAssociation))
                            .onFailure()
                            .recoverWithUni( ex -> replaceSingleAssociation(dto.getUuid(), dto.getVersion(), defaultTripletAssociation));

                });
    }
}
