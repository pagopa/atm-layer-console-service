package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankConfigTripletDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnBankConfigDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnVersionFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfileDto;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.UUID;

public interface ModelService {

    Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(int pageIndex, int pageSize, String functionType, String modelVersion, String status, String acquirerId, String filename);

    Uni<UserProfileDto> findByUserId(String userId);

    Uni<PageInfo<BpmnBankConfigDTO>> getAssociationsByBpmn(UUID bpmnId, Long version, int pageIndex, int pageSize);

    Uni<BpmnBankConfigDTO> addSingleAssociation(UUID bpmnId, Long version, BankConfigTripletDto bankConfigTripletDto);

    Uni<Void> deleteSingleAssociation(UUID bpmnId, Long version, String acquirerId, String branchId, String terminalId);

    Uni<BpmnBankConfigDTO> replaceSingleAssociation(UUID bpmnId, Long version, BankConfigTripletDto bankConfigTripletDto);

}
