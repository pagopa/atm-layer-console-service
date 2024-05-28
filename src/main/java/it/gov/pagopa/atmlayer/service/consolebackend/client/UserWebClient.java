package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "user-client")
public interface UserWebClient {

    @POST
    @Path("/insert/userId/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<UserDTO> createUser(@PathParam("userId") String userId);

    @DELETE
    @Path("/delete/userId/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Void> delete(@PathParam("userId") String userId);

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<UserDTO> getById(@PathParam("userId") String userId);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Uni<List<UserDTO>> getAll();
}
