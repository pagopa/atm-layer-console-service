package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.model.CognitoToken;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "cognito-client")
public interface CognitoWebClient {

    @Path("/oauth2/token")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Uni<CognitoToken> createToken(@FormParam("grant_type") String grantType, @FormParam("scope") String scope, @HeaderParam("Authorization") String authorization);

}
