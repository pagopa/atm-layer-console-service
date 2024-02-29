package it.gov.pagopa.atmlayer.service.consolebackend.exception;


import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.CompositeException;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorCodeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.mapper.GlobalExceptionMapperImpl;
import it.gov.pagopa.atmlayer.service.consolebackend.model.ATMLayerErrorResponse;
import it.gov.pagopa.atmlayer.service.consolebackend.model.ATMLayerValidationErrorResponse;
import it.gov.pagopa.atmlayer.service.consolebackend.utils.ConstraintViolationMappingUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.common.jaxrs.ResponseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
class GlobalExceptionMapperImplTest {

    @Mock
    Logger logger;

    @Mock
    ConstraintViolationMappingUtils constraintViolationMappingUtils;

    @InjectMocks
    GlobalExceptionMapperImpl globalExceptionMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConstraintViolationExceptionMapper() {
        String message = "Message";
        HashSet<ConstraintViolation<?>> constraintViolations = new HashSet<>();
        ConstraintViolationException exception = new ConstraintViolationException(message, constraintViolations);
        RestResponse<ATMLayerValidationErrorResponse> response = globalExceptionMapper.constraintViolationExceptionMapper(exception);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

    }

    @Test
    void testGenericExceptionMapperException() {
        Exception exception = new RuntimeException("Test exception");
        RestResponse<ATMLayerErrorResponse> response = globalExceptionMapper.genericExceptionMapper(exception);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    void testGenericExceptionMapperAtmLayerException(){
        AtmLayerException atmLayerException=new AtmLayerException(Response.Status.BAD_REQUEST, it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorCodeEnum.ATMLCB_401);
        RestResponse<ATMLayerErrorResponse> response = globalExceptionMapper.genericExceptionMapper(atmLayerException);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus());
        assertEquals("ATMLCB_401",response.getEntity().getErrorCode());
        assertEquals("UNAUTHORIZED", response.getEntity().getType());
    }

    @Test
    void testClientExceptionMapper(){

        LinkedHashMap testEntity = new LinkedHashMap();
        testEntity.put("type","ERROR_TYPE_TEST");
        testEntity.put("errorCode", "ATMLCB_TEST");
        testEntity.put("statusCode",400);
        testEntity.put("message","Test error message");
        Response testResponse= Mockito.mock(Response.class);
        ClientWebApplicationException clientWebApplicationException=Mockito.mock(ClientWebApplicationException.class);
        when(clientWebApplicationException.getResponse()).thenReturn(testResponse);
        when(testResponse.readEntity(LinkedHashMap.class)).thenReturn(testEntity);
        when(testResponse.getStatus()).thenReturn(400);
        RestResponse<ATMLayerErrorResponse> response = globalExceptionMapper.clientExceptionMapper(clientWebApplicationException);
        assertEquals("ATMLCB_TEST", response.getEntity().getErrorCode());

    }

    
}
