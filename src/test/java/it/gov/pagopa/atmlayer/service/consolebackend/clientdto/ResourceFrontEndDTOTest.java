package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.S3ResourceTypeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class ResourceFrontEndDTOTest {
    ResourceFrontEndDTO dto, dto1;
    UUID defaultId = UUID.randomUUID();
    UUID defaultFileId = UUID.randomUUID();
    Timestamp defaultCreateDate = new Timestamp(System.currentTimeMillis());
    Timestamp defaultUpdateDate = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    void init(){
        dto = new ResourceFrontEndDTO();
        buildDTO(dto);
        dto1 = new ResourceFrontEndDTO();
        buildDTO(dto1);
    }

    private void buildDTO(ResourceFrontEndDTO dto) {
        dto.setResourceId(defaultId);
        dto.setSha256("sha256");
        dto.setEnabled(true);
        dto.setNoDeployableResourceType(NoDeployableResourceType.OTHER);
        dto.setCreatedAt(defaultCreateDate);
        dto.setLastUpdatedAt(defaultUpdateDate);
        dto.setCreatedBy("user@domain.com");
        dto.setLastUpdatedBy("user@domain.com");
        dto.setCdnUrl("url");
        dto.setResourceFileId(defaultFileId);
        dto.setResourceType(S3ResourceTypeEnum.BPMN);
        dto.setStorageKey("key");
        dto.setFileName("fileName");
        dto.setExtension("extension");
        dto.setResourceFileCreatedAt(defaultCreateDate);
        dto.setResourceFileLastUpdatedAt(defaultUpdateDate);
        dto.setResourceFileCreatedBy("user@domain.com");
        dto.setResourceFileLastUpdatedBy("user@domain.com");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(defaultId, dto.getResourceId());
        assertEquals("sha256", dto.getSha256());
        assertEquals(true, dto.getEnabled());
        assertEquals(NoDeployableResourceType.OTHER, dto.getNoDeployableResourceType());
        assertEquals(defaultCreateDate, dto.getCreatedAt());
        assertEquals(defaultUpdateDate, dto.getLastUpdatedAt());
        assertEquals("user@domain.com", dto.getCreatedBy());
        assertEquals("user@domain.com", dto.getLastUpdatedBy());
        assertEquals("url", dto.getCdnUrl());
        assertEquals(defaultFileId, dto.getResourceFileId());
        assertEquals(S3ResourceTypeEnum.BPMN, dto.getResourceType());
        assertEquals("key", dto.getStorageKey());
        assertEquals("fileName", dto.getFileName());
        assertEquals("extension", dto.getExtension());
        assertEquals(defaultCreateDate, dto.getResourceFileCreatedAt());
        assertEquals(defaultUpdateDate, dto.getResourceFileLastUpdatedAt());
        assertEquals("user@domain.com", dto.getResourceFileCreatedBy());
        assertEquals("user@domain.com", dto.getResourceFileLastUpdatedBy());
    }

    @Test
    void testAllArgsConstructor(){
        ResourceFrontEndDTO resourceFrontEndDTO = new ResourceFrontEndDTO(
                UUID.randomUUID(), "sha256", true, NoDeployableResourceType.HTML,
                Timestamp.valueOf("2018-09-01 09:01:15"), Timestamp.valueOf("2018-09-01 09:01:15"),
                "createdBy", "lastUpdatedBy", "cdnUrl",
                UUID.randomUUID(), S3ResourceTypeEnum.HTML, "storageKey", "fileName", "extension",
                Timestamp.valueOf("2018-09-01 09:01:15"),Timestamp.valueOf("2018-09-01 09:01:15"),
                "resourceFileCreatedBy","resourceFileLastUpdatedBy");
        assertNotNull(resourceFrontEndDTO);
    }
}