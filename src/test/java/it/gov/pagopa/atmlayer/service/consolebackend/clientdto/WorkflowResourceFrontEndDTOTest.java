package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.S3ResourceTypeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
    void testGettersAndSetters(){
        assertEquals(dto.getWorkflowResourceId(), defaultId);
        assertEquals(dto.getDeployedFileName(), "fileName");
        assertEquals(dto.getDefinitionKey(), "definitionKey");
        assertEquals(dto.getStatus(), StatusEnum.CREATED);
        assertEquals(dto.getSha256(), "abc123456");
        assertEquals(dto.getDefinitionVersionCamunda(), 1);
        assertEquals(dto.getCamundaDefinitionId(), "camunda");
        assertEquals(dto.getDescription(), "description");
        assertEquals(dto.getResourceId(), defaultResourceId);
        assertEquals(dto.getResourceS3Type(), S3ResourceTypeEnum.BPMN);;
        assertEquals(dto.getStorageKey(), "storageKey");
        assertEquals(dto.getFileName(), "fileName");
        assertEquals(dto.getExtension(), "extension");
        assertEquals(dto.getResourceCreatedAt(), defaultCreateDate);
        assertEquals(dto.getResourceLastUpdatedAt(), defaultUpdateDate);
        assertEquals(dto.getResourceCreatedBy(), "username@domain.com");
        assertEquals(dto.getResourceLastUpdatedBy(), "username@domain.com");
        assertEquals(dto.getResource(), "resource");
        assertEquals(dto.getResourceType(), DeployableResourceType.BPMN);
        assertEquals(dto.getDeploymentId(), defaultDeploymentId);
        assertEquals(dto.getCreatedAt(), defaultCreateDate);
        assertEquals(dto.getCreatedBy(), "username.domain.com");
        assertEquals(dto.getLastUpdatedBy(), "username.domain.com");
        assertEquals(dto.getLastUpdatedAt(), defaultUpdateDate);
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
}