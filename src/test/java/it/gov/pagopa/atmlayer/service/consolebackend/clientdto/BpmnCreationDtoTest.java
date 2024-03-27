package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class BpmnCreationDtoTest {
    BpmnCreationDto dto, dto1;
    File file = new File("src/test/resources/Test.bpmn");

    @BeforeEach
    public void init(){
        dto = new BpmnCreationDto();
        buildDTO(dto);
        dto1 = new BpmnCreationDto();
        buildDTO(dto1);
    }

    private void buildDTO(BpmnCreationDto dto) {
        dto.setFile(file);
        dto.setFunctionType("functionType");
        dto.setFilename("Test.bpmn");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(file, dto.getFile());
        assertEquals("functionType", dto.getFunctionType());
        assertEquals("Test.bpmn", dto.getFilename());
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