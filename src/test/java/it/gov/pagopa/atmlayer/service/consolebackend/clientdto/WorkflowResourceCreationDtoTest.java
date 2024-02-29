package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class WorkflowResourceCreationDtoTest {
    WorkflowResourceCreationDto dto, dto1;
    File file = new File("src/test/resources/Test.bpmn");

    @BeforeEach
    public void init(){
        dto = new WorkflowResourceCreationDto();
        buildDTO(dto);
        dto1 = new WorkflowResourceCreationDto();
        buildDTO(dto1);
    }

    private void buildDTO(WorkflowResourceCreationDto dto) {
        dto.setFile(file);
        dto.setFilename("Test.bpmn");
        dto.setResourceType(DeployableResourceType.BPMN);
        dto.setDescription("description");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(file, dto.getFile());
        assertEquals("description", dto.getDescription());
        assertEquals("Test.bpmn", dto.getFilename());
        assertEquals(DeployableResourceType.BPMN, dto.getResourceType());
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