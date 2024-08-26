package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionUpdateDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.sql.Timestamp;
import java.util.List;

@RegisterRestClient(configKey = "transaction-client")
public interface TransactionWebClient {
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<TransactionDTO> insert(@RequestBody(required = true) @Valid TransactionInsertionDTO transactionInsertionDTO);

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<PageInfo<TransactionDTO>> searchTransaction(@QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                                @QueryParam("transactionId") String transactionId,
                                                @QueryParam("functionType") String functionType,
                                                @QueryParam("acquirerId") String acquirerId,
                                                @QueryParam("branchId") String branchId,
                                                @QueryParam("terminalId") String terminalId,
                                                @QueryParam("transactionStatus") String transactionStatus,
                                                @QueryParam("startTime") @Schema(example = "yyyy-mm-dd hh:mm:ss") Timestamp startTime,
                                                @QueryParam("endTime") @Schema(example = "yyyy-mm-dd hh:mm:ss") Timestamp endTime);

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<TransactionDTO> update(@RequestBody(required = true) @Valid TransactionUpdateDTO transactionUpdateDTO);

    @DELETE
    @Path("/delete/{transactionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Void> delete(@PathParam("transactionId") String transactionId);
}
