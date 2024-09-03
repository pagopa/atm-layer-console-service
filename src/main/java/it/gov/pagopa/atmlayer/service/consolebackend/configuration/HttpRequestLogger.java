package it.gov.pagopa.atmlayer.service.consolebackend.configuration;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.owasp.encoder.Encode;

import java.util.Map;
import java.util.List;

@Provider
@Slf4j
public class HttpRequestLogger implements ContainerRequestFilter {

    public void logRequest(ContainerRequestContext requestContext) {
        String uri = requestContext.getUriInfo().getAbsolutePath() != null
                ? Encode.forJava(requestContext.getUriInfo().getAbsolutePath().toString())
                : null;

        String method = requestContext.getMethod();

        Map<String, List<String>> headersMap = requestContext.getHeaders();

        headersMap.remove("Authorization");

        String headers = Encode.forJava(headersMap.toString());

        log.info("====================================request started with, URI : {}, Method : {}, Headers  :  {}", uri, method, headers);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        logRequest(requestContext);
    }
}
