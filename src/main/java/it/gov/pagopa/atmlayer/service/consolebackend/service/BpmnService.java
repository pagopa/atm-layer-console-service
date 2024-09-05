package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;

import java.util.UUID;
public interface BpmnService {
    Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(int pageIndex, int pageSize, String functionType, String modelVersion,
                                                          String definitionVersionCamunda, UUID bpmnId, UUID deploymentId,
                                                          String camundaDefinitionId, String definitionKey, String deployedFileName,
                                                          String resource, String sha256, StatusEnum status, String acquirerId, String branchId,
                                                          String terminalId, String fileName);
    Uni<BpmnDTO> createBpmn(@Context ContainerRequestContext containerRequestContext, BpmnCreationDto bpmnCreationDto);
    Uni<FileS3Dto> downloadBpmnFrontEnd(UUID bpmnId, Long modelVersion);
    Uni<PageInfo<BpmnBankConfigDTO>> getAssociationsByBpmn(int pageIndex, int pageSize, UUID uuid, Long version);
    Uni<BpmnBankConfigDTO> addSingleAssociation(UUID bpmnId, Long version, BankConfigTripletDto bankConfigTripletDto);
    Uni<Void> deleteSingleAssociation(UUID bpmnId, Long version, String acquirerId,  String branchId, String terminalId);
    Uni<BpmnBankConfigDTO> replaceSingleAssociation(UUID bpmnId,Long version,BankConfigTripletDto bankConfigTripletDto);
    Uni<BpmnDTO> deployBPMN(UUID uuid, Long version);
    Uni<Void> disableBPMN(UUID bpmnId, Long version);
    Uni<BpmnDTO> upgradeBPMN(BpmnUpgradeDto bpmnUpgradeDto);
}
