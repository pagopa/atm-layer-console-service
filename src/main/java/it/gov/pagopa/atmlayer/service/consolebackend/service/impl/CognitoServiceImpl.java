package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.BpmnWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.client.CognitoWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.model.CognitoToken;
import it.gov.pagopa.atmlayer.service.consolebackend.service.CognitoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Slf4j
public class CognitoServiceImpl implements CognitoService {

    @Inject
    @RestClient
    CognitoWebClient cognitoWebClient;

    @Override
    public Uni<CognitoToken> createToken(){
        return cognitoWebClient.createToken("client_credentials", "dev/tasks", "Basic Aggiungere qui il basicAuth per Cognito");
    }

}
