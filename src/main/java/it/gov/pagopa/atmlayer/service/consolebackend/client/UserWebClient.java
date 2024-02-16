package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "user-client")
public interface UserWebClient {

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<UserProfileDto> findByUserId(@NotNull @QueryParam("userId") String userId);
}
