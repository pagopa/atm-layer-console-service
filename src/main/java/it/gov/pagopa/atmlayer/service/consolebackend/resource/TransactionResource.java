package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfilesDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionUpdateDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.TransactionService;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
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

import java.sql.Timestamp;

@ApplicationScoped
@Path("/transactions")
@SecuritySchemes({
        @SecurityScheme(securitySchemeName = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
})
@SecurityRequirement(name = "bearerAuth")
public class TransactionResource {

    @Inject
    public TransactionResource(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    private final TransactionService transactionService;
    private final UserService userService;

    @Path("/insert")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "insertTransaction", description = "Inserimento transazione")
    @APIResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<TransactionDTO> insert(@Context ContainerRequestContext containerRequestContext,
                                      @RequestBody(required = true) @Valid TransactionInsertionDTO transactionInsertionDTO) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_TRANSAZIONI)
                .onItem()
                .transformToUni(voidItem -> this.transactionService.insert(transactionInsertionDTO));
    }

    ;

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "searchTransaction",
            description = "Ricerca transazioni mettendo dei filtri"
    )
    @APIResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = PageInfo.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<PageInfo<TransactionDTO>> searchTransaction(@Context ContainerRequestContext containerRequestContext,
                                                           @QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(minimum = "0",maximum = "10000")) int pageIndex,
                                                @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(minimum = "1",maximum = "100")) int pageSize,
                                                @QueryParam("transactionId") @Schema(format = "byte", maxLength = 255) String transactionId,
                                                @QueryParam("functionType") @Schema(format = "byte", maxLength = 255) String functionType,
                                                @QueryParam("acquirerId") @Schema(format = "byte", maxLength = 255) String acquirerId,
                                                @QueryParam("branchId") @Schema(format = "byte", maxLength = 255) String branchId,
                                                @QueryParam("terminalId") @Schema(format = "byte", maxLength = 255) String terminalId,
                                                @QueryParam("transactionStatus") @Schema(format = "byte", maxLength = 255) String transactionStatus,
                                                @QueryParam("startTime") @Schema(example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}") Timestamp startTime,
                                                @QueryParam("endTime") @Schema(example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}") Timestamp endTime) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_TRANSAZIONI)
                .onItem()
                .transformToUni(voidItem -> this.transactionService.searchTransaction(pageIndex, pageSize, transactionId, functionType, acquirerId, branchId, terminalId, transactionStatus, startTime, endTime));
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "updateTransaction",
            description = "Aggiorna transazione"
    )
    @APIResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = TransactionDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<TransactionDTO> update(@Context ContainerRequestContext containerRequestContext,
                                      @RequestBody(required = true) @Valid TransactionUpdateDTO transactionUpdateDTO) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_TRANSAZIONI)
                .onItem()
                .transformToUni(voidItem -> transactionService.update(transactionUpdateDTO));
    }

    @DELETE
    @Path("/delete/{transactionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "deleteTransaction",
            description = "Elimina transazione"
    )
    @APIResponse(responseCode = "204", description = "Ok")
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<Void> delete(@Context ContainerRequestContext containerRequestContext,
                            @PathParam("transactionId") @Schema(format = "byte", maxLength = 255) String transactionId) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_TRANSAZIONI)
                .onItem()
                .transformToUni(voidItem -> this.transactionService.delete(transactionId));
    }
}
