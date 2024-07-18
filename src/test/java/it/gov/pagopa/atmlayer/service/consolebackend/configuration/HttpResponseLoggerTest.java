package it.gov.pagopa.atmlayer.service.consolebackend.configuration;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.wildfly.common.Assert.assertTrue;

public class HttpResponseLoggerTest {

    @Mock
    ContainerRequestContext requestContext;

    @Mock
    ContainerResponseContext responseContext;

    @InjectMocks
    HttpResponseLogger httpResponseLogger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLogResponse() {
        when(responseContext.getStatus()).thenReturn(200);
        when(responseContext.getStatusInfo()).thenReturn(new Response.StatusType() {
            @Override
            public int getStatusCode() {
                return 200;
            }

            @Override
            public Response.Status.Family getFamily() {
                return Response.Status.Family.SUCCESSFUL;
            }

            @Override
            public String getReasonPhrase() {
                return "OK";
            }
        });
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        when(responseContext.getHeaders()).thenReturn(headers);
        httpResponseLogger.logResponse(responseContext);
        assertTrue(!headers.containsKey("momentaneo"));
    }
}
