package it.gov.pagopa.atmlayer.service.consolebackend.appdto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class FileS3DtoTest {

    @Test
    void testConstructor() {
        FileS3Dto dto = new FileS3Dto();
        dto.setFileContent("abcdefghilmnopqrstuvz1234567890");
        assertEquals("abcdefghilmnopqrstuvz1234567890", dto.getFileContent());
        assertEquals("FileS3Dto(fileContent=abcdefghilmnopqrstuvz1234567890)", dto.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        String fileContent = "abcdefghilmnopqrstuvz1234567890";
        FileS3Dto dto = new FileS3Dto(fileContent);
        FileS3Dto newDto = new FileS3Dto(fileContent);
        assertEquals(newDto.getFileContent(), newDto.getFileContent());
        assertEquals(newDto.hashCode(), dto.hashCode());
    }

}