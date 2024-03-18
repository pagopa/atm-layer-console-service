package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.model.CognitoToken;

public interface CognitoService {

    Uni<CognitoToken> createToken();

}
