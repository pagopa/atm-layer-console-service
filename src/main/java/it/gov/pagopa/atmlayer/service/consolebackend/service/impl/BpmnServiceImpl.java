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
import it.gov.pagopa.atmlayer.service.consolebackend.utils.LogUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

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
    public Uni<BpmnDTO> createBpmn(@Context ContainerRequestContext containerRequestContext, BpmnCreationDto bpmnCreationDto) {
        return Uni.createFrom().item(bpmnCreationDto)
                .flatMap(bpmnDto -> {
                    VerifyResponse verify = camundaWebClient.verifyBpmn(bpmnCreationDto.getFile());
                    if (verify.getIsVerified()) {
                        return bpmnWebClient.createBpmn(bpmnDto)
                                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Inserimento BPMN"));
                    } else {
                        return Uni.createFrom().failure(new AtmLayerException("Il file Bpmn non è valido " + verify.getMessage(), Response.Status.NOT_ACCEPTABLE, AppErrorCodeEnum.FILE_NOT_VALID));
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
    public Uni<BpmnBankConfigDTO> addSingleAssociation(UUID bpmnId, Long version, BankConfigTripletDto bankConfigTripletDto, ContainerRequestContext containerRequestContext) {
        return bpmnWebClient.addSingleAssociation(bpmnId, version, bankConfigTripletDto)
                .onItem().invoke(bpmnBankConfigDTO -> LogUtils.logOperation(containerRequestContext, "Associazione singola BPMN a configurazione bancaria"));
    }

    @Override
    public Uni<Void> deleteSingleAssociation(UUID bpmnId, Long version, String acquirerId, String branchId, String terminalId, ContainerRequestContext containerRequestContext) {
        return bpmnWebClient.deleteSingleAssociation(bpmnId, version, acquirerId, branchId, terminalId)
                .onItem().invoke(voidItem -> LogUtils.logOperation(containerRequestContext, "Cancellazione singola associazione BPMN"));
    }

    @Override
    public Uni<BpmnBankConfigDTO> replaceSingleAssociation(UUID bpmnId, Long version, BankConfigTripletDto bankConfigTripletDto, ContainerRequestContext containerRequestContext) {
        return bpmnWebClient.replaceSingleAssociation(bpmnId, version, bankConfigTripletDto)
                .onItem().invoke(bpmnBankConfigDto -> LogUtils.logOperation(containerRequestContext, "Sostituzione singola associazione BPMN"));
    }

    @Override
    public Uni<BpmnDTO> deployBPMN(UUID uuid, Long version, ContainerRequestContext containerRequestContext) {
        return bpmnWebClient.deployBPMN(uuid, version)
                .onItem().invoke(bpmnDTO -> LogUtils.logOperation(containerRequestContext, "Rilascio BPMN"));
    }

    @Override
    public Uni<Void> disableBPMN(UUID bpmnId, Long version, ContainerRequestContext containerRequestContext) {
        return bpmnWebClient.disableBPMN(bpmnId, version)
                .onItem().invoke(voidItem -> LogUtils.logOperation(containerRequestContext, "Disabilita BPMN"));
    }

    @Override
    public Uni<BpmnDTO> upgradeBPMN(BpmnUpgradeDto bpmnUpgradeDto, ContainerRequestContext containerRequestContext) {
        return Uni.createFrom().item(bpmnUpgradeDto)
                .flatMap(bpmnDto -> {
                    VerifyResponse verify = camundaWebClient.verifyBpmn(bpmnUpgradeDto.getFile());
                    if (verify.getIsVerified()) {
                        return bpmnWebClient.upgradeBPMN(bpmnDto)
                                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Aggiornamento BPMN"));
                    } else {
                        return Uni.createFrom().failure(new AtmLayerException("Il file Bpmn non è valido " + verify.getMessage(), Response.Status.NOT_ACCEPTABLE, AppErrorCodeEnum.FILE_NOT_VALID));
                    }
                });
    }
}
