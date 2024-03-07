package it.gov.pagopa.atmlayer.service.consolebackend.exception;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorCodeEnum;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class AtmLayerExceptionTest {

    @Test
    void testExceptionWithThrowable() {
        Throwable throwable = new RuntimeException("Test error");
        AtmLayerException exception = AtmLayerException.builder().error(throwable).build();

        assertNotNull(exception);
        assertEquals("Test error", exception.getMessage());
        assertEquals(AppErrorCodeEnum.ATMLCB_500.getType().name(), exception.getType());
        assertEquals(500, exception.getStatusCode());
        assertEquals(AppErrorCodeEnum.ATMLCB_500.getErrorCode(), exception.getErrorCode());
        assertEquals(throwable, exception.getCause());
    }

    @Test
    void testAtmLayerExceptionConstructor() {
        String message = "Test message";
        Response.Status status = Response.Status.BAD_REQUEST;
        String type = "Test type";

        AtmLayerException atmLayerException = new AtmLayerException(message, status, type);

        assertEquals(message, atmLayerException.getMessage());
        assertEquals(type, atmLayerException.getType());
        assertEquals(status.getStatusCode(), atmLayerException.getStatusCode());
    }
}