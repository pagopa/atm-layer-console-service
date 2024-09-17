package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BpmnService;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
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

import java.util.UUID;

@Path("/bpmn")
@Tag(name = "BPMN", description = "BPMN proxy")
@Slf4j
@ApplicationScoped
@SecuritySchemes({
        @SecurityScheme(securitySchemeName = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
})
@SecurityRequirement(name = "bearerAuth")
public class BpmnResource {
    @Inject
    public BpmnResource(BpmnService bpmnService, UserService userService) {
        this.bpmnService = bpmnService;
        this.userService = userService;

    }

    private final BpmnService bpmnService;

    private final UserService userService;

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getBpmnFiltered", description = "Esegue la GET dei Bpmn sul Model filtrando sui campi desiderati gestendo la paginazione")
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il processo è terminato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageInfo.class)))
    public Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(@Context ContainerRequestContext containerRequestContext,
                                                                 @QueryParam("pageIndex") @DefaultValue("0")
                                                                 @Parameter(required = true, schema = @Schema(minimum = "0", maximum = "10000")) int pageIndex,
                                                                 @QueryParam("pageSize") @DefaultValue("10")
                                                                 @Parameter(required = true, schema = @Schema(minimum="1", maximum="100") ) int pageSize,
                                                                 @QueryParam("functionType") @Schema(format = "byte", maxLength = 255) String functionType,
                                                                 @QueryParam("modelVersion") @Schema(format = "byte", maxLength = 5) String modelVersion,
                                                                 @QueryParam("definitionVersionCamunda") @Schema(format = "byte", maxLength = 5) String definitionVersionCamunda,
                                                                 @QueryParam("bpmnId") UUID bpmnId,
                                                                 @QueryParam("deploymentId") UUID deploymentId,
                                                                 @QueryParam("camundaDefinitionId") @Schema(format = "byte", maxLength = 255) String camundaDefinitionId,
                                                                 @QueryParam("definitionKey") @Schema(format = "byte", maxLength = 255) String definitionKey,
                                                                 @QueryParam("deployedFileName") @Schema(format = "byte", maxLength = 255) String deployedFileName,
                                                                 @QueryParam("resource") @Schema(format = "byte", maxLength = 255) String resource,
                                                                 @QueryParam("sha256") @Schema(format = "byte", maxLength = 255) String sha256,
                                                                 @QueryParam("status") StatusEnum status,
                                                                 @QueryParam("acquirerId") @Schema(format = "byte", maxLength = 255) String acquirerId,
                                                                 @QueryParam("branchId") @Schema(format = "byte", maxLength = 255) String branchId,
                                                                 @QueryParam("terminalId") @Schema(format = "byte", maxLength = 255) String terminalId,
                                                                 @QueryParam("fileName") @Schema(format = "byte", maxLength = 255) String fileName) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.READ_GESTIONE_FLUSSI)
                .onItem()
                .transformToUni(voidItem -> this.bpmnService.getBpmnFiltered(pageIndex, pageSize, functionType, modelVersion, definitionVersionCamunda, bpmnId, deploymentId, camundaDefinitionId, definitionKey, deployedFileName, resource, sha256, status, acquirerId, branchId, terminalId, fileName)
                        .onItem()
                        .transform(Unchecked.function(pagedList -> {
                            if (pagedList.getResults().isEmpty()) {
                                log.info("No Bpmn file meets the applied filters");
                            }
                            return pagedList;
                        })));
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Blocking
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "createBpmn",description = "Carica un file BPMN")
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il file è stato caricato.")
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<BpmnDTO> createBpmn(@Context ContainerRequestContext containerRequestContext,
                                   @RequestBody(required = true) @Valid BpmnCreationDto bpmnCreationDto) {
        userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.WRITE_GESTIONE_FLUSSI);
        return this.bpmnService.createBpmn(containerRequestContext, bpmnCreationDto);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/associations/{uuid}/version/{version}")
    @Operation(
            operationId = "getAssociationsByBpmn",
            description = "Cerca associazioni di un BPMN"
    )
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Recuperati terminali associati al BPMN cercato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageInfo.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<PageInfo<BpmnBankConfigDTO>> getAssociationsByBpmn(@Context ContainerRequestContext containerRequestContext,
                                                                  @PathParam("uuid") UUID uuid, @PathParam("version") @Schema(minimum = "1", maximum = "10000")  Long version,
                                                                  @QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(minimum = "0", maximum = "10000")) int pageIndex,
                                                                  @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(minimum = "1", maximum = "100")) int pageSize) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.READ_GESTIONE_FLUSSI)
                .onItem()
                .transformToUni(voidItem -> this.bpmnService.getAssociationsByBpmn(pageIndex, pageSize, uuid, version)
                .onItem()
                .transform(Unchecked.function(pagedList -> {
                    if (pagedList.getResults().isEmpty()) {
                        log.info("No BPMN meets the applied filters");
                    }
                    return pagedList;
                })));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/associations/{uuid}/version/{version}")
    @Operation(
            operationId = "addSingleAssociation",
            description = "Aggiungi una singola associazione"
    )
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Aggiunta singola associazione.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BpmnBankConfigDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<BpmnBankConfigDTO> addSingleAssociation(@Context ContainerRequestContext containerRequestContext,
                                                       @PathParam("uuid") UUID bpmnId,
                                                       @PathParam("version") @Schema(minimum = "1", maximum = "10000") Long version,
                                                       @RequestBody(required = true) BankConfigTripletDto bankConfigTripletDto) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.WRITE_GESTIONE_FLUSSI)
                .onItem()
                .transformToUni(voidItem -> this.bpmnService.addSingleAssociation(bpmnId, version, bankConfigTripletDto, containerRequestContext));
    }

    @DELETE
    @Path("/associations/{uuid}/version/{version}")
    @Operation(
            operationId = "deleteSingleAssociation",
            description = "Elimina una singola associazione"
    )
    @APIResponse(responseCode = "204", description = "Operazione eseguita con successo. Eliminata singola associazione")
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<Void> deleteSingleAssociation(@Context ContainerRequestContext containerRequestContext,
                                             @PathParam("uuid") UUID bpmnId,
                                             @PathParam("version") @Schema(minimum = "1", maximum = "10000") Long version,
                                             @QueryParam("acquirerId") @Schema(format = "byte", maxLength = 255) @NotEmpty String acquirerId,
                                             @QueryParam("branchId") @Schema(format = "byte", maxLength = 255) String branchId,
                                             @QueryParam("terminalId") @Schema(format = "byte", maxLength = 255) String terminalId) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.WRITE_GESTIONE_FLUSSI)
                .onItem()
                .transformToUni(voidItem -> this.bpmnService.deleteSingleAssociation(bpmnId, version, acquirerId, branchId, terminalId, containerRequestContext));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/associations/{uuid}/version/{version}")
    @Operation(
            operationId = "replaceSingleAssociation",
            description = "Sostituisci una singola associazione"
    )
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Sostituita singola associazione.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BpmnBankConfigDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<BpmnBankConfigDTO> replaceSingleAssociation(@Context ContainerRequestContext containerRequestContext,
                                                           @PathParam("uuid") UUID bpmnId,
                                                           @PathParam("version") @Schema(minimum = "1", maximum = "10000") Long version,
                                                           @RequestBody(required = true) BankConfigTripletDto bankConfigTripletDto) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.WRITE_GESTIONE_FLUSSI)
                .onItem()
                .transformToUni(voidItem -> this.bpmnService.replaceSingleAssociation(bpmnId, version, bankConfigTripletDto, containerRequestContext));
    }

    @POST
    @Path("/deploy/{uuid}/version/{version}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "deployBpmn",
            description = "Rilascio BPMN"
    )
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il processo è stato deployato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BpmnDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<BpmnDTO> deployBPMN(@Context ContainerRequestContext containerRequestContext,
                                   @PathParam("uuid") UUID uuid,
                                   @PathParam("version") @Schema(minimum = "1", maximum = "10000") Long version) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.DEPLOY_BPMN)
                .onItem()
                .transformToUni(voidItem -> this.bpmnService.deployBPMN(uuid, version, containerRequestContext));
    }

    @GET
    @Path("/downloadFrontEnd/{uuid}/version/{version}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "downloadBpmnFrontEnd",
            description = "Scarica il file BPMN dal front-end"
    )
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il file è stato scaricato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileS3Dto.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<FileS3Dto> downloadBpmnFrontEnd(@Context ContainerRequestContext containerRequestContext,
                                               @PathParam("uuid") UUID bpmnId,
                                               @PathParam("version") @Schema(minimum = "1", maximum = "10000") Long modelVersion){
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.READ_GESTIONE_FLUSSI)
                .onItem()
                .transformToUni(voidItem -> this.bpmnService.downloadBpmnFrontEnd(bpmnId, modelVersion));
    }

    @POST
    @Path("/disable/{uuid}/version/{version}")
    @Operation(
            operationId = "disableBPMN",
            description = "Disabilita un file BPMN"
    )
    @APIResponse(responseCode = "204", description = "Operazione eseguita con successo. Il processo è stato disabilitato.")
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<Void> disableBPMN(@Context ContainerRequestContext containerRequestContext,
                                 @PathParam("uuid") UUID bpmnId,
                                 @PathParam("version") @Schema(minimum = "1", maximum = "10000") Long version) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.WRITE_GESTIONE_FLUSSI)
                .onItem()
                .transformToUni(voidItem -> this.bpmnService.disableBPMN(bpmnId, version, containerRequestContext));
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Blocking
    @Path("/upgrade")
    @Operation(
            operationId = "upgradeBPMN",
            description = "Aggiorna il file BPMN aumentando la versione"
    )
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il processo è stato aggiornato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BpmnDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<BpmnDTO> upgradeBPMN(@Context ContainerRequestContext containerRequestContext,
                                    @Valid BpmnUpgradeDto bpmnUpgradeDto) {
        userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.WRITE_GESTIONE_FLUSSI);
        return this.bpmnService.upgradeBPMN(bpmnUpgradeDto, containerRequestContext);
    }
}