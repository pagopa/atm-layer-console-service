package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.Scene;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.State;
import it.gov.pagopa.atmlayer.service.consolebackend.model.ATMLayerErrorResponse;
import it.gov.pagopa.atmlayer.service.consolebackend.service.TaskService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
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
    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }
    private final TaskService taskService;

    @Path("/main")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Restituisce la scena principale della funzione selezionata.", description = "CREATE della scena prinicpale con la lista dei task dato l'ID della funzione selezionata.")
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il processo è terminato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Scene.class)))
    @APIResponse(responseCode = "201", description = "Operazione eseguita con successo. Restituisce l'oggetto Task nel body della risposta.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Scene.class)))
    @APIResponse(responseCode = "202", description = "Operazione eseguita con successo. Il processo è in esecuzione.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Scene.class)))
    @APIResponse(responseCode = "209", description = "Errore durante l'elaborazione del flusso della funzione.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ATMLayerErrorResponse.class)))
    @APIResponse(responseCode = "400", description = "Richiesta malformata, la descrizione può fornire dettagli sull'errore.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ATMLayerErrorResponse.class)))
    @APIResponse(responseCode = "500", description = "Errore generico, la descrizione può fornire dettagli sull'errore.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ATMLayerErrorResponse.class)))
    public Uni<Response> createMainScene(
            @RequestBody(name = "state", description = "Il body della richiesta con lo stato del dispositivo, delle periferiche e dei tesk eseguiti") @NotNull State state) {

        return this.taskService.createMainScene(state);
    }

    @Path("/next/trns/{transactionId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Restituisce la scena successiva con la lista dei task dato l'ID del flusso.", description = "CREATE dello step successivo a quello corrente dato l'ID del flusso.")
    @APIResponse(responseCode = "200", description = "Operazione eseguita con successo. Il processo è terminato.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Scene.class)))
    @APIResponse(responseCode = "201", description = "Operazione eseguita con successo. Restituisce l'oggetto Task nel body della risposta.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Scene.class)))
    @APIResponse(responseCode = "202", description = "Operazione eseguita con successo. Il processo è in esecuzione.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Scene.class)))
    @APIResponse(responseCode = "209", description = "Errore durante l'elaborazione del flusso della funzione.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ATMLayerErrorResponse.class)))
    @APIResponse(responseCode = "400", description = "Richiesta malformata, la descrizione può fornire dettagli sull'errore.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ATMLayerErrorResponse.class)))
    @APIResponse(responseCode = "500", description = "Errore generico, la descrizione può fornire dettagli sull'errore.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ATMLayerErrorResponse.class)))
    public Uni<Response> createNextScene(
            @Parameter(name = "transactionId", description = "ID della transazione") @NotNull @PathParam("transactionId") String transactionId,
            @RequestBody(name = "state", description = "Il body della richiesta con lo stato del dispositivo, delle periferiche e dei tesk eseguiti") @NotNull State state) {

        return this.taskService.createNextScene(transactionId, state);
    }


}
