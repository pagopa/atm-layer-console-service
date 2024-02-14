package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankConfigTripletDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnBankConfigDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnVersionFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfileDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@Path("/api/v1/model")
@RegisterRestClient(configKey = "model-path-api")
public interface ModelWebClient {


    @GET
    @Path("/bpmn/filter")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(@QueryParam("pageIndex") @DefaultValue("0")
                                                          @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                          @QueryParam("pageSize") @DefaultValue("10")
                                                          @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                                          @QueryParam("functionType") String functionType,
                                                          @QueryParam("modelVersion") String modelVersion,
                                                          @QueryParam("status") String status,
                                                          @QueryParam("acquirerId") String acquirerId,
                                                          @QueryParam("fileName") String fileName);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bpmn/associations/{uuid}/version/{version}")
    Uni<PageInfo<BpmnBankConfigDTO>> getAssociationsByBpmn(@PathParam("uuid") UUID bpmnId, @PathParam("version") Long version,
                                                                  @QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                                  @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bpmn/associations/{uuid}/version/{version}")
    Uni<BpmnBankConfigDTO> addSingleAssociation(@PathParam("uuid") UUID bpmnId, @PathParam("version") Long version,
                                                       @RequestBody(required = true) BankConfigTripletDto bankConfigTripletDto);

    @DELETE
    @Path("/bpmn/associations/{uuid}/version/{version}")
    Uni<Void> deleteSingleAssociation(@PathParam("uuid") UUID bpmnId, @PathParam("version") Long version,
                                             @QueryParam("acquirerId") @NotEmpty String acquirerId,
                                             @QueryParam("branchId") String branchId,
                                             @QueryParam("terminalId") String terminalId);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bpmn/associations/{uuid}/version/{version}")
    Uni<BpmnBankConfigDTO> replaceSingleAssociation(@PathParam("uuid") UUID bpmnId, @PathParam("version") Long version,
                                                           @RequestBody(required = true) BankConfigTripletDto bankConfigTripletDto);

    @GET
    @Path("/users/search")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<UserProfileDto> findByUserId(@NotNull @QueryParam("userId") String userId);

    @GET
    @Path("/workflow-resource/filter")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<PageInfo<WorkflowResourceFrontEndDTO>> getWorkflowResourceFiltered(@QueryParam("pageIndex") @DefaultValue("0")
                                                                 @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) Integer page,
                                                                 @QueryParam("pageSize") @DefaultValue("10")
                                                                 @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) Integer size,
                                                                 @QueryParam("status")
                                                                 @Schema(implementation = String.class, type = SchemaType.STRING, enumeration = {"CREATED", "WAITING_DEPLOY", "UPDATED_BUT_NOT_DEPLOYED", "DEPLOYED", "DEPLOY_ERROR"}) StatusEnum status,
                                                                 @QueryParam("workflowResourceId") UUID workflowResourceId,
                                                                 @QueryParam("deployedFileName") String deployedFileName,
                                                                 @QueryParam("definitionKey") String definitionKey,
                                                                 @QueryParam("resourceType") DeployableResourceType resourceType,
                                                                 @QueryParam("sha256") String sha256,
                                                                 @QueryParam("definitionVersionCamunda") String definitionVersionCamunda,
                                                                 @QueryParam("camundaDefinitionId") String camundaDefinitionId,
                                                                 @QueryParam("description") String description,
                                                                 @QueryParam("resource") String resource,
                                                                 @QueryParam("deploymentId") UUID deploymentId,
                                                                 @QueryParam("fileName") String fileName);
}
