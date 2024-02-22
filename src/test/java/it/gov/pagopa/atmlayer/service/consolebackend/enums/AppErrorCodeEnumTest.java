package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class AppErrorCodeEnumTest {

    @Test
    void testGetError500Code() {
        assertEquals("ATMLCB_500", AppErrorCodeEnum.ATMLCB_500.getErrorCode());
    }

    @Test
    void testGetError500Message() {
        assertEquals("An unexpected error has occurred, see logs for more info", AppErrorCodeEnum.ATMLCB_500.getErrorMessage());
    }

    @Test
    void testGetError500Type() {
        assertEquals("GENERIC", AppErrorCodeEnum.ATMLCB_500.getType().name());
    }

    @Test
    void testGetError401Code() {
        assertEquals("ATMLCB_401", AppErrorCodeEnum.ATMLCB_401.getErrorCode());
    }

    @Test
    void testGetError401Message() {
        assertEquals("Unauthorized", AppErrorCodeEnum.ATMLCB_401.getErrorMessage());
    }

    @Test
    void testGetError401Type() {
        assertEquals("UNAUTHORIZED", AppErrorCodeEnum.ATMLCB_401.getType().name());
    }

    @Test
    void testValueOf() {
        assertEquals(AppErrorCodeEnum.valueOf("ATMLCB_500"), AppErrorCodeEnum.ATMLCB_500);
        assertEquals(AppErrorCodeEnum.valueOf("ATMLCB_401"), AppErrorCodeEnum.ATMLCB_401);
    }
}