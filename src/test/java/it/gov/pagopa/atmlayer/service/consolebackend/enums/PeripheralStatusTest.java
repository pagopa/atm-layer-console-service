package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class PeripheralStatusTest {

    @Test
    void testPeripheralStatusEnum(){
        assertEquals(PeripheralStatus.OK, PeripheralStatus.valueOf("OK"));
        assertEquals(PeripheralStatus.WARNING, PeripheralStatus.valueOf("WARNING"));
        assertEquals(PeripheralStatus.KO, PeripheralStatus.valueOf("KO"));
    }
}
