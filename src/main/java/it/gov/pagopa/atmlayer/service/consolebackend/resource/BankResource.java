package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankPresentationDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankUpdateDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BankService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@ApplicationScoped
@Path("/banks")
@Tag(name = "Bank", description = "Bank Resource Proxy")
@Slf4j
@SecuritySchemes({
        @SecurityScheme(securitySchemeName = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
})
@SecurityRequirement(name = "bearerAuth")
public class BankResource {

    @Inject
    public BankResource(BankService bankService) {
        this.bankService = bankService;
    }

    private final BankService bankService;

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<BankPresentationDTO> insert(@RequestBody(required = true) @Valid BankInsertionDTO bankInsertionDTO) {
        return this.bankService.insert(bankInsertionDTO);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<BankPresentationDTO> update(@RequestBody(required = true) @Valid BankUpdateDTO bankUpdateDTO) {
        return this.bankService.update(bankUpdateDTO);
    }

    @POST
    @Path("/disable/{acquirerId}")
    public Uni<Void> disable(@PathParam("acquirerId") String acquirerId) {
        return this.bankService.disable(acquirerId);
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PageInfo<BankDTO>> search(@QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                         @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                         @QueryParam("acquirerId") String acquirerId,
                                         @QueryParam("denomination") String denomination,
                                         @QueryParam("clientId") String clientId) {
        return this.bankService.search(pageIndex, pageSize, acquirerId, denomination, clientId)
                .onItem()
                .transform(Unchecked.function(pagedList -> {
                    if (pagedList.getResults().isEmpty()) {
                        log.info("No Bank Entity meets the applied filters");
                    }
                    return pagedList;
                }));
    }

    @GET
    @Path("/{acquirerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<BankPresentationDTO> getBank(@PathParam("acquirerId") String acquirerId) {
        return bankService.findByAcquirerId(acquirerId);
    }

}
