package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(StatusEnum.valueOf("CREATED"), StatusEnum.CREATED);
        assertEquals(StatusEnum.valueOf("WAITING_DEPLOY"), StatusEnum.WAITING_DEPLOY);
        assertEquals(StatusEnum.valueOf("UPDATED_BUT_NOT_DEPLOYED"), StatusEnum.UPDATED_BUT_NOT_DEPLOYED);
        assertEquals(StatusEnum.valueOf("DEPLOYED"), StatusEnum.DEPLOYED);
        assertEquals(StatusEnum.valueOf("DEPLOY_ERROR"), StatusEnum.DEPLOY_ERROR);
    }
}