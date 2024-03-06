package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.Scene;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.State;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestResponse;

@RegisterRestClient(configKey = "task-client")
public interface TaskWebClient {

    @Path("/main")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Scene> createMainScene(@Parameter @NotNull  State state);

    @Path("/next/trns/{transactionId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Scene> createNextScene(
            @Parameter @NotNull @PathParam("transactionId") String transactionId,
            @Parameter @NotNull State state);

}
