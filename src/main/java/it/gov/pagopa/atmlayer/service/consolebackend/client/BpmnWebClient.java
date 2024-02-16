package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@RegisterRestClient(configKey = "bpmn-client")
public interface BpmnWebClient {

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(@QueryParam("pageIndex") @DefaultValue("0")
                                                          @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                          @QueryParam("pageSize") @DefaultValue("10")
                                                          @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                                          @QueryParam("functionType") String functionType,
                                                          @QueryParam("modelVersion") String modelVersion,
                                                          @QueryParam("definitionVersionCamunda") String definitionVersionCamunda,
                                                          @QueryParam("bpmnId") UUID bpmnId,
                                                          @QueryParam("deploymentId") UUID deploymentId,
                                                          @QueryParam("camundaDefinitionId") String camundaDefinitionId,
                                                          @QueryParam("definitionKey") String definitionKey,
                                                          @QueryParam("deployedFileName") String deployedFileName,
                                                          @QueryParam("resource") String resource,
                                                          @QueryParam("sha256") String sha256,
                                                          @QueryParam("status") StatusEnum status,
                                                          @QueryParam("acquirerId") String acquirerId,
                                                          @QueryParam("branchId") String branchId,
                                                          @QueryParam("terminalId") String terminalId,
                                                          @QueryParam("fileName") String fileName);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<BpmnDTO> createBpmn (@RequestBody(required = true) @Valid BpmnCreationDto bpmnCreationDto);

    @GET
    @Path("/downloadFrontEnd/{uuid}/version/{version}")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<FileS3Dto> downloadBpmnFrontEnd(@PathParam("uuid") UUID bpmnId, @PathParam("version") Long modelVersion);

    @GET
    @Path("/associations/{uuid}/version/{version}")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<PageInfo<BpmnBankConfigDTO>> getAssociationsByBpmn(@QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                           @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                                           @PathParam("uuid") UUID uuid, @PathParam("version") Long version);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/associations/{uuid}/version/{version}")
    Uni<BpmnBankConfigDTO> addSingleAssociation(@PathParam("uuid") UUID bpmnId, @PathParam("version") Long version,
                                                @RequestBody(required = true) BankConfigTripletDto bankConfigTripletDto);

    @DELETE
    @Path("/associations/{uuid}/version/{version}")
    Uni<Void> deleteSingleAssociation(@PathParam("uuid") UUID bpmnId, @PathParam("version") Long version,
                                      @QueryParam("acquirerId") @NotEmpty String acquirerId,
                                      @QueryParam("branchId") String branchId,
                                      @QueryParam("terminalId") String terminalId);

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
