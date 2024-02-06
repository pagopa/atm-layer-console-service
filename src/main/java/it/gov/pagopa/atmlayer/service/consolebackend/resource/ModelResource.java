package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnVersionFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorCodeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.AtmLayerException;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.ModelService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
//import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@ApplicationScoped
@Path("/model")
@Tag(name = "Model", description = "Model proxy")
@Slf4j
public class ModelResource {

    @Inject
    ModelService modelService;

//    @Inject
//    JsonWebToken jwt;

//    @Inject
//    ATMLayerJwtRoleVerifier atmLayerJwtRoleVerifier;

    @GET
    @Path("/bpmn/filtred")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Restituisce i Bpmn filtrati paginati", description = "Esegue la GET dei Bpmn sul Model filtrando sui campi desiderati gestendo la paginazione")
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il processo è terminato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageInfo.class)))
    public Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(@QueryParam("pageIndex") @DefaultValue("0")
                                                                 @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                                 @QueryParam("pageSize") @DefaultValue("10")
                                                                 @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                                                 @QueryParam("functionType") String functionType,
                                                                 @QueryParam("modelVersion") String modelVersion,
                                                                 @QueryParam("status")
                                                                 @Schema(implementation = String.class, type = SchemaType.STRING, enumeration = {"CREATED", "WAITING_DEPLOY", "UPDATED_BUT_NOT_DEPLOYED", "DEPLOYED", "DEPLOY_ERROR"}) String status,
                                                                 @QueryParam("acquirerId") String acquirerId,
                                                                 @QueryParam("fileName") String fileName) {

//        String fiscalCode = jwt.getClaim("fiscalCode");
//
//        if("RSSMRA85T10A562S".equals(fiscalCode)){
            return this.modelService.getBpmnFiltered(pageIndex, pageSize, functionType, modelVersion, status, acquirerId, fileName)
                    .onItem()
                    .transform(Unchecked.function(pagedList -> {
                        if (pagedList.getResults().isEmpty()) {
                            log.info("No Bpmn file meets the applied filters");
                        }
                        return pagedList;
                    }));
//        }
//        throw new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED, AppErrorCodeEnum.ATMLCB_401);
    }

}
