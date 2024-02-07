package it.gov.pagopa.atmlayer.service.consolebackend.configuration;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Provider
@Slf4j
public class HttpRequestLogger implements ContainerRequestFilter {
    public void logRequest(ContainerRequestContext requestContext) {
        log.info("====================================request started with Authorization= {}, URI : {}, Method : {}, Headers  :  {}", requestContext.getHeaderString("Authorization"), requestContext.getUriInfo().getAbsolutePath(), requestContext.getMethod(), requestContext.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        LocalDateTime timestampStart = LocalDateTime.now();
        log.info("Request started at : {}", timestampStart);
        requestContext.setProperty("timestampStart",timestampStart);
        logRequest(requestContext);
    }
}
