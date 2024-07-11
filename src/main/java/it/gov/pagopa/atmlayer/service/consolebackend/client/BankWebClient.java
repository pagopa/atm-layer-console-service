package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "bank-client")
public interface BankWebClient {

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<BankDTO> insert(@RequestBody(required = true) @Valid BankInsertionDTO bankInsertionDTO);

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<BankDTO> update(@RequestBody(required = true) @Valid BankInsertionDTO bankInsertionDTO);

    @POST
    @Path("/disable/{acquirerId}")
    Uni<Void> disable(@PathParam("acquirerId") String acquirerId);

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<PageInfo<BankDTO>> search(@QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                  @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                  @QueryParam("acquirerId") String acquirerId,
                                  @QueryParam("denomination") String denomination,
                                  @QueryParam("rateMin") int rateMin,
                                  @QueryParam("rateMax") int rateMax,
                                  @QueryParam("clientId") String clientId);

}
