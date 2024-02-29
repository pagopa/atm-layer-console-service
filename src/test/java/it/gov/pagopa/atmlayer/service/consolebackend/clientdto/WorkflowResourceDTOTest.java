package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class WorkflowResourceDTOTest {
    WorkflowResourceDTO dto, dto1;
    ResourceFileDTO fileDto = new ResourceFileDTO();
    UUID defaultId = UUID.randomUUID();
    UUID defaultDeployId = UUID.randomUUID();
    Timestamp defaultCreateDate = new Timestamp(System.currentTimeMillis());
    Timestamp defaultUpdateDate = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    void init(){
        dto = new WorkflowResourceDTO();
        buildDTO(dto);
        dto1 = new WorkflowResourceDTO();
        buildDTO(dto1);
    }

    private void buildDTO(WorkflowResourceDTO dto) {
        dto.setWorkflowResourceId(defaultId);
        dto.setDeployedFileName("fileName");
        dto.setDefinitionKey("key");
        dto.setStatus(StatusEnum.CREATED);
        dto.setSha256("sha256");
        dto.setDefinitionVersionCamunda(1);
        dto.setCamundaDefinitionId("camundaId");
        dto.setDescription("description");
        dto.setResourceFile(fileDto);
        dto.setResource("resource");
        dto.setResourceType(DeployableResourceType.BPMN);
        dto.setDeploymentId(defaultDeployId);
        dto.setCreatedAt(defaultCreateDate);
        dto.setLastUpdatedAt(defaultUpdateDate);
        dto.setCreatedBy("user@domain.com");
        dto.setLastUpdatedBy("user@domain.com");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(defaultId, dto.getWorkflowResourceId());
        assertEquals("fileName", dto.getDeployedFileName());
        assertEquals("key", dto.getDefinitionKey());
        assertEquals(StatusEnum.CREATED, dto.getStatus());
        assertEquals("sha256", dto.getSha256());
        assertEquals(1, dto.getDefinitionVersionCamunda());
        assertEquals("camundaId", dto.getCamundaDefinitionId());
        assertEquals("description", dto.getDescription());
        assertEquals(fileDto, dto.getResourceFile());
        assertEquals("resource", dto.getResource());
        assertEquals(DeployableResourceType.BPMN, dto.getResourceType());
        assertEquals(defaultDeployId, dto.getDeploymentId());
        assertEquals(defaultCreateDate, dto.getCreatedAt());
        assertEquals(defaultUpdateDate, dto.getLastUpdatedAt());
        assertEquals("user@domain.com", dto.getCreatedBy());
        assertEquals("user@domain.com", dto.getLastUpdatedBy());
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
}