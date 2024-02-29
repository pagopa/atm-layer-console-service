package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.S3ResourceTypeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class BpmnVersionFrontEndDTOTest {

    BpmnVersionFrontEndDTO dto;
    BpmnVersionFrontEndDTO dto1;
    UUID defaultId = UUID.randomUUID();
    UUID defaultDeploymentId = UUID.randomUUID();
    UUID defaultResourceId = UUID.randomUUID();
    Timestamp defaultCreateDate = new Timestamp(System.currentTimeMillis());
    Timestamp defaultUpdateDate = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    void init(){
        dto = new BpmnVersionFrontEndDTO();
        buildDTO(dto);
        dto1 = new BpmnVersionFrontEndDTO();
        buildDTO(dto1);
    }

    private void buildDTO(BpmnVersionFrontEndDTO dto){
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
        dto.setExtension("extension");
        dto.setFunctionType("function");
        dto.setLastUpdatedAt(defaultUpdateDate);
        dto.setLastUpdatedBy("user1@domain.com");
        dto.setModelVersion(1L);
        dto.setResource("resource");
        dto.setResourceId(defaultResourceId);
        dto.setResourceCreatedBy("email@domain.com");
        dto.setResourceType(S3ResourceTypeEnum.BPMN);
        dto.setResourceCreatedAt(defaultCreateDate);
        dto.setResourceLastUpdatedBy("username@domain.com");
        dto.setResourceLastUpdatedAt(defaultUpdateDate);
        dto.setStorageKey("storageKey");
        dto.setStatus(StatusEnum.CREATED);
        dto.setSha256("123456789abcdefgh");
    }

    @Test
    void testGettersAndSetters() {
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
        assertEquals("extension", dto.getExtension());
        assertEquals("function", dto.getFunctionType());
        assertEquals(defaultUpdateDate, dto.getLastUpdatedAt());
        assertEquals("user1@domain.com", dto.getLastUpdatedBy());
        assertEquals(1L, dto.getModelVersion());
        assertEquals("resource", dto.getResource());
        assertEquals(defaultResourceId, dto.getResourceId());
        assertEquals("email@domain.com", dto.getResourceCreatedBy());
        assertEquals(S3ResourceTypeEnum.BPMN, dto.getResourceType());
        assertEquals(defaultCreateDate, dto.getResourceCreatedAt());
        assertEquals("username@domain.com", dto.getResourceLastUpdatedBy());
        assertEquals(defaultUpdateDate, dto.getResourceLastUpdatedAt());
        assertEquals("storageKey", dto.getStorageKey());
        assertEquals(StatusEnum.CREATED, dto.getStatus());
        assertEquals("123456789abcdefgh", dto.getSha256());
    }

    @Test
    void testEquals() {
        assertEquals(dto, dto1);
    }

    @Test
    void testHashCode() {
        int hashCodeDto = dto.hashCode();
        int hashCodeDto1 = dto1.hashCode();
        assertEquals(hashCodeDto, hashCodeDto1);
    }

    @Test
    void testAllArgsConstructor(){
        BpmnVersionFrontEndDTO bpmnVersionFrontEndDTO=new BpmnVersionFrontEndDTO(
                UUID.randomUUID(),
                1L, "deployedFileName","definitionKey","functionType",
                StatusEnum.DEPLOYED,"sha256",true,1,"camundaDefinitionId",
                "description", UUID.randomUUID(), S3ResourceTypeEnum.BPMN, "storageKey", "fileName", "extension",
                Timestamp.valueOf("2018-09-01 09:01:15"),Timestamp.valueOf("2018-09-01 09:01:15"),
                "resourceCreatedBy","resourceLastUpdatedBy", "resource", UUID.randomUUID(),
                Timestamp.valueOf("2018-09-01 09:01:15"),Timestamp.valueOf("2018-09-01 09:01:15"),
                "createdBy", "lastUpdatedBy");
        assertNotNull(bpmnVersionFrontEndDTO);
    }

    @Test
    void testBuilder(){
        BpmnVersionFrontEndDTO bpmnVersionFrontEndDTO = BpmnVersionFrontEndDTO.builder()
                .bpmnId(UUID.randomUUID())
                .build();
        assertNotNull(bpmnVersionFrontEndDTO);
    }

    @Test
    void testToString(){
        BpmnVersionFrontEndDTO bpmnVersionFrontEndDTO = new BpmnVersionFrontEndDTO();
        String bpmnVersionFrontEndString = bpmnVersionFrontEndDTO.toString();
        assertNotNull(bpmnVersionFrontEndString);
    }
}