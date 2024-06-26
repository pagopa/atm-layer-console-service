package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class S3ResourceTypeEnumTest {

    @Test
    void testExtensionBPMN() {
        assertEquals("bpmn", S3ResourceTypeEnum.BPMN.getExtension());
    }
    @Test
    void testMimeTypeBPMN() {
        assertEquals("application/bpmn", S3ResourceTypeEnum.BPMN.getMimetype());
    }

    @Test
    void testExtensionDMN() {
        assertEquals("dmn", S3ResourceTypeEnum.DMN.getExtension());
    }
    @Test
    void testMimeTypeDMN() {
        assertEquals("application/dmn", S3ResourceTypeEnum.DMN.getMimetype());
    }

    @Test
    void testExtensionFORM() {
        assertEquals("json", S3ResourceTypeEnum.FORM.getExtension());
    }
    @Test
    void testMimeTypeFORM() {
        assertEquals("application/json", S3ResourceTypeEnum.FORM.getMimetype());
    }

    @Test
    void testExtensionHTML() {
        assertEquals("html", S3ResourceTypeEnum.HTML.getExtension());
    }
    @Test
    void testMimeTypeHTML() {
        assertEquals("application/html", S3ResourceTypeEnum.HTML.getMimetype());
    }

    @Test
    void testExtensionOTHER() {
        assertEquals("other", S3ResourceTypeEnum.OTHER.getExtension());
    }
    @Test
    void testMimeTypeOTHER() {
        assertEquals(null, S3ResourceTypeEnum.OTHER.getMimetype());
    }

    @Test
    void testValueOf() {
        assertEquals(S3ResourceTypeEnum.BPMN, S3ResourceTypeEnum.valueOf("BPMN"));
        assertEquals(S3ResourceTypeEnum.DMN, S3ResourceTypeEnum.valueOf("DMN"));
        assertEquals(S3ResourceTypeEnum.FORM, S3ResourceTypeEnum.valueOf("FORM"));
        assertEquals(S3ResourceTypeEnum.HTML, S3ResourceTypeEnum.valueOf("HTML"));
        assertEquals(S3ResourceTypeEnum.OTHER, S3ResourceTypeEnum.valueOf("OTHER"));
    }
}