package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import it.gov.pagopa.atmlayer.service.consolebackend.client.BpmnWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.client.TaskWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.Scene;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.State;
import it.gov.pagopa.atmlayer.service.consolebackend.service.TaskService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;

@ApplicationScoped
@Slf4j
public class TaskServiceImpl implements TaskService {


    @Inject
    @RestClient
    TaskWebClient taskWebClient;

    @Override
    public RestResponse<Scene> createMainScene(State state) {
        return taskWebClient.createMainScene(state);
    }
}
