package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.TaskWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto.State;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    TaskWebClient taskWebClient;

    @InjectMocks
    TaskServiceImpl taskServiceImpl;

    @Test
    void testCreateMainScene() {
        State state = new State();
        Response expectedResponse = Response.ok().build();
        when(taskWebClient.createMainScene(any(State.class))).thenReturn(Uni.createFrom().item(expectedResponse));

        Uni<Response> result = taskServiceImpl.createMainScene(state);

        assertEquals(expectedResponse.getStatus(), result.await().indefinitely().getStatus());
        verify(taskWebClient).createMainScene(state);
    }

    @Test
    void testCreateNextScene() {
        String transactionId = "test-transaction-id";
        State state = new State();
        Response expectedResponse = Response.ok().build();
        when(taskWebClient.createNextScene(anyString(), any(State.class))).thenReturn(Uni.createFrom().item(expectedResponse));

        Uni<Response> result = taskServiceImpl.createNextScene(transactionId, state);

        assertEquals(expectedResponse.getStatus(), result.await().indefinitely().getStatus());
        verify(taskWebClient).createNextScene(transactionId, state);
    }
}
