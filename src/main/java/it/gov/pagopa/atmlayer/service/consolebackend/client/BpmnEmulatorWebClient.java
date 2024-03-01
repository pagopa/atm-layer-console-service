package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@RegisterRestClient(configKey = "bpmn-emulator-client")
public interface BpmnEmulatorWebClient {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<BpmnDTO> createBpmn (@RequestBody(required = true) @Valid BpmnCreationDto bpmnCreationDto);


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/associations/{uuid}/version/{version}")
    Uni<BpmnBankConfigDTO> addSingleAssociation(@PathParam("uuid") UUID bpmnId, @PathParam("version") Long version,
                                                @RequestBody(required = true) BankConfigTripletDto bankConfigTripletDto);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/associations/{uuid}/version/{version}")
    Uni<BpmnBankConfigDTO> replaceSingleAssociation(@PathParam("uuid") UUID bpmnId,
                                                    @PathParam("version") Long version,
                                                    @RequestBody(required = true) BankConfigTripletDto bankConfigTripletDto);

    @POST
    @Path("/deploy/{uuid}/version/{version}")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<BpmnDTO> deployBPMN(@PathParam("uuid") UUID uuid, @PathParam("version") Long version);

}
