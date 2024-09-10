package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfilesDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfilesInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserProfileService;
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
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/user_profiles")
@Tag(name = "User Profiles")
@Slf4j
@ApplicationScoped
@SecuritySchemes({
        @SecurityScheme(securitySchemeName = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
})
@SecurityRequirement(name = "bearerAuth")
public class UserProfileResource {

    @Inject
    public UserProfileResource(UserProfileService userProfileService, UserService userService) {
        this.userProfileService = userProfileService;
        this.userService = userService;
    }

    private final UserProfileService userProfileService;
    private final UserService userService;

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "insertUserProfile", description = "Salva sul database le associazioni tra profilo e user e le restituisce")
    @APIResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfilesDTO.class)))
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<List<UserProfilesDTO>> insert(@Context ContainerRequestContext containerRequestContext,
                                             @RequestBody(required = true) @Valid UserProfilesInsertionDTO userProfilesInsertionDTO) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_UTENTI)
                .onItem()
                .transformToUni(voidItem -> this.userProfileService.insertUserProfiles(userProfilesInsertionDTO));
    }

//    @GET
//    @Path("/userId/{userId}/profileId/{profileId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Operation(operationId = "getUserProfile", description = "Restituisce lo user e il profilo associato")
//    @APIResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfilesDTO.class)))
//    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
//    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
//    public Uni<UserProfilesDTO> getById(@Context ContainerRequestContext containerRequestContext,
//                                        @PathParam("userId") @Schema(format = "byte", maxLength = 255) String userId,
//                                        @PathParam("profileId") @Schema(example = "5", minimum = "1", maximum = "30") int profileId) {
//        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_UTENTI)
//                .onItem()
//                .transformToUni(voidItem -> this.userProfileService.findById(userId, profileId));
//    }

//    @DELETE
//    @Path("/userId/{userId}/profileId/{profileId}")
//    @Operation(
//            operationId = "deleteUserProfile",
//            description = "Cancella lo userProfile dal database"
//    )
//    @APIResponse(responseCode = "204", description = "Ok")
//    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLM_4000XXX\"}"))
//    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
//    public Uni<Void> deleteUserProfiles(@Context ContainerRequestContext containerRequestContext,
//                                        @PathParam("userId") @Schema(format = "byte", maxLength = 255) String userId,
//                                        @PathParam("profileId") @Schema(example = "5", minimum = "1", maximum = "30") int profileId) {
//        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_UTENTI)
//                .onItem()
//                .transformToUni(voidItem -> this.userProfileService.deleteUserProfiles(userId, profileId));
//    }
}
