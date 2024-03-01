package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankConfigTripletDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnBankConfigDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnDTO;

import java.util.UUID;

public interface BpmnEmulatorService {

    Uni<BpmnDTO> createBpmn(BpmnCreationDto bpmnCreationDto);
    Uni<BpmnBankConfigDTO> addSingleAssociation(UUID bpmnId, Long version, BankConfigTripletDto bankConfigTripletDto);
    Uni<BpmnBankConfigDTO> replaceSingleAssociation(UUID bpmnId,Long version,BankConfigTripletDto bankConfigTripletDto);
    Uni<BpmnDTO> deployBPMN(UUID uuid, Long version);

}
