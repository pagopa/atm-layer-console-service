package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class ResourceCreationDtoTest {
    ResourceCreationDto dto, dto1;
    File file = new File("src/test/resources/Test.bpmn");

    @BeforeEach
    public void init(){
        dto = new ResourceCreationDto();
        buildDTO(dto);
        dto1 = new ResourceCreationDto();
        buildDTO(dto1);
    }

    private void buildDTO(ResourceCreationDto dto) {
        dto.setFile(file);
        dto.setFilename("Test.bpmn");
        dto.setDescription("description");
        dto.setPath("path");
        dto.setResourceType(NoDeployableResourceType.OTHER);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(file, dto.getFile());
        assertEquals("description", dto.getDescription());
        assertEquals("Test.bpmn", dto.getFilename());
        assertEquals("path", dto.getPath());
        assertEquals(NoDeployableResourceType.OTHER, dto.getResourceType());
    }

    @Test
    void testEquals() {
        assertEquals(dto1, dto);
    }

    @Test
    void testHashCode() {
        int hashCodeDto = dto.hashCode();
        int hashCodeDto1 = dto1.hashCode();
        assertEquals(hashCodeDto, hashCodeDto1);
    }
}