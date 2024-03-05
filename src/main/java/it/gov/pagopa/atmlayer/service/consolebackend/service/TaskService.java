package it.gov.pagopa.atmlayer.service.consolebackend.service;

import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.Scene;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.State;
import org.jboss.resteasy.reactive.RestResponse;

public interface TaskService {

    RestResponse<Scene> createMainScene(State state);

}
