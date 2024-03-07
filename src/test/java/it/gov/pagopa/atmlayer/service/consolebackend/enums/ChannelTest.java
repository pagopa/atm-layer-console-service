package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class ChannelTest {

    @Test
    void testChannelEnum(){
        assertEquals(Channel.ATM, Channel.valueOf("ATM"));
        assertEquals(Channel.KIOSK, Channel.valueOf("KIOSK"));
    }
}
