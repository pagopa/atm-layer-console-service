package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
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
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
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
    public Uni<PageInfo<TransactionDTO>> searchTransaction(@Context ContainerRequestContext containerRequestContext,
                                                           @QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                                @QueryParam("transactionId") String transactionId,
                                                @QueryParam("functionType") String functionType,
                                                @QueryParam("acquirerId") String acquirerId,
                                                @QueryParam("branchId") String branchId,
                                                @QueryParam("terminalId") String terminalId,
                                                @QueryParam("transactionStatus") String transactionStatus,
                                                @QueryParam("startTime") @Schema(example = "yyyy-mm-dd hh:mm:ss") Timestamp startTime,
                                                @QueryParam("endTime") @Schema(example = "yyyy-mm-dd hh:mm:ss") Timestamp endTime) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_TRANSAZIONI)
                .onItem()
                .transformToUni(voidItem -> this.transactionService.searchTransaction(pageIndex, pageSize, transactionId, functionType, acquirerId, branchId, terminalId, transactionStatus, startTime, endTime));
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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
    public Uni<Void> delete(@Context ContainerRequestContext containerRequestContext,
                            @PathParam("transactionId") String transactionId) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_TRANSAZIONI)
                .onItem()
                .transformToUni(voidItem -> this.transactionService.delete(transactionId));
    }
}
