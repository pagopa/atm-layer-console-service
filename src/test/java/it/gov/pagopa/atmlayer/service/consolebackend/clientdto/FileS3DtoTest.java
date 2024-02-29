package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class FileS3DtoTest {

    @Test
    void testAllArgsConstructor(){
        FileS3Dto fileS3Dto = new FileS3Dto("fileContent");
        assertNotNull(fileS3Dto);
        assertEquals("fileContent", fileS3Dto.getFileContent());
    }

    @Test
    void testBuilderAndToString(){
        FileS3Dto fileS3Dto = FileS3Dto.builder().fileContent("fileContent").build();
        assertNotNull(fileS3Dto);
        assertEquals("fileContent", fileS3Dto.getFileContent());
        String fileS3DtoString = fileS3Dto.toString();
        assertNotNull(fileS3DtoString);
    }

}
