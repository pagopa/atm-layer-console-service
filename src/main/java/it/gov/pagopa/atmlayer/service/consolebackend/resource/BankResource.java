package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankPresentationDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankUpdateDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BankService;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
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
    public BankResource(BankService bankService, UserService userService) {
        this.bankService = bankService;
        this.userService = userService;
    }

    private final BankService bankService;
    private final UserService userService;

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "insertBank",
            description = "Inserimento banca"
    )
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankPresentationDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<BankPresentationDTO> insert(@Context ContainerRequestContext containerRequestContext,
                                           @RequestBody(required = true) @Valid BankInsertionDTO bankInsertionDTO) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_BANCHE)
                .onItem()
                .transformToUni(voidItem -> this.bankService.insert(bankInsertionDTO, containerRequestContext));
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "updateBank",
            description = "Aggiorna banca"
    )
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Banca aggiornata.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankPresentationDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<BankPresentationDTO> update(@Context ContainerRequestContext containerRequestContext,
                                           @RequestBody(required = true) @Valid BankUpdateDTO bankUpdateDTO) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_BANCHE)
                .onItem()
                .transformToUni(voidItem -> this.bankService.update(bankUpdateDTO, containerRequestContext));
    }

    @POST
    @Path("/disable/{acquirerId}")
    @Operation(
            operationId = "disableBank",
            description = "Disabilita una banca"
    )
    @APIResponse(responseCode = "204", description = "Operazione eseguita con successo.")
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<Void> disable(@Context ContainerRequestContext containerRequestContext,
                             @PathParam("acquirerId") @Schema(format = "byte", maxLength = 255) String acquirerId) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_BANCHE)
                .onItem()
                .transformToUni(voidItem -> this.bankService.disable(acquirerId, containerRequestContext));
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "searchBanks",
            description = "Esegue la GET delle banche filtrando sui campi desiderati gestendo la paginazione"
    )
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}"))
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il processo è terminato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageInfo.class)))
    public Uni<PageInfo<BankDTO>> search(@Context ContainerRequestContext containerRequestContext,
                                         @QueryParam("pageIndex") @DefaultValue("0") @Parameter(required = true, schema = @Schema(minimum = "0", maximum = "10000")) int pageIndex,
                                         @QueryParam("pageSize") @DefaultValue("10") @Parameter(required = true, schema = @Schema(minimum = "1", maximum = "100")) int pageSize,
                                         @QueryParam("acquirerId") @Schema(format = "byte", maxLength = 255) String acquirerId,
                                         @QueryParam("denomination") @Schema(format = "byte", maxLength = 255) String denomination,
                                         @QueryParam("clientId") @Schema(format = "byte", maxLength = 255) String clientId) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_BANCHE)
                .onItem()
                .transformToUni(voidItem -> this.bankService.search(pageIndex, pageSize, acquirerId, denomination, clientId)
                        .onItem()
                        .transform(Unchecked.function(pagedList -> {
                            if (pagedList.getResults().isEmpty()) {
                                log.info("No Bank Entity meets the applied filters");
                            }
                            return pagedList;
                        })));
    }

    @GET
    @Path("/{acquirerId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "getBank",
            description = "Recupera una banca specifica"
    )
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Banca aggiornata.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankPresentationDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<BankPresentationDTO> getBank(@Context ContainerRequestContext containerRequestContext,
                                            @PathParam("acquirerId") @Schema(format = "byte", maxLength = 255) String acquirerId) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_BANCHE)
                .onItem()
                .transformToUni(voidItem -> bankService.findByAcquirerId(acquirerId));
    }
}
