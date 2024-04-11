package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.BpmnWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.client.CamundaWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorCodeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.AtmLayerException;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BpmnService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Arrays;
import java.util.UUID;

@ApplicationScoped
@Slf4j
public class BpmnServiceImpl implements BpmnService {

    @Inject
    @RestClient
    BpmnWebClient bpmnWebClient;

    @Inject
    @RestClient
    CamundaWebClient camundaWebClient;

    @Override
    public Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(int pageIndex, int pageSize, String functionType, String modelVersion, String definitionVersionCamunda, UUID bpmnId, UUID deploymentId, String camundaDefinitionId, String definitionKey, String deployedFileName, String resource, String sha256, StatusEnum status, String acquirerId, String branchId, String terminalId, String fileName) {
        return bpmnWebClient.getBpmnFiltered(pageIndex, pageSize, functionType, modelVersion, definitionVersionCamunda, bpmnId, deploymentId, camundaDefinitionId, definitionKey, deployedFileName, resource, sha256, status, acquirerId, branchId, terminalId, fileName);
    }

    @Override
    public Uni<BpmnDTO> createBpmn(BpmnCreationDto bpmnCreationDto) {
        return Uni.createFrom().item(bpmnCreationDto)
                .flatMap(bpmnDto -> {
                    try {
                        camundaWebClient.verifyBpmn(bpmnCreationDto.getFile());
                        return bpmnWebClient.createBpmn(bpmnDto);
                    } catch (Exception e) {
                        return Uni.createFrom().failure(new AtmLayerException("Il file Bpmn non è valido " + Arrays.toString(e.getStackTrace()), Response.Status.NOT_ACCEPTABLE, AppErrorCodeEnum.FILE_NOT_VALID));
                    }
                });
    }

    @Override
    public Uni<FileS3Dto> downloadBpmnFrontEnd(UUID bpmnId, Long modelVersion) {
        return bpmnWebClient.downloadBpmnFrontEnd(bpmnId, modelVersion);
    }

    @Override
    public Uni<PageInfo<BpmnBankConfigDTO>> getAssociationsByBpmn(int pageIndex, int pageSize, UUID uuid, Long version) {
        return bpmnWebClient.getAssociationsByBpmn(pageIndex, pageSize, uuid, version);
    }

    @Override
    public Uni<BpmnBankConfigDTO> addSingleAssociation(UUID bpmnId, Long version, BankConfigTripletDto bankConfigTripletDto) {
        return bpmnWebClient.addSingleAssociation(bpmnId, version, bankConfigTripletDto);
    }

    @Override
    public Uni<Void> deleteSingleAssociation(UUID bpmnId, Long version, String acquirerId, String branchId, String terminalId) {
        return bpmnWebClient.deleteSingleAssociation(bpmnId, version, acquirerId, branchId, terminalId);
    }

    @Override
    public Uni<BpmnBankConfigDTO> replaceSingleAssociation(UUID bpmnId, Long version, BankConfigTripletDto bankConfigTripletDto) {
        return bpmnWebClient.replaceSingleAssociation(bpmnId, version, bankConfigTripletDto);
    }

    @Override
    public Uni<BpmnDTO> deployBPMN(UUID uuid, Long version) {
        return bpmnWebClient.deployBPMN(uuid, version);
    }

    @Override
    public Uni<Void> disableBPMN(UUID bpmnId, Long version) {
        return bpmnWebClient.disableBPMN(bpmnId, version);
    }

    @Override
    public Uni<BpmnDTO> upgradeBPMN(BpmnUpgradeDto bpmnUpgradeDto) {
        return Uni.createFrom().item(bpmnUpgradeDto)
                .flatMap(bpmnDto -> {
                    try {
                        camundaWebClient.verifyBpmn(bpmnUpgradeDto.getFile());
                        return bpmnWebClient.upgradeBPMN(bpmnDto);
                    } catch (Exception e) {
                        return Uni.createFrom().failure(new AtmLayerException("Il file Bpmn non è valido " + (e.getCause() == null ? e.getMessage() : e.getCause().getMessage()), Response.Status.NOT_ACCEPTABLE, AppErrorCodeEnum.FILE_NOT_VALID));
                    }
                });
    }
}
