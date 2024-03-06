package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.Scene;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.State;
import it.gov.pagopa.atmlayer.service.consolebackend.service.TaskService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

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
    @Operation(summary = "Restituisce la scena principale della funzione selezionata.", description = "CREATE della scena prinicpale con la lista dei task dato l'ID della funzione selezionata.")
    public Uni<Scene> createMainScene(
            @Parameter(description = "Il body della richiesta con lo stato del dispositivo, delle periferiche e dei tesk eseguiti") @NotNull State state) {

        return this.taskService.createMainScene(state)
                .onItem()
                .transformToUni(scene -> Uni.createFrom().item(scene));
    }

    @Path("/next/trns/{transactionId}")
    @POST
    @Operation(summary = "Restituisce la scena successiva con la lista dei task dato l'ID del flusso.", description = "CREATE dello step successivo a quello corrente dato l'ID del flusso.")
    public Uni<Scene> createNextScene(
            @Parameter(description = "ID della transazione") @NotNull @PathParam("transactionId") String transactionId,
            @Parameter(description = "Il body della richiesta con lo stato del dispositivo, delle periferiche e dei tesk eseguiti") @NotNull State state) {

        return this.taskService.createNextScene(transactionId, state)
                .onItem()
                .transformToUni(scene -> Uni.createFrom().item(scene));
    }


}
