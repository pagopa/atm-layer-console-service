package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.FileS3Dto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.mapper.ModelClientExceptionMapper;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.io.File;
import java.util.UUID;
@RegisterProvider(ModelClientExceptionMapper.class)
@RegisterRestClient(configKey = "workflow-client")
public interface WorkflowWebClient {
    @GET
    @Path("/filter")
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

    @GET
    @Path("/downloadFrontEnd/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<FileS3Dto> downloadFrontEnd(@PathParam("uuid") UUID workflowResourceId);

    @POST
    @Path("/deploy/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<WorkflowResourceDTO> deploy(@PathParam("uuid") UUID uuid);

    @PUT
    @Path("/rollback/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<WorkflowResourceDTO> rollback(@PathParam("uuid") UUID uuid);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<WorkflowResourceDTO> create(@RequestBody(required = true) @Valid WorkflowResourceCreationDto workflowResourceCreationDto);

    @PUT
    @Path("/update/{uuid}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<WorkflowResourceDTO> update(@RequestBody(required = true) @FormParam("file") @NotNull(message = "input file is required") File file,
                                    @PathParam("uuid") UUID uuid);

    @POST
    @Path("/disable/{uuid}")
    Uni<Void> disable(@PathParam("uuid") UUID uuid);
}
