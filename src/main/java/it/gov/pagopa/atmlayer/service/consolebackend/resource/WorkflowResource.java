package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.FileS3Dto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorCodeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.AtmLayerException;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import it.gov.pagopa.atmlayer.service.consolebackend.service.WorkflowService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.UUID;

import static it.gov.pagopa.atmlayer.service.consolebackend.utils.HeadersUtils.getEmailJWT;
import static it.gov.pagopa.atmlayer.service.consolebackend.utils.HeadersUtils.havePermission;

@Path("/workflow-resource")
@Tag(name = "Workflow Resource", description = "Workflow Resource proxy")
@Slf4j
@ApplicationScoped
@SecuritySchemes({
        @SecurityScheme(securitySchemeName = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
})
@SecurityRequirement(name = "bearerAuth")
public class WorkflowResource {

    @Inject
    WorkflowService workflowService;

    @Inject
    UserService userService;

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Restituisce i Workflow filtrati paginati")
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il processo è terminato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageInfo.class)))
    public Uni<PageInfo<WorkflowResourceFrontEndDTO>> getWorkflowFiltered(@Context ContainerRequestContext containerRequestContext,
                                                                          @QueryParam("pageIndex") @DefaultValue("0")
                                                                          @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) Integer pageIndex,
                                                                          @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) Integer pageSize,
                                                                          @QueryParam("status") @Schema(implementation = String.class, type = SchemaType.STRING, enumeration = {"CREATED", "WAITING_DEPLOY", "UPDATED_BUT_NOT_DEPLOYED", "DEPLOYED", "DEPLOY_ERROR"}) StatusEnum status,
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
                                                                          @QueryParam("fileName") String fileName) {

        return userService.findByUserId(getEmailJWT(containerRequestContext)).onItem().transformToUni(userProfile -> {
            if (userProfile != null && UserProfileEnum.ADMIN.equals(userProfile.getProfile())) {
                return this.workflowService.getWorkflowResourceFiltered(pageIndex, pageSize, status, workflowResourceId, deployedFileName, definitionKey, resourceType, sha256, definitionVersionCamunda, camundaDefinitionId, description, resource, deploymentId, fileName)
                        .onItem()
                        .transform(Unchecked.function(pagedList -> {
                            if (pagedList.getResults().isEmpty()) {
                                log.info("No Workflow resources meets the applied filters");
                            }
                            return pagedList;
                        }));
            } else {
                throw new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED, AppErrorCodeEnum.ATMLCB_401);
            }
        });
    }

    @GET
    @Path("/downloadFrontEnd/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<FileS3Dto> downloadFrontEnd(@Context ContainerRequestContext containerRequestContext,
                                           @PathParam("uuid") UUID uuid){
        return userService.findByUserId(getEmailJWT(containerRequestContext)).onItem().transformToUni(userProfile -> {
            if (!havePermission(userProfile, UserProfileEnum.ADMIN)) {
                throw new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED, AppErrorCodeEnum.ATMLCB_401);
            }
            return this.workflowService.downloadFrontEnd(uuid)
                    .onFailure()
                    .transform(failure -> new AtmLayerException(failure.getMessage(), Response.Status.BAD_REQUEST,AppErrorCodeEnum.ATMLCB_500));
        });
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<WorkflowResourceDTO> create(@Context ContainerRequestContext containerRequestContext,
                                           @RequestBody(required = true) @Valid WorkflowResourceCreationDto workflowResourceCreationDto) {
        return userService.findByUserId(getEmailJWT(containerRequestContext)).onItem().transformToUni(userProfile -> {
            if (!havePermission(userProfile, UserProfileEnum.ADMIN)) {
                throw new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED, AppErrorCodeEnum.ATMLCB_401);
            }
            return this.workflowService.create(workflowResourceCreationDto)
                    .onFailure()
                    .transform(failure -> new AtmLayerException(failure.getMessage(), Response.Status.BAD_REQUEST,AppErrorCodeEnum.ATMLCB_500));
        });
    }

    @POST
    @Path("/deploy/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<WorkflowResourceDTO> deploy(@Context ContainerRequestContext containerRequestContext,
                                           @PathParam("uuid") UUID uuid) {

        return userService.findByUserId(getEmailJWT(containerRequestContext)).onItem().transformToUni(userProfile -> {
            if (!havePermission(userProfile, UserProfileEnum.ADMIN)) {
                throw new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED, AppErrorCodeEnum.ATMLCB_401);
            }
            return this.workflowService.deploy(uuid)
                    .onFailure()
                    .transform(failure -> new AtmLayerException(failure.getMessage(), Response.Status.BAD_REQUEST,AppErrorCodeEnum.ATMLCB_500));
        });

    }

    @PUT
    @Path("/rollback/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<WorkflowResourceDTO> rollback(@Context ContainerRequestContext containerRequestContext,
                                             @PathParam("uuid") UUID uuid) {

        return userService.findByUserId(getEmailJWT(containerRequestContext)).onItem().transformToUni(userProfile -> {
            if (!havePermission(userProfile, UserProfileEnum.ADMIN)) {
                throw new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED, AppErrorCodeEnum.ATMLCB_401);
            }
            return this.workflowService.rollback(uuid)
                    .onFailure()
                    .transform(failure -> new AtmLayerException(failure.getMessage(), Response.Status.BAD_REQUEST,AppErrorCodeEnum.ATMLCB_500));
        });
    }
}
