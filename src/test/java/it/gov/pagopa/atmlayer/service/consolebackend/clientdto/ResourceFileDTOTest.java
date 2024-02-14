package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.S3ResourceTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ResourceFileDTOTest {
    ResourceFileDTO dto;
    ResourceFileDTO dto1;
    UUID defaultId = UUID.randomUUID();
    Timestamp defaultCreateDate = new Timestamp(System.currentTimeMillis());
    Timestamp defaultUpdateDate = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    void init(){
        dto = new ResourceFileDTO();
        buildDTO(dto);
        dto1 = new ResourceFileDTO();
        buildDTO(dto1);
    }

    private void buildDTO(ResourceFileDTO dto){
        dto.setId(defaultId);
        dto.setCreatedAt(defaultCreateDate);
        dto.setCreatedBy("email@domain.com");
        dto.setExtension("extension");
        dto.setFileName("fileName");
        dto.setLastUpdatedBy("email@domain.com");
        dto.setLastUpdatedAt(defaultUpdateDate);
        dto.setResourceType(S3ResourceTypeEnum.OTHER);
        dto.setStorageKey("storageKey");
    }

    @Test
    void testGettersAndSetters(){
        assertEquals(dto.getId(), defaultId);
        assertEquals(defaultCreateDate, dto.getCreatedAt());
        assertEquals("email@domain.com", dto.getCreatedBy());
        assertEquals("extension", dto.getExtension());
        assertEquals("fileName", dto.getFileName());
        assertEquals("email@domain.com", dto.getLastUpdatedBy());
        assertEquals(defaultUpdateDate, dto.getLastUpdatedAt());
        assertEquals(S3ResourceTypeEnum.OTHER, dto.getResourceType());
        assertEquals("storageKey", dto.getStorageKey());

    }

    @Test
    void testEquals() {
        assertEquals(dto, dto1);
    }

    @Test
    void testHashCode() { assertEquals(dto.hashCode(), dto1.hashCode()); }

    @Test
    void testToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ResourceFileDTO(id=" + dto.getId().toString() + ", ");
        stringBuilder.append("resourceType=OTHER, ");
        stringBuilder.append("storageKey=storageKey, ");
        stringBuilder.append("fileName=fileName, ");
        stringBuilder.append("extension=extension, ");
        stringBuilder.append("createdAt=" + dto.getCreatedAt().toString() + ", ");
        stringBuilder.append("lastUpdatedAt=" + dto.getLastUpdatedAt().toString() + ", ");
        stringBuilder.append("createdBy=email@domain.com, ");
        stringBuilder.append("lastUpdatedBy=email@domain.com)");
        assertEquals(stringBuilder.toString(), dto.toString());
    }
}