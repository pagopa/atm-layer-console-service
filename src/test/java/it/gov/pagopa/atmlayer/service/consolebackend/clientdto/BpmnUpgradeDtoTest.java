package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class BpmnUpgradeDtoTest {
    BpmnUpgradeDto dto, dto1;
    File file = new File("src/test/resources/Test.bpmn");
    UUID defaultId = UUID.randomUUID();

    @BeforeEach
    void init(){
        dto = new BpmnUpgradeDto();
        buildDTO(dto);
        dto1 = new BpmnUpgradeDto();
        buildDTO(dto1);
    }

    private void buildDTO(BpmnUpgradeDto dto){
        dto.setFile(file);
        dto.setFunctionType("functionType");
        dto.setFilename("Test.bpmn");
        dto.setUuid(defaultId);
    }

    @Test
    void testGettersAndSetters(){
        assertEquals("functionType", dto.getFunctionType());
        assertEquals("Test.bpmn", dto.getFilename());
        assertEquals(file, dto.getFile());
        assertEquals(defaultId, dto.getUuid());
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