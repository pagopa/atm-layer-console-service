package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@RegisterRestClient(configKey = "workflow-client")
public interface ModelWebClient {

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
}
