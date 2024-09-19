package it.gov.pagopa.atmlayer.service.consolebackend.utils;

import jakarta.ws.rs.container.ContainerRequestContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtils {

    public static void logOperation(ContainerRequestContext containerRequestContext, String operationName) {
        String userId = HeadersUtils.getUserIdJWT(containerRequestContext);
        log.info("Utente: {} ha eseguito l'operazione: {}", userId, operationName);
    }

}
