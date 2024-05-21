package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class UserProfileDtoTest {

    UserProfileDto dto;
    UserProfileDto dto1;
    Timestamp defaultCreateDate = new Timestamp(System.currentTimeMillis());
    Timestamp defaultUpdateDate = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    void setUp() {
        dto = new UserProfileDto();
        buildDTO(dto);
        dto1 = new UserProfileDto();
        buildDTO(dto1);
    }

    private void buildDTO(UserProfileDto dto){
        dto.setUserId("id12345");
        dto.setProfile(UserProfileEnum.ADMIN);
        dto.setVisible(true);
        dto.setEditable(true);
        dto.setAdmin(true);
        dto.setCreatedAt(defaultCreateDate);
        dto.setLastUpdatedAt(defaultUpdateDate);
    }

    @Test
    void testGettersAndSetters(){
        assertEquals("id12345", dto.getUserId());
        assertEquals(UserProfileEnum.ADMIN, dto.getProfile());
        assertEquals(true, dto.getVisible());
        assertEquals(true, dto.getEditable());
        assertEquals(true, dto.getAdmin());
        assertEquals(defaultCreateDate, dto.getCreatedAt());
        assertEquals(defaultUpdateDate, dto.getLastUpdatedAt());
    }

    @Test
    void testEquals() { assertEquals(dto, dto1); }

    @Test
    void testHashCode() {assertEquals(dto.hashCode(), dto1.hashCode()); }

    @Test
    void testToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UserProfileDto(userId=id12345, ");
        stringBuilder.append("profile=ADMIN, ");
        stringBuilder.append("visible=true, ");
        stringBuilder.append("editable=true, ");
        stringBuilder.append("admin=true, ");
        stringBuilder.append("createdAt=" + dto.getCreatedAt().toString() + ", ");
        stringBuilder.append("lastUpdatedAt=" + dto.getLastUpdatedAt().toString() + ")");
        assertEquals(stringBuilder.toString(), dto.toString());
    }
}