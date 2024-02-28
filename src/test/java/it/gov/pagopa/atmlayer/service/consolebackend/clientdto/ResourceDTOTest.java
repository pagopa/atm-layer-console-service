package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@QuarkusTest
class ResourceDTOTest {
    ResourceDTO dto, dto1;
    UUID defaultId = UUID.randomUUID();
    ResourceFileDTO resourceFileDTO = mock(ResourceFileDTO.class);
    Timestamp defaultCreateDate = new Timestamp(System.currentTimeMillis());
    Timestamp defaultUpdateDate = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    public void init(){
        dto = new ResourceDTO();
        buildDTO(dto);
        dto1 = new ResourceDTO();
        buildDTO(dto1);
    }

    private void buildDTO(ResourceDTO dto) {
        dto.setResourceId(defaultId);
        dto.setSha256("sha256");
        dto.setEnabled(true);
        dto.setNoDeployableResourceType(NoDeployableResourceType.OTHER);
        dto.setCreatedAt(defaultCreateDate);
        dto.setLastUpdatedAt(defaultUpdateDate);
        dto.setCreatedBy("user@domain.com");
        dto.setLastUpdatedBy("user@domain.com");
        dto.setCdnUrl("url");
        dto.setResourceFile(resourceFileDTO);
        dto.setDescription("description");
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
        assertEquals(resourceFileDTO, dto.getResourceFile());
        assertEquals("description", dto.getDescription());
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