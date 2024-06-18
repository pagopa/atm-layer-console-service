package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionUpdateDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.TransactionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
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
import java.util.List;

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
    TransactionService transactionService;

    @Path("/insert")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<TransactionDTO> insert(@RequestBody(required = true) @Valid TransactionInsertionDTO transactionInsertionDTO) {
        return this.transactionService.insert(transactionInsertionDTO);
    }

    ;

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PageInfo<TransactionDTO>> searchTransaction(@QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                                @QueryParam("transactionId") String transactionId,
                                                @QueryParam("functionType") String functionType,
                                                @QueryParam("acquirerId") String acquirerId,
                                                @QueryParam("branchId") String branchId,
                                                @QueryParam("terminalId") String terminalId,
                                                @QueryParam("transactionStatus") String transactionStatus,
                                                @QueryParam("startTime") @Schema(example = "yyyy-mm-dd hh:mm:ss") Timestamp startTime,
                                                @QueryParam("endTime") @Schema(example = "yyyy-mm-dd hh:mm:ss") Timestamp endTime) {
        return this.transactionService.searchTransaction(pageIndex, pageSize, transactionId, functionType, acquirerId, branchId, terminalId, transactionStatus, startTime, endTime);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<TransactionDTO>> getAll() {
        return this.transactionService.getAllTransaction();
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<TransactionDTO> update(@RequestBody(required = true) @Valid TransactionUpdateDTO transactionUpdateDTO) {
        return transactionService.update(transactionUpdateDTO);
    }

    @DELETE
    @Path("/delete/{transactionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Void> delete(@PathParam("transactionId") String transactionId) {
        return this.transactionService.delete(transactionId);
    }
}
