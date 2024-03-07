package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class OutcomeEnumTest {

    @Test
    void testOutcomeEnumValues(){
        assertEquals("OK", OutcomeEnum.OK.getValue());
        assertEquals("The operation completed successfully", OutcomeEnum.OK.getDescription());

        assertEquals("END", OutcomeEnum.END.getValue());
        assertEquals("The process is terminated", OutcomeEnum.END.getDescription());

        assertEquals("PROCESSING", OutcomeEnum.PROCESSING.getValue());
        assertEquals("Process still running, retry later", OutcomeEnum.PROCESSING.getDescription());
    }
}
