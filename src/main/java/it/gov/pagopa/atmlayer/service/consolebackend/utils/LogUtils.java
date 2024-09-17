package it.gov.pagopa.atmlayer.service.consolebackend.utils;

import jakarta.ws.rs.container.ContainerRequestContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtils {

    public static void logOperation(ContainerRequestContext containerRequestContext, String operationName) {
        String email = HeadersUtils.getEmailJWT(containerRequestContext);
        log.info("Utente: {} ha eseguito l'operazione: {}", email, operationName);
    }

}
