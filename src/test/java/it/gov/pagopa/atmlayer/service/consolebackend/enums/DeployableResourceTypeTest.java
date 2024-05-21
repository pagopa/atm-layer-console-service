package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class DeployableResourceTypeTest {

    @Test
    void testTagNameBPMN() {
        assertEquals("bpmn:process", DeployableResourceType.BPMN.getTagName());
    }
    @Test
    void testAttributeBPMN() {
        assertEquals("id", DeployableResourceType.BPMN.getAttribute());
    }

    @Test
    void testTagNameDMN() {
        assertEquals("decision", DeployableResourceType.DMN.getTagName());
    }
    @Test
    void testAttributeDMN() {
        assertEquals("id", DeployableResourceType.DMN.getAttribute());
    }

    @Test
    void testTagNameFORM() {
        assertEquals("id", DeployableResourceType.FORM.getTagName());
    }
    @Test
    void testAttributeFORM() {
        assertEquals(null, DeployableResourceType.FORM.getAttribute());
    }

    @Test
    void testValueOf() {
        assertEquals(DeployableResourceType.BPMN, DeployableResourceType.valueOf("BPMN"));
        assertEquals(DeployableResourceType.DMN, DeployableResourceType.valueOf("DMN"));
        assertEquals(DeployableResourceType.FORM, DeployableResourceType.valueOf("FORM"));
    }
}