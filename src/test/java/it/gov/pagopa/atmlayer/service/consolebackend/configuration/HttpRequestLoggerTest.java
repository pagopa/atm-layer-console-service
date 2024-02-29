package it.gov.pagopa.atmlayer.service.consolebackend.configuration;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.net.URI;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
public class HttpRequestLoggerTest {

    @Mock
    ContainerRequestContext requestContext;

    @Mock
    UriInfo uriInfo;

    @InjectMocks
    HttpRequestLogger httpRequestLogger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLogRequest() {
        Logger logger = mock(Logger.class);
        when(requestContext.getUriInfo()).thenReturn(uriInfo);
        when(uriInfo.getAbsolutePath()).thenReturn(URI.create("test-uri"));
        when(requestContext.getMethod()).thenReturn("GET");
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        when(requestContext.getHeaders()).thenReturn(headers);
        httpRequestLogger.logRequest(requestContext);
    }

    @Test
    public void testFilter() {
        Logger logger = mock(Logger.class);
        when(requestContext.getUriInfo()).thenReturn(uriInfo);
        when(requestContext.getUriInfo().getAbsolutePath()).thenReturn(URI.create("test-uri"));
        when(requestContext.getMethod()).thenReturn("GET");
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        httpRequestLogger.filter(requestContext);
    }
}
