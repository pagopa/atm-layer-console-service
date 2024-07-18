package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class UserProfileEnumTest {

    @Test
    void testGetValue() {
        assertEquals(1, UserProfileEnum.READ_GESTIONE_FLUSSI.getValue());
        assertEquals(2, UserProfileEnum.WRITE_GESTIONE_FLUSSI.getValue());
        assertEquals(3, UserProfileEnum.DEPLOY_BPMN.getValue());
        assertEquals(4, UserProfileEnum.EMULATOR.getValue());
        assertEquals(5, UserProfileEnum.GESTIONE_UTENTI.getValue());
    }

    @Test
    void testValueOf() {
        assertEquals(UserProfileEnum.READ_GESTIONE_FLUSSI, UserProfileEnum.valueOf(1));
        assertEquals(UserProfileEnum.WRITE_GESTIONE_FLUSSI, UserProfileEnum.valueOf(2));
        assertEquals(UserProfileEnum.DEPLOY_BPMN, UserProfileEnum.valueOf(3));
        assertEquals(UserProfileEnum.EMULATOR, UserProfileEnum.valueOf(4));
        assertEquals(UserProfileEnum.GESTIONE_UTENTI, UserProfileEnum.valueOf(5));
    }
}