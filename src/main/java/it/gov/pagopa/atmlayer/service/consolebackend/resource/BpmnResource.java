package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BpmnService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
    public BpmnResource(BpmnService bpmnService) {
        this.bpmnService = bpmnService;
    }

    private final BpmnService bpmnService;

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Restituisce i Bpmn filtrati paginati", description = "Esegue la GET dei Bpmn sul Model filtrando sui campi desiderati gestendo la paginazione")
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Recuperati processi cercati.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageInfo.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(@QueryParam("pageIndex") @DefaultValue("0")
                                                                 @Parameter(required = true, schema = @Schema(minimum = "0", maximum = "100000")) int pageIndex,
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
        return this.bpmnService.getBpmnFiltered(pageIndex, pageSize, functionType, modelVersion, definitionVersionCamunda, bpmnId, deploymentId, camundaDefinitionId, definitionKey, deployedFileName, resource, sha256, status, acquirerId, branchId, terminalId, fileName)
                .onItem()
                .transform(Unchecked.function(pagedList -> {
                    if (pagedList.getResults().isEmpty()) {
                        log.info("No Bpmn file meets the applied filters");
                    }
                    return pagedList;
                }));
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Blocking
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Carica un file BPMN")
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il file è stato caricato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BpmnDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<BpmnDTO> createBpmn(@RequestBody(required = true) @Valid BpmnCreationDto bpmnCreationDto) {
        return this.bpmnService.createBpmn(bpmnCreationDto);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/associations/{uuid}/version/{version}")
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Recuperati terminali associati al BPMN cercato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageInfo.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<PageInfo<BpmnBankConfigDTO>> getAssociationsByBpmn(@PathParam("uuid") UUID uuid, @PathParam("version") @Schema(minimum="1", maximum="10000") Long version,
                                                                  @QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(minimum = "0", maximum = "100000")) int pageIndex,
                                                                  @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(minimum = "1", maximum = "100")) int pageSize) {
        return this.bpmnService.getAssociationsByBpmn(pageIndex, pageSize, uuid, version)
                .onItem()
                .transform(Unchecked.function(pagedList -> {
                    if (pagedList.getResults().isEmpty()) {
                        log.info("No BPMN meets the applied filters");
                    }
                    return pagedList;
                }));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/associations/{uuid}/version/{version}")
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Aggiunta singola associazione.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BpmnBankConfigDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<BpmnBankConfigDTO> addSingleAssociation(@PathParam("uuid") UUID bpmnId,
                                                       @PathParam("version") @Schema(minimum="1", maximum="10000") Long version,
                                                       @RequestBody(required = true) BankConfigTripletDto bankConfigTripletDto) {
        return this.bpmnService.addSingleAssociation(bpmnId, version, bankConfigTripletDto);
    }

    @DELETE
    @Path("/associations/{uuid}/version/{version}")
    @APIResponse(responseCode = "204", description = "Operazione eseguita con successo. Eliminata singola associazione")
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<Void> deleteSingleAssociation(@PathParam("uuid") UUID bpmnId,
                                             @PathParam("version") @Schema(minimum="1", maximum="10000") Long version,
                                             @QueryParam("acquirerId") @NotEmpty @Schema(format = "byte", maxLength = 255) String acquirerId,
                                             @QueryParam("branchId") @Schema(format = "byte", maxLength = 255) String branchId,
                                             @QueryParam("terminalId") @Schema(format = "byte", maxLength = 255) String terminalId) {
        return this.bpmnService.deleteSingleAssociation(bpmnId, version, acquirerId, branchId, terminalId);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/associations/{uuid}/version/{version}")
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Sostituita singola associazione.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BpmnBankConfigDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<BpmnBankConfigDTO> replaceSingleAssociation(@PathParam("uuid") UUID bpmnId, @PathParam("version") @Schema(minimum="1", maximum="10000") Long version,
                                                           @RequestBody(required = true) BankConfigTripletDto bankConfigTripletDto) {
        return this.bpmnService.replaceSingleAssociation(bpmnId, version, bankConfigTripletDto);
    }

    @POST
    @Path("/deploy/{uuid}/version/{version}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il processo è stato deployato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BpmnDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<BpmnDTO> deployBPMN(@PathParam("uuid") UUID uuid,
                                   @PathParam("version") @Schema(minimum="1", maximum="10000") Long version) {
        return this.bpmnService.deployBPMN(uuid, version);
    }

    @GET
    @Path("/downloadFrontEnd/{uuid}/version/{version}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il file è stato scaricato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileS3Dto.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<FileS3Dto> downloadBpmnFrontEnd(@PathParam("uuid") UUID bpmnId,
                                               @PathParam("version") @Schema(minimum="1", maximum="10000") Long modelVersion){
         return this.bpmnService.downloadBpmnFrontEnd(bpmnId, modelVersion);
    }

    @POST
    @Path("/disable/{uuid}/version/{version}")
    @APIResponse(responseCode = "204", description = "Operazione eseguita con successo. Il processo è stato disabilitato.")
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<Void> disableBPMN(@PathParam("uuid") UUID bpmnId,
                                 @PathParam("version") @Schema(minimum="1", maximum="10000") Long version) {
        return this.bpmnService.disableBPMN(bpmnId, version);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Blocking
    @Path("/upgrade")
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il processo è stato aggiornato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BpmnDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}" ))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}" ))
    public Uni<BpmnDTO> upgradeBPMN(@Valid BpmnUpgradeDto bpmnUpgradeDto) {
        return this.bpmnService.upgradeBPMN(bpmnUpgradeDto);
    }
}