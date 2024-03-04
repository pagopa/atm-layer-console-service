package it.gov.pagopa.atmlayer.service.consolebackend.resource.emulator;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankConfigTripletDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmEmulatorUpgradeDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnBankConfigDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BpmnEmulatorService;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BpmnService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Path("/emulator/bpmn")
@Tag(name = "Emulator BPMN")
@Slf4j
@ApplicationScoped
@SecuritySchemes({
        @SecurityScheme(securitySchemeName = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
})
@SecurityRequirement(name = "bearerAuth")
public class BpmnEmulatorResource {

    @Inject
    BpmnEmulatorService bpmnEmulatorService;

    @POST
    @Path("/deploy")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Carica un file BPM per l'emulatore")
    public Uni<BpmnBankConfigDTO> deployEmulatorBpmn(@Valid BpmEmulatorUpgradeDto dto){
        return bpmnEmulatorService.deployEmulatorBpmn(dto);
    }
}
