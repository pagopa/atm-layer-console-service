package it.gov.pagopa.atmlayer.service.consolebackend.configuration;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

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
        Logger logger = mock(Logger.class);
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
        when(responseContext.getHeaders()).thenReturn(new MultivaluedHashMap<>());
        httpResponseLogger.logResponse(requestContext, responseContext);

        verify(logger).info(argThat(argument ->
                argument.contains("Response Status: 200 OK") &&
                        argument.contains("Headers: {}")
        ));
    }
}
