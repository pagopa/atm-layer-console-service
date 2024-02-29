package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class BpmnBankConfigDTOTest {
    BpmnBankConfigDTO dto, dto1;
    UUID defaultId = UUID.randomUUID();
    Timestamp defaultCreateDate = new Timestamp(System.currentTimeMillis());
    Timestamp defaultUpdateDate = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    void init(){
        dto = new BpmnBankConfigDTO();
        buildDTO(dto);
        dto1 = new BpmnBankConfigDTO();
        buildDTO(dto1);
    }

    private void buildDTO(BpmnBankConfigDTO dto){
        dto.setBpmnId(defaultId);
        dto.setBpmnModelVersion(1L);
        dto.setAcquirerId("acquirerId");
        dto.setBranchId("branchId");
        dto.setTerminalId("terminalId");
        dto.setFunctionType("functionType");
        dto.setCreatedAt(defaultCreateDate);
        dto.setLastUpdatedAt(defaultUpdateDate);
        dto.setCreatedBy("user@domain.com");
        dto.setLastUpdatedBy("user@domain.com");
    }

    @Test
    void testGettersAndSetters(){
        assertEquals(defaultId, dto.getBpmnId());
        assertEquals(1L, dto.getBpmnModelVersion());
        assertEquals("acquirerId", dto.getAcquirerId());
        assertEquals("branchId", dto.getBranchId());
        assertEquals("terminalId", dto.getTerminalId());
        assertEquals("functionType", dto.getFunctionType());
        assertEquals(defaultCreateDate, dto.getCreatedAt());
        assertEquals(defaultUpdateDate, dto.getLastUpdatedAt());
        assertEquals("user@domain.com", dto.getCreatedBy());
        assertEquals("user@domain.com", dto.getLastUpdatedBy());
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