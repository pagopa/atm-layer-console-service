package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class NoDeployableResourceTypeTest {

    @Test
    void testExtensionHTML() {
        assertEquals("html", NoDeployableResourceType.HTML.getExtension());
    }

    @Test
    void testExtensionOTHER() { assertEquals(null, NoDeployableResourceType.OTHER.getExtension()); }

    @Test
    void testMimeTypeHTML() {
        assertEquals("application/html", NoDeployableResourceType.HTML.getMimetype());
    }

    @Test
    void testMimeTypeOTHER() { assertEquals(null, NoDeployableResourceType.OTHER.getMimetype()); }


    @Test
    void testValueOf() {
        assertEquals(NoDeployableResourceType.HTML, NoDeployableResourceType.valueOf("HTML"));
        assertEquals(NoDeployableResourceType.OTHER, NoDeployableResourceType.valueOf("OTHER"));
    }

}