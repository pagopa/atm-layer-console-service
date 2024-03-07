package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class EppModeTest {

    @Test
    void testEppModeEnum(){
        assertEquals(EppMode.DATA, EppMode.valueOf("DATA"));
        assertEquals(EppMode.SMS, EppMode.valueOf("SMS"));
    }
}
