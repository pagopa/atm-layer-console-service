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
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il processo è terminato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageInfo.class)))
    public Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(@QueryParam("pageIndex") @DefaultValue("0")
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
                                                                 @QueryParam("fileName") String fileName) {
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
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il file è stato caricato.")
    public Uni<BpmnDTO> createBpmn(@RequestBody(required = true) @Valid BpmnCreationDto bpmnCreationDto) {
        return this.bpmnService.createBpmn(bpmnCreationDto);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/associations/{uuid}/version/{version}")
    public Uni<PageInfo<BpmnBankConfigDTO>> getAssociationsByBpmn(@PathParam("uuid") UUID uuid, @PathParam("version") Long version,
                                                                  @QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                                  @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize) {
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
    public Uni<BpmnBankConfigDTO> addSingleAssociation(@PathParam("uuid") UUID bpmnId,
                                                       @PathParam("version") Long version,
                                                       @RequestBody(required = true) BankConfigTripletDto bankConfigTripletDto) {
        return this.bpmnService.addSingleAssociation(bpmnId, version, bankConfigTripletDto);
    }

    @DELETE
    @Path("/associations/{uuid}/version/{version}")
    public Uni<Void> deleteSingleAssociation(@PathParam("uuid") UUID bpmnId,
                                             @PathParam("version") Long version,
                                             @QueryParam("acquirerId") @NotEmpty String acquirerId,
                                             @QueryParam("branchId") String branchId,
                                             @QueryParam("terminalId") String terminalId) {
        return this.bpmnService.deleteSingleAssociation(bpmnId, version, acquirerId, branchId, terminalId);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/associations/{uuid}/version/{version}")
    public Uni<BpmnBankConfigDTO> replaceSingleAssociation(@PathParam("uuid") UUID bpmnId, @PathParam("version") Long version,
                                                           @RequestBody(required = true) BankConfigTripletDto bankConfigTripletDto) {
        return this.bpmnService.replaceSingleAssociation(bpmnId, version, bankConfigTripletDto);
    }

    @POST
    @Path("/deploy/{uuid}/version/{version}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<BpmnDTO> deployBPMN(@PathParam("uuid") UUID uuid,
                                   @PathParam("version") Long version) {
        return this.bpmnService.deployBPMN(uuid, version);
    }

    @GET
    @Path("/downloadFrontEnd/{uuid}/version/{version}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<FileS3Dto> downloadBpmnFrontEnd(@PathParam("uuid") UUID bpmnId,
                                               @PathParam("version") Long modelVersion){
         return this.bpmnService.downloadBpmnFrontEnd(bpmnId, modelVersion);
    }

    @POST
    @Path("/disable/{uuid}/version/{version}")
    public Uni<Void> disableBPMN(@PathParam("uuid") UUID bpmnId,
                                 @PathParam("version") Long version) {
        return this.bpmnService.disableBPMN(bpmnId, version);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Blocking
    @Path("/upgrade")
    public Uni<BpmnDTO> upgradeBPMN(@Valid BpmnUpgradeDto bpmnUpgradeDto) {
        return this.bpmnService.upgradeBPMN(bpmnUpgradeDto);
    }
}