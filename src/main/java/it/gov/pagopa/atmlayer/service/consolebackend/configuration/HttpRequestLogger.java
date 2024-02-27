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
        log.info("====================================request started with, URI : {}, Method : {}, Headers  :  {}", requestContext.getUriInfo().getAbsolutePath(), requestContext.getMethod(), requestContext.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        logRequest(requestContext);
    }
}
