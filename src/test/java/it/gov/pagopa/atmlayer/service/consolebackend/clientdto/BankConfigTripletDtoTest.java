package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class BankConfigTripletDtoTest {

    BankConfigTripletDto dto, dto1;

    @BeforeEach
    void init(){
        dto = new BankConfigTripletDto("acquirerId", "branchId", "terminalId");
        dto1 = new BankConfigTripletDto("acquirerId", "branchId", "terminalId");
    }

    @Test
    void testGettersAndSetters(){
       assertEquals(dto.getAcquirerId(), "acquirerId");
       assertEquals(dto.getBranchId(), "branchId");
       dto.setTerminalId("terminalId");
       assertEquals(dto.getTerminalId(), "terminalId");
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