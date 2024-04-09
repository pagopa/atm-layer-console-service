package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.Scene;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.State;
import jakarta.ws.rs.core.Response;

public interface TaskService {

    Uni<Response> createMainScene(State state);

    Uni<Response> createNextScene(String transactionId, State state);

}
