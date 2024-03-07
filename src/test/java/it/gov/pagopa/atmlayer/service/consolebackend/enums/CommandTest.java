package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class CommandTest {

    @Test
    void testCommandEnum(){
        assertEquals(Command.AUTHORIZE, Command.valueOf("AUTHORIZE"));
        assertEquals(Command.PRINT_RECEIPT, Command.valueOf("PRINT_RECEIPT"));
        assertEquals(Command.SCAN_BILL_DATA, Command.valueOf("SCAN_BILL_DATA"));
        assertEquals(Command.SCAN_FISCAL_CODE, Command.valueOf("SCAN_FISCAL_CODE"));
        assertEquals(Command.END, Command.valueOf("END"));
        assertEquals(Command.GET_IBAN, Command.valueOf("GET_IBAN"));
        assertEquals(Command.GET_PAN, Command.valueOf("GET_PAN"));
    }
}
