package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.ModelWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankConfigTripletDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnBankConfigDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnVersionFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfileDto;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.ModelService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@ApplicationScoped
@Slf4j
public class ModelServiceImpl implements ModelService {

    @Inject
    @RestClient
    ModelWebClient modelWebClient;

    @Override
    public Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(int pageIndex, int pageSize, String functionType, String modelVersion, String status, String acquirerId, String filename) {
        return modelWebClient.getBpmnFiltered(pageIndex, pageSize, functionType, modelVersion, status, acquirerId, filename);
    }

    @Override
    public Uni<UserProfileDto> findByUserId(String userId) {
        return modelWebClient.findByUserId(userId);
    }

    @Override
    public Uni<PageInfo<BpmnBankConfigDTO>> getAssociationsByBpmn(UUID bpmnId, Long version, int pageIndex, int pageSize) {
        return modelWebClient.getAssociationsByBpmn(bpmnId, version, pageIndex, pageSize);
    }

    @Override
    public Uni<BpmnBankConfigDTO> addSingleAssociation(UUID bpmnId, Long version, BankConfigTripletDto bankConfigTripletDto) {
        return modelWebClient.addSingleAssociation(bpmnId, version, bankConfigTripletDto);
    }

    @Override
    public Uni<Void> deleteSingleAssociation(UUID bpmnId, Long version, String acquirerId, String branchId, String terminalId) {
        return modelWebClient.deleteSingleAssociation(bpmnId, version, acquirerId, branchId, terminalId);
    }

    @Override
    public Uni<BpmnBankConfigDTO> replaceSingleAssociation(UUID bpmnId, Long version, BankConfigTripletDto bankConfigTripletDto) {
        return modelWebClient.replaceSingleAssociation(bpmnId, version, bankConfigTripletDto);
    }
}
