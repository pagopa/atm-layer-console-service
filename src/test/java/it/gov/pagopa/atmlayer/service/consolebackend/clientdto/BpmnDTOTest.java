package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class BpmnDTOTest {
    BpmnDTO dto, dto1;
    UUID defaultId = UUID.randomUUID();
    UUID defaultDeploymentId = UUID.randomUUID();
    ResourceFileDTO fileDto = new ResourceFileDTO();
    Timestamp defaultCreateDate = new Timestamp(System.currentTimeMillis());
    Timestamp defaultUpdateDate = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    void init(){
        dto = new BpmnDTO();
        buildDTO(dto);
        dto1 = new BpmnDTO();
        buildDTO(dto1);
    }
    
    private void buildDTO(BpmnDTO dto){
        dto.setBpmnId(defaultId);
        dto.setCreatedAt(defaultCreateDate);
        dto.setCreatedBy("user@domain.com");
        dto.setDefinitionKey("key");
        dto.setDescription("description");
        dto.setCamundaDefinitionId("camundaId");
        dto.setDeploymentId(defaultDeploymentId);
        dto.setDefinitionVersionCamunda(1);
        dto.setDeployedFileName("file.bmpn");
        dto.setDefinitionKey("definitionKey");
        dto.setEnabled(true);
        dto.setFunctionType("function");
        dto.setLastUpdatedAt(defaultUpdateDate);
        dto.setLastUpdatedBy("user1@domain.com");
        dto.setModelVersion(1L);
        dto.setResource("resource");
        dto.setResourceFile(fileDto);
        dto.setStatus(StatusEnum.CREATED);
        dto.setSha256("123456789abcdefgh");
    }

    @Test
    void testGettersAndSetters(){
        assertEquals(defaultId, dto.getBpmnId());
        assertEquals(defaultCreateDate, dto.getCreatedAt());
        assertEquals("user@domain.com", dto.getCreatedBy());
        assertEquals("definitionKey", dto.getDefinitionKey());
        assertEquals("description", dto.getDescription());
        assertEquals("camundaId", dto.getCamundaDefinitionId());
        assertEquals(defaultDeploymentId, dto.getDeploymentId());
        assertEquals(1, dto.getDefinitionVersionCamunda());
        assertEquals("file.bmpn", dto.getDeployedFileName());
        assertEquals("definitionKey", dto.getDefinitionKey());
        assertEquals(true, dto.getEnabled());
        assertEquals("function", dto.getFunctionType());
        assertEquals(defaultUpdateDate, dto.getLastUpdatedAt());
        assertEquals("user1@domain.com", dto.getLastUpdatedBy());
        assertEquals(1L, dto.getModelVersion());
        assertEquals("resource", dto.getResource());
        assertEquals(fileDto, dto.getResourceFile());
        assertEquals(StatusEnum.CREATED, dto.getStatus());
        assertEquals("123456789abcdefgh", dto.getSha256());
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

    @Test
    void testAllArgsConstructor(){
        ResourceFileDTO resourceFileDTO=new ResourceFileDTO();
        BpmnDTO bpmnDTO=new BpmnDTO(
                UUID.randomUUID(),
                1L, "deployedFileName","definitionKey","functionType",
                StatusEnum.DEPLOYED,"sha256",true,1,"camundaDefinitionId",
                "description", resourceFileDTO, "resource", UUID.randomUUID(),
                Timestamp.valueOf("2018-09-01 09:01:15"),Timestamp.valueOf("2018-09-01 09:01:15"),
                "createdBy","lastUpdatedBy");
        assertNotNull(bpmnDTO);
    }

    @Test
    void testBuilder(){
        BpmnDTO bpmnDTO = BpmnDTO.builder()
                .bpmnId(UUID.randomUUID())
                .modelVersion(1L)
                .build();
        assertNotNull(bpmnDTO);
    }

    @Test
    void testToString(){
        BpmnDTO bpmnDTO = new BpmnDTO();
        String bpmDTOstring=bpmnDTO.toString();
        assertNotNull(bpmDTOstring);
    }
}