package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.Scene;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.State;
import it.gov.pagopa.atmlayer.service.consolebackend.service.TaskService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/task")
@Tag(name = "TASK", description = "TASK proxy")
@Slf4j
@ApplicationScoped
@SecuritySchemes({
        @SecurityScheme(securitySchemeName = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
})
@SecurityRequirement(name = "bearerAuth")
public class TaskResource {

    @Inject
    TaskService taskService;

    @Path("/main")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Restituisce la scena principale della funzione selezionata.", description = "CREATE della scena prinicpale con la lista dei task dato l'ID della funzione selezionata.")
    public Uni<Scene> createMainScene(
            @RequestBody(name = "state", description = "Il body della richiesta con lo stato del dispositivo, delle periferiche e dei tesk eseguiti") @NotNull State state) {

        return this.taskService.createMainScene(state);
    }

    @Path("/next/trns/{transactionId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Restituisce la scena successiva con la lista dei task dato l'ID del flusso.", description = "CREATE dello step successivo a quello corrente dato l'ID del flusso.")
    public Uni<Scene> createNextScene(
            @Parameter(name = "transactionId", description = "ID della transazione") @NotNull @PathParam("transactionId") String transactionId,
            @RequestBody(name = "state", description = "Il body della richiesta con lo stato del dispositivo, delle periferiche e dei tesk eseguiti") @NotNull State state) {

        return this.taskService.createNextScene(transactionId, state);
    }


}
