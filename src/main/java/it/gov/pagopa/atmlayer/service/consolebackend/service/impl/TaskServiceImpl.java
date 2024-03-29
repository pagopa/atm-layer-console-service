package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.TaskWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.Scene;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.State;
import it.gov.pagopa.atmlayer.service.consolebackend.service.TaskService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Slf4j
public class TaskServiceImpl implements TaskService {


    @Inject
    @RestClient
    TaskWebClient taskWebClient;

    @Override
    public Uni<Scene> createMainScene(State state) {
        return taskWebClient.createMainScene(state);
    }
    @Override
    public Uni<Scene> createNextScene(String transactionId, State state){
        return taskWebClient.createNextScene(transactionId, state);
    }
}
