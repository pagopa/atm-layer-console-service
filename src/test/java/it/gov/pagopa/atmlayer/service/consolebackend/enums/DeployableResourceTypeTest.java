package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(DeployableResourceType.valueOf("BPMN"), DeployableResourceType.BPMN);
        assertEquals(DeployableResourceType.valueOf("DMN"), DeployableResourceType.DMN);
        assertEquals(DeployableResourceType.valueOf("FORM"), DeployableResourceType.FORM);
    }
}