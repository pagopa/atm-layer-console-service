package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.S3ResourceTypeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class WorkflowResourceFrontEndDTOTest {
    WorkflowResourceFrontEndDTO dto;
    WorkflowResourceFrontEndDTO dto1;
    UUID defaultId = UUID.randomUUID();
    UUID defaultResourceId = UUID.randomUUID();
    UUID defaultDeploymentId = UUID.randomUUID();
    Timestamp defaultCreateDate = new Timestamp(System.currentTimeMillis());
    Timestamp defaultUpdateDate = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    void init(){
        dto = new WorkflowResourceFrontEndDTO();
        buildDTO(dto);
        dto1 = new WorkflowResourceFrontEndDTO();
        buildDTO(dto1);
    }

    private void buildDTO(WorkflowResourceFrontEndDTO dto){
        dto.setWorkflowResourceId(defaultId);
        dto.setDeployedFileName("fileName");
        dto.setDefinitionKey("definitionKey");
        dto.setStatus(StatusEnum.CREATED);
        dto.setSha256("abc123456");
        dto.setDefinitionVersionCamunda(1);
        dto.setCamundaDefinitionId("camunda");
        dto.setDescription("description");
        dto.setResourceId(defaultResourceId);
        dto.setResourceS3Type(S3ResourceTypeEnum.BPMN);
        dto.setStorageKey("storageKey");
        dto.setFileName("fileName");
        dto.setExtension("extension");
        dto.setResourceCreatedAt(defaultCreateDate);
        dto.setResourceLastUpdatedAt(defaultUpdateDate);
        dto.setResourceCreatedBy("username@domain.com");
        dto.setResourceLastUpdatedBy("username@domain.com");
        dto.setResource("resource");
        dto.setResourceType(DeployableResourceType.BPMN);
        dto.setDeploymentId(defaultDeploymentId);
        dto.setCreatedAt(defaultCreateDate);
        dto.setCreatedBy("username.domain.com");
        dto.setLastUpdatedBy("username.domain.com");
        dto.setLastUpdatedAt(defaultUpdateDate);
    }
    @Test
    void testGettersAndSetters() {
        assertEquals(defaultId, dto.getWorkflowResourceId());
        assertEquals("fileName", dto.getDeployedFileName());
        assertEquals("definitionKey", dto.getDefinitionKey());
        assertEquals(StatusEnum.CREATED, dto.getStatus());
        assertEquals("abc123456", dto.getSha256());
        assertEquals(1, dto.getDefinitionVersionCamunda());
        assertEquals("camunda", dto.getCamundaDefinitionId());
        assertEquals("description", dto.getDescription());
        assertEquals(defaultResourceId, dto.getResourceId());
        assertEquals(S3ResourceTypeEnum.BPMN, dto.getResourceS3Type());
        assertEquals("storageKey", dto.getStorageKey());
        assertEquals("fileName", dto.getFileName());
        assertEquals("extension", dto.getExtension());
        assertEquals(defaultCreateDate, dto.getResourceCreatedAt());
        assertEquals(defaultUpdateDate, dto.getResourceLastUpdatedAt());
        assertEquals("username@domain.com", dto.getResourceCreatedBy());
        assertEquals("username@domain.com", dto.getResourceLastUpdatedBy());
        assertEquals("resource", dto.getResource());
        assertEquals(DeployableResourceType.BPMN, dto.getResourceType());
        assertEquals(defaultDeploymentId, dto.getDeploymentId());
        assertEquals(defaultCreateDate, dto.getCreatedAt());
        assertEquals("username.domain.com", dto.getCreatedBy());
        assertEquals("username.domain.com", dto.getLastUpdatedBy());
        assertEquals(defaultUpdateDate, dto.getLastUpdatedAt());
    }

    @Test
    void testEquals() {
        assertEquals(dto, dto1);
    }

    @Test
    void testHashCode() {
        assertEquals(dto.hashCode(), dto1.hashCode());
    }

    @Test
    void testToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WorkflowResourceFrontEndDTO(workflowResourceId=" + dto. getWorkflowResourceId() + ", ");
        stringBuilder.append("deployedFileName=fileName, ");
        stringBuilder.append("definitionKey=definitionKey, ");
        stringBuilder.append("status=CREATED, ");
        stringBuilder.append("sha256=abc123456, ");
        stringBuilder.append("definitionVersionCamunda=1, ");
        stringBuilder.append("camundaDefinitionId=camunda, ");
        stringBuilder.append("description=description, ");
        stringBuilder.append("resourceId=" + dto.getResourceId() + ", ");
        stringBuilder.append("resourceS3Type=BPMN, ");
        stringBuilder.append("storageKey=storageKey, ");
        stringBuilder.append("fileName=fileName, ");
        stringBuilder.append("extension=extension, ");
        stringBuilder.append("resourceCreatedAt=" + defaultCreateDate.toString() + ", ");
        stringBuilder.append("resourceLastUpdatedAt=" + defaultUpdateDate.toString() + ", ");
        stringBuilder.append("resourceCreatedBy=username@domain.com, ");
        stringBuilder.append("resourceLastUpdatedBy=username@domain.com, ");
        stringBuilder.append("resource=resource, ");
        stringBuilder.append("resourceType=BPMN, ");
        stringBuilder.append("deploymentId=" + defaultDeploymentId + ", ");
        stringBuilder.append("createdAt=" + defaultCreateDate.toString() + ", ");
        stringBuilder.append("lastUpdatedAt=" + defaultUpdateDate.toString() + ", ");
        stringBuilder.append("createdBy=username.domain.com, ");
        stringBuilder.append("lastUpdatedBy=username.domain.com)");
        assertEquals(stringBuilder.toString(), dto.toString());
    }

    @Test
    void testBuilder(){
        WorkflowResourceDTO workflowResourceDTO = WorkflowResourceDTO.builder()
                .workflowResourceId(UUID.randomUUID())
                .build();
        assertNotNull(workflowResourceDTO);
    }

    @Test
    void testAllArgsConstructor(){
        WorkflowResourceFrontEndDTO workflowResourceFrontEndDTO = new WorkflowResourceFrontEndDTO(
                UUID.randomUUID(), "deployedFileName","definitionKey",StatusEnum.DEPLOYED,
                "sha256",1,"camundaDefinitionId",
                "description", UUID.randomUUID(), S3ResourceTypeEnum.BPMN, "storageKey", "fileName", "extension",
                Timestamp.valueOf("2018-09-01 09:01:15"),Timestamp.valueOf("2018-09-01 09:01:15"),
                "resourceCreatedBy","resourceLastUpdatedBy", "resource", DeployableResourceType.BPMN, UUID.randomUUID(),
                Timestamp.valueOf("2018-09-01 09:01:15"),Timestamp.valueOf("2018-09-01 09:01:15"),
                "createdBy", "lastUpdatedBy");
        assertNotNull(workflowResourceFrontEndDTO);
    }

    @Test
    void testBuilderAnnotation(){
        WorkflowResourceFrontEndDTO workflowResourceFrontEndDTO = WorkflowResourceFrontEndDTO.builder()
                .workflowResourceId(UUID.randomUUID())
                .build();
        assertNotNull(workflowResourceFrontEndDTO);
    }
}