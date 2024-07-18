package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfilesDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfilesInsertionDTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "user-profile-client")
public interface UserProfileWebClient {

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<List<UserProfilesDTO>> insert(@RequestBody(required = true) @Valid UserProfilesInsertionDTO userProfilesInsertionDTO);

    @GET
    @Path("/userId/{userId}/profileId/{profileId}")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<UserProfilesDTO> getById(@PathParam("userId") String userId,
                                 @PathParam("profileId") int profileId);

    @DELETE
    @Path("/userId/{userId}/profileId/{profileId}")
    Uni<Void> deleteUserProfiles(@PathParam("userId") String userId,
                                 @PathParam("profileId") int profileId);
}
