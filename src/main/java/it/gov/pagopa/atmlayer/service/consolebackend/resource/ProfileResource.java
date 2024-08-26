package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ProfileDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.service.ProfileService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/profile")
@Tag(name = "Profiles", description = "Profiles proxy")
@Slf4j
@ApplicationScoped
@SecuritySchemes({
        @SecurityScheme(securitySchemeName = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
})
@SecurityRequirement(name = "bearerAuth")
public class ProfileResource {

    @Inject
    ProfileService profileService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "getAllProfiles",
            description = "Recupera tutti i profili"
    )
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "4XX", description = "Bad Request", content = @Content(example = "{\"type\":\"BAD_REQUEST\", \"statusCode\":\"4XX\", \"message\":\"Messaggio di errore\", \"errorCode\":\"ATMLCB_4000XXX\"}"))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(example = "{\"type\":\"GENERIC\", \"statusCode\":\"500\", \"message\":\"Si è verificato un errore imprevisto, vedere i log per ulteriori informazioni\", \"errorCode\":\"ATMLCB_500\"}"))
    public Uni<List<ProfileDTO>> getAll() {
        return this.profileService.getAll();
    }
}
