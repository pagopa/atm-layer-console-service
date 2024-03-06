package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.Scene;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.State;

public interface TaskService {

    Uni<Scene> createMainScene(State state);

    Uni<Scene> createNextScene(String transactionId, State state);

}
