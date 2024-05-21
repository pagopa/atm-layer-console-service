package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class StatusEnumTest {

    @Test
    void testUpdatableAndDeletableStatuses() {
        assertTrue(StatusEnum.getUpdatableAndDeletableStatuses().contains(StatusEnum.CREATED));
        assertTrue(StatusEnum.getUpdatableAndDeletableStatuses().contains(StatusEnum.DEPLOY_ERROR));
    }

    @Test
    void testIsEditable() {
        assertTrue(StatusEnum.isEditable(StatusEnum.CREATED));
        assertTrue(StatusEnum.isEditable(StatusEnum.DEPLOY_ERROR));
    }

    @Test
    void testValueOf() {
        assertEquals(StatusEnum.CREATED, StatusEnum.valueOf("CREATED"));
        assertEquals(StatusEnum.WAITING_DEPLOY, StatusEnum.valueOf("WAITING_DEPLOY"));
        assertEquals(StatusEnum.UPDATED_BUT_NOT_DEPLOYED, StatusEnum.valueOf("UPDATED_BUT_NOT_DEPLOYED"));
        assertEquals(StatusEnum.DEPLOYED, StatusEnum.valueOf("DEPLOYED"));
        assertEquals(StatusEnum.DEPLOY_ERROR, StatusEnum.valueOf("DEPLOY_ERROR"));
    }
}