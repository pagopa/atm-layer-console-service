package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankConfigTripletDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnBankConfigDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnVersionFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorCodeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.AtmLayerException;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.ModelService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotEmpty;
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
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.UUID;

import static it.gov.pagopa.atmlayer.service.consolebackend.utils.HeadersUtils.getEmailJWT;
import static it.gov.pagopa.atmlayer.service.consolebackend.utils.HeadersUtils.havePermission;

@Path("/model")
@Tag(name = "Model", description = "Model proxy")
@Slf4j
@ApplicationScoped
@SecuritySchemes({
        @SecurityScheme(securitySchemeName = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
})
public class ModelResource {

    @Inject
    ModelService modelService;


    @GET
    @Path("/bpmn/filtred")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Restituisce i Bpmn filtrati paginati", description = "Esegue la GET dei Bpmn sul Model filtrando sui campi desiderati gestendo la paginazione")
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il processo è terminato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageInfo.class)))
    public Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(@Context ContainerRequestContext containerRequestContext,
                                                                 @QueryParam("pageIndex") @DefaultValue("0")
                                                                 @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                                 @QueryParam("pageSize") @DefaultValue("10")
                                                                 @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                                                 @QueryParam("functionType") String functionType,
                                                                 @QueryParam("modelVersion") String modelVersion,
                                                                 @QueryParam("status")
                                                                 @Schema(implementation = String.class, type = SchemaType.STRING, enumeration = {"CREATED", "WAITING_DEPLOY", "UPDATED_BUT_NOT_DEPLOYED", "DEPLOYED", "DEPLOY_ERROR"}) String status,
                                                                 @QueryParam("acquirerId") String acquirerId,
                                                                 @QueryParam("fileName") String fileName) {

        return modelService.findByUserId(getEmailJWT(containerRequestContext)).onItem().transformToUni(userProfile -> {
            if (havePermission(userProfile, UserProfileEnum.ADMIN)) {
                return this.modelService.getBpmnFiltered(pageIndex, pageSize, functionType, modelVersion, status, acquirerId, fileName)
                        .onItem()
                        .transform(Unchecked.function(pagedList -> {
                            if (pagedList.getResults().isEmpty()) {
                                log.info("No Bpmn file meets the applied filters");
                            }
                            return pagedList;
                        }));
            } else {
                throw new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED, AppErrorCodeEnum.ATMLCB_401);
            }
        });
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bpmn/associations/{uuid}/version/{version}")
    public Uni<PageInfo<BpmnBankConfigDTO>> getAssociationsByBpmn(@Context ContainerRequestContext containerRequestContext,
                                                                  @PathParam("uuid") UUID bpmnId, @PathParam("version") Long version,
                                                                  @QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                                  @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize) {

        return modelService.findByUserId(getEmailJWT(containerRequestContext)).onItem().transformToUni(userProfile -> {
            if (havePermission(userProfile, UserProfileEnum.ADMIN)) {
                return this.modelService.getAssociationsByBpmn(bpmnId, version, pageIndex, pageSize)
                        .onItem()
                        .transform(Unchecked.function(pagedList -> {
                            if (pagedList.getResults().isEmpty()) {
                                log.info("No associations found for BpmnInd= {} and modelVersion= {}", bpmnId, version);
                            }
                            return pagedList;
                        }));
            } else {
                throw new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED, AppErrorCodeEnum.ATMLCB_401);
            }
        });
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/associations/{uuid}/version/{version}")
    public Uni<BpmnBankConfigDTO> addSingleAssociation(@Context ContainerRequestContext containerRequestContext,
                                                       @PathParam("uuid") UUID bpmnId, @PathParam("version") Long version,
                                                       @RequestBody(required = true) BankConfigTripletDto bankConfigTripletDto) {

        return modelService.findByUserId(getEmailJWT(containerRequestContext)).onItem().transformToUni(userProfile -> {
            if (havePermission(userProfile, UserProfileEnum.ADMIN)) {
                return this.modelService.addSingleAssociation(bpmnId, version, bankConfigTripletDto)
                        .onItem()
                        .transform(Unchecked.function( res -> res));
            } else {
                throw new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED, AppErrorCodeEnum.ATMLCB_401);
            }
        });
    }

    @DELETE
    @Path("/associations/{uuid}/version/{version}")
    public Uni<Void> deleteSingleAssociation(@Context ContainerRequestContext containerRequestContext,
                                             @PathParam("uuid") UUID bpmnId, @PathParam("version") Long version,
                                             @QueryParam("acquirerId") @NotEmpty String acquirerId,
                                             @QueryParam("branchId") String branchId,
                                             @QueryParam("terminalId") String terminalId) {
        return modelService.findByUserId(getEmailJWT(containerRequestContext)).onItem().transformToUni(userProfile -> {
            if (havePermission(userProfile, UserProfileEnum.ADMIN)) {
                return this.modelService.deleteSingleAssociation(bpmnId, version, acquirerId, branchId, terminalId)
                        .onItem()
                        .transform(Unchecked.function( res -> res));
            } else {
                throw new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED, AppErrorCodeEnum.ATMLCB_401);
            }
        });
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/associations/{uuid}/version/{version}")
    public Uni<BpmnBankConfigDTO> replaceSingleAssociation(@Context ContainerRequestContext containerRequestContext,
                                                           @PathParam("uuid") UUID bpmnId, @PathParam("version") Long version,
                                                           @RequestBody(required = true) BankConfigTripletDto bankConfigTripletDto) {
        return modelService.findByUserId(getEmailJWT(containerRequestContext)).onItem().transformToUni(userProfile -> {
            if (havePermission(userProfile, UserProfileEnum.ADMIN)) {
                return this.modelService.replaceSingleAssociation(bpmnId, version, bankConfigTripletDto)
                        .onItem()
                        .transform(Unchecked.function( res -> res));
            } else {
                throw new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED, AppErrorCodeEnum.ATMLCB_401);
            }
        });
    }

}
