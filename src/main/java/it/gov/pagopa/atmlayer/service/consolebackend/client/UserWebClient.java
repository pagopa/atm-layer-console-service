package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.quarkus.arc.NoClassInterceptors;
import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfileDto;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.AtmLayerExceptionDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "user-client")
public interface UserWebClient {

    @NoClassInterceptors
    @ClientExceptionMapper
    static RuntimeException clientErrorException(Response response) {
        if (response.getStatus() >= 400 && response.getStatus() < 500) {
            AtmLayerExceptionDTO responseData=response.readEntity(AtmLayerExceptionDTO.class);
            return new RuntimeException(responseData.getMessage());
        }
        return new RuntimeException("Unmapped client error, see logs for details");
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<UserProfileDto> findByUserId(@NotNull @QueryParam("userId") String userId);
}
