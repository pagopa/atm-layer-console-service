package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

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
    public UserResource(UserService userService){
        this.userService = userService;
    }

    private final UserService userService;


    @POST
    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)
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
    public Uni<Void> delete(@Context ContainerRequestContext containerRequestContext,
                            @PathParam("userId") String userId) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_UTENTI)
                .onItem()
                .transformToUni(voidItem -> this.userService.deleteUser(userId));
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<UserDTO> getUserById(@Context ContainerRequestContext containerRequestContext,
                                    @PathParam("userId") String userId) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_UTENTI)
                .onItem()
                .transformToUni(voidItem -> this.userService.getUserById(userId));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<UserDTO>> getAll(@Context ContainerRequestContext containerRequestContext) {
        return userService.checkAuthorizationUser(containerRequestContext, UserProfileEnum.GESTIONE_UTENTI)
                .onItem()
                .transformToUni(voidItem -> this.userService.getAllUsers());
    }
}
