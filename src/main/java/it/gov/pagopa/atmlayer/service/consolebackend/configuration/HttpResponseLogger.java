package it.gov.pagopa.atmlayer.service.consolebackend.configuration;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

@Provider
@Slf4j
public class HttpResponseLogger implements ContainerResponseFilter {
    public void logResponse(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        try {
            log.info("====================================response received with transactionId= {}, Status code  :  {}, Status message  :  {}, Headers  :  {}", requestContext.getHeaderString("TransactionId"), responseContext.getStatus(), responseContext.getStatusInfo().getReasonPhrase(), responseContext.getHeaders());
        } catch (Exception e) {
            log.info("====================================response received with transactionId= {}, Status code  :  {}, Status message  :  {}, Headers  :  {}", requestContext.getHeaderString("TransactionId"), responseContext.getStatus(), responseContext.getStatusInfo().getReasonPhrase(), responseContext.getHeaders());
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        logResponse(requestContext, responseContext);
        LocalDateTime timestampEnd = LocalDateTime.now();
        log.info("Request finished at : {}", timestampEnd);
        long duration = Duration.between((Temporal) requestContext.getProperty("timestampStart"), timestampEnd).toMillis();
        log.info("Request duration : {}ms", duration);
    }
}
