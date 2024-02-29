package it.gov.pagopa.atmlayer.service.consolebackend.configuration;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.MultivaluedMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

@QuarkusTest
public class CorsFilterTest {

    @InjectMocks
    CorsFilter corsFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFilter() throws IOException {
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        ContainerResponseContext responseContext = mock(ContainerResponseContext.class);

        MultivaluedMap<String, Object> headers = mock(MultivaluedMap.class);
        when(responseContext.getHeaders()).thenReturn(headers);
        corsFilter.filter(requestContext, responseContext);

        verify(responseContext.getHeaders()).add("Access-Control-Allow-Origin", "*");
        verify(responseContext.getHeaders()).add("Access-Control-Allow-Headers", "*");
        verify(responseContext.getHeaders()).add("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        verify(responseContext.getHeaders()).add("Content-Type", "application/json; application/octet-stream");

        // Add more assertions if needed
    }
}
