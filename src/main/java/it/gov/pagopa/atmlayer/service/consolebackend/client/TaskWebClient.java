package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.Scene;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.State;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "task-client")
public interface TaskWebClient {

    @Path("/main")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<Response> createMainScene(@Parameter @NotNull  State state);

    @Path("/next/trns/{transactionId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<Response> createNextScene(
            @Parameter @NotNull @PathParam("transactionId") String transactionId,
            @Parameter @NotNull State state);

}
