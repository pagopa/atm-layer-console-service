package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserInsertionWithProfilesDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
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

import java.util.List;

import static it.gov.pagopa.atmlayer.service.consolebackend.utils.HeadersUtils.getEmailJWT;

@Path("/user")
@Tag(name = "User")
@Slf4j
@ApplicationScoped
@SecuritySchemes({
        @SecurityScheme(securitySchemeName = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
})
@SecurityRequirement(name = "bearerAuth")
public class UserResource {

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;


    @POST
    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "insertUser", description = "Salva sul database lo user e lo restituisce")
    @APIResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<UserDTO> insert(@Context ContainerRequestContext containerRequestContext,
                               @RequestBody(required = true) @Valid UserInsertionDTO userInsertionDTO) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_UTENTI)
                .onItem()
                .transformToUni(voidItem -> this.userService.createUser(userInsertionDTO));
    }

    @DELETE
    @Path("/delete/userId/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "deleteUser",
            description = "Elimina User"
    )
    @APIResponse(responseCode = "204", description = "Ok")
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<Void> delete(@Context ContainerRequestContext containerRequestContext,
                            @PathParam("userId") @Schema(format = "byte", maxLength = 255) String userId) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_UTENTI)
                .onItem()
                .transformToUni(voidItem -> this.userService.deleteUser(userId));
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getUserById", description = "Esegue la query sul database restituisce lo user trovato tramite i valori di input")
    @APIResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<UserDTO> getUserById(@Context ContainerRequestContext containerRequestContext,
                                    @PathParam("userId") @Schema(format = "byte", maxLength = 255) String userId) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_UTENTI)
                .onItem()
                .transformToUni(voidItem -> this.userService.getUserById(userId));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "getAllUsers",
            description = "Recupera tutti gli user"
    )
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<List<UserDTO>> getAll(@Context ContainerRequestContext containerRequestContext) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_UTENTI)
                .onItem()
                .transformToUni(voidItem -> this.userService.getAllUsers());
    }

    @GET
    @Path("/filtered")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getUserFiltered",
            description = "Esegue la GET degli User sul model filtrando sui campi desiderati gestendo la paginazione"
    )
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Recuperati Utenti cercati.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageInfo.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"An unexpected error has occurred, see logs for more info\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<PageInfo<UserDTO>> getUserFiltered(@QueryParam("pageIndex") @DefaultValue("0")
                                                  @Parameter(required = true, schema = @Schema(minimum = "0", maximum = "100000")) int pageIndex,
                                                  @QueryParam("pageSize") @DefaultValue("10")
                                                  @Parameter(required = true, schema = @Schema(minimum = "1", maximum = "100")) int pageSize,
                                                  @QueryParam("name") @Schema(format = "byte", maxLength = 255) String name,
                                                  @QueryParam("surname") @Schema(format = "byte", maxLength = 255) String surname,
                                                  @QueryParam("userId") @Schema(format = "byte", maxLength = 255) String userId) {
        return this.userService.getUserFiltered(pageIndex, pageSize, name, surname, userId)
                .onItem()
                .transform(Unchecked.function(pagedList -> {
                    if (pagedList.getResults().isEmpty()) {
                        log.info("No User record meets the applied filters");
                    }
                    return pagedList;
                }));
    }

    @POST
    @Path("/first-access")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "firstAccess", description = "Salva sul database lo user e lo restituisce, se è il primo utente viene automaticamente aggiunto il ruolo di gestione utenti")
    @APIResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<UserDTO> firstAccess(@Context ContainerRequestContext containerRequestContext) {

        return this.userService.checkFirstAccess(getEmailJWT(containerRequestContext));
    }

    @PUT
    @Path("/update-with-profiles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "updateWithProfiles", description = "Salva sul database lo user aggiornato e lo restituisce, questa operazione comprende sia i campi dell'entità User che Profile")
    @APIResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<UserDTO> updateWithProfiles(@RequestBody(required = true) @Valid UserInsertionWithProfilesDTO userInsertionWithProfilesDTO) {
        return this.userService.updateWithProfiles(userInsertionWithProfilesDTO);
    }

    @POST
    @Path("/insert-with-profiles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "insertWithProfiles", description = "Salva sul database lo user creato e lo restituisce, questa operazione comprende sia i campi dell'entità User che Profile")
    @APIResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<UserDTO> insertWithProfiles(@RequestBody(required = true) @Valid UserInsertionWithProfilesDTO userInsertionWithProfilesDTO) {
        return this.userService.insertWithProfiles(userInsertionWithProfilesDTO);
    }
}
