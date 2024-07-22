package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnVersionFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserInsertionWithProfilesDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.UUID;

@RegisterRestClient(configKey = "user-client")
public interface UserWebClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Uni<PageInfo<BpmnVersionFrontEndDTO>> getUserFiltered(@QueryParam("pageIndex") @DefaultValue("0")
                                                          @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                          @QueryParam("pageSize") @DefaultValue("10")
                                                          @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                                          @QueryParam("name") String name,
                                                          @QueryParam("surname") String surname,
                                                          @QueryParam("userId") String userId,
                                                          @QueryParam("profileId") int profileId);

    @POST
    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<UserDTO> createUser(@RequestBody(required = true) @Valid UserInsertionDTO userInsertionDTO);

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

    @POST
    @Path("/first-access/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<UserDTO> firstAccess(@PathParam("userId") String userId);

    @PUT
    @Path("/update-with-profiles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<UserDTO> updateWithProfiles(@RequestBody(required = true) @Valid UserInsertionWithProfilesDTO userInsertionWithProfilesDTO);

    @POST
    @Path("/insert-with-profiles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<UserDTO> insertWithProfiles(@RequestBody(required = true) @Valid UserInsertionWithProfilesDTO userInsertionWithProfilesDTO);
}
