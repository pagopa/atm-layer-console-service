package it.gov.pagopa.atmlayer.service.consolebackend.configuration;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Provider
@Slf4j
public class HttpRequestLogger implements ContainerRequestFilter {
    public void logRequest(ContainerRequestContext requestContext) {
        URI uri = requestContext.getUriInfo().getAbsolutePath();
        String method = requestContext.getMethod();
        MultivaluedMap<String, String> headers = requestContext.getHeaders();
        log.info("====================================request started with, URI : {}, Method : {}, Headers  :  {}", uri, method, headers);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        logRequest(requestContext);
    }
}
