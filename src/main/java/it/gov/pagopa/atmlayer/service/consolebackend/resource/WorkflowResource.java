package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.FileS3Dto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.WorkflowService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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

import java.io.File;
import java.util.UUID;

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
    public WorkflowResource(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }
    private final WorkflowService workflowService;

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Restituisce i Workflow filtrati paginati")
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Recuperate risorse cercate.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageInfo.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<PageInfo<WorkflowResourceFrontEndDTO>> getWorkflowFiltered(@QueryParam("pageIndex") @DefaultValue("0")
                                                                          @Parameter(required = true, schema = @Schema(minimum = "0", maximum = "100000")) Integer pageIndex,
                                                                          @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(minimum="1", maximum="100")) Integer pageSize,
                                                                          @QueryParam("status") @Schema(implementation = String.class, type = SchemaType.STRING, enumeration = {"CREATED", "WAITING_DEPLOY", "UPDATED_BUT_NOT_DEPLOYED", "DEPLOYED", "DEPLOY_ERROR"}) StatusEnum status,
                                                                          @QueryParam("workflowResourceId") UUID workflowResourceId,
                                                                          @QueryParam("deployedFileName") @Schema(format = "byte", maxLength = 255) String deployedFileName,
                                                                          @QueryParam("definitionKey") @Schema(format = "byte", maxLength = 255) String definitionKey,
                                                                          @QueryParam("resourceType") DeployableResourceType resourceType,
                                                                          @QueryParam("sha256") @Schema(format = "byte", maxLength = 255) String sha256,
                                                                          @QueryParam("definitionVersionCamunda") @Schema(format = "byte", maxLength = 5) String definitionVersionCamunda,
                                                                          @QueryParam("camundaDefinitionId") @Schema(format = "byte", maxLength = 255) String camundaDefinitionId,
                                                                          @QueryParam("description") @Schema(format = "byte", maxLength = 255) String description,
                                                                          @QueryParam("resource") @Schema(format = "byte", maxLength = 255) String resource,
                                                                          @QueryParam("deploymentId") UUID deploymentId,
                                                                          @QueryParam("fileName") @Schema(format = "byte", maxLength = 255) String fileName) {
         return this.workflowService.getWorkflowResourceFiltered(pageIndex, pageSize, status, workflowResourceId, deployedFileName, definitionKey, resourceType, sha256, definitionVersionCamunda, camundaDefinitionId, description, resource, deploymentId, fileName)
                 .onItem()
                 .transform(Unchecked.function(pagedList -> {
                     if (pagedList.getResults().isEmpty()) {
                         log.info("No Workflow resources meets the applied filters");
                     }
                     return pagedList;
                 }));
    }

    @GET
    @Path("/downloadFrontEnd/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Risorsa scaricata.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileS3Dto.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<FileS3Dto> downloadFrontEnd(@PathParam("uuid") UUID uuid){
        return this.workflowService.downloadFrontEnd(uuid);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Risorsa creata.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkflowResourceDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<WorkflowResourceDTO> create(@RequestBody(required = true) @Valid WorkflowResourceCreationDto workflowResourceCreationDto) {
        return this.workflowService.create(workflowResourceCreationDto);
    }

    @POST
    @Path("/deploy/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Risorsa deployata.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkflowResourceDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<WorkflowResourceDTO> deploy(@PathParam("uuid") UUID uuid) {
        return this.workflowService.deploy(uuid);
    }

    @PUT
    @Path("/rollback/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Versione precedente ripristinata.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkflowResourceDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<WorkflowResourceDTO> rollback(@PathParam("uuid") UUID uuid) {
        return this.workflowService.rollback(uuid);
    }

    @PUT
    @Path("/update/{uuid}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Risorsa aggiornata.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkflowResourceDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<WorkflowResourceDTO> update(@RequestBody(required = true) @FormParam("file") @NotNull(message = "input file is required") File file,
                                    @PathParam("uuid") UUID uuid){
            return this.workflowService.update(file, uuid);
    }

    @POST
    @Path("/disable/{uuid}")
    @APIResponse(responseCode = "204", description = "Operazione eseguita con successo. Risorsa disabilitata.")
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<Void> disable(@PathParam("uuid") UUID uuid){
            return this.workflowService.disable(uuid);
    }
}
