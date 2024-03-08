package it.gov.pagopa.atmlayer.service.consolebackend.exception;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.mapper.ModelClientExceptionMapper;
import it.gov.pagopa.atmlayer.service.consolebackend.model.ATMLayerErrorResponse;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
public class ModelClientExceptionMapperTest {

    @InjectMocks
    ModelClientExceptionMapper modelClientExceptionMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


        @Test
    void testClientExceptionMapper(){

        LinkedHashMap testEntity = new LinkedHashMap();
        testEntity.put("type","ERROR_TYPE_TEST");
        testEntity.put("errorCode", "ATMLCB_TEST");
        testEntity.put("statusCode",400);
        testEntity.put("message","Test error message");
        Response testResponse= Mockito.mock(Response.class);
        ClientWebApplicationException clientWebApplicationException= Mockito.mock(ClientWebApplicationException.class);
        when(clientWebApplicationException.getResponse()).thenReturn(testResponse);
        when(testResponse.readEntity(LinkedHashMap.class)).thenReturn(testEntity);
        when(testResponse.getStatus()).thenReturn(400);
        RestResponse<ATMLayerErrorResponse> response = modelClientExceptionMapper.buildErrorResponse(clientWebApplicationException);
        assertEquals("ATMLCB_TEST", response.getEntity().getErrorCode());

    }
}
