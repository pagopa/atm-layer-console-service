package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.BpmnEmulatorWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankConfigTripletDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnBankConfigDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BpmnEmulatorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@ApplicationScoped
@Slf4j
public class BpmnEmulatorServiceImpl implements BpmnEmulatorService {

    @Inject
    @RestClient
    BpmnEmulatorWebClient bpmnEmulatorWebClient;

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

}
