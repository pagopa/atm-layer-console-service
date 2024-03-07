package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.HashMap;

import static it.gov.pagopa.atmlayer.service.consolebackend.utils.HeadersUtils.getEmailJWT;

@Path("/info")
@Tag(name = "Info", description = "Info")
@Slf4j
@ApplicationScoped
@SecuritySchemes({
        @SecurityScheme(securitySchemeName = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
})
@SecurityRequirement(name = "bearerAuth")
public class InfoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/email")
    public Uni<HashMap<String, String>> getTokenEmail(@Context ContainerRequestContext containerRequestContext) {
        String email="";
        try {
            email = getEmailJWT(containerRequestContext);
        }catch (Exception e){
            email= "utente@pagopa.it";
        }
        HashMap<String, String> emailMap = new HashMap<>();
        emailMap.put("email", email);
        return Uni.createFrom().item(emailMap);
    }

}
