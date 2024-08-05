package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TransactionInsertionDTOTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testNoArgsConstructor() {
        TransactionInsertionDTO transaction = new TransactionInsertionDTO();
        assertNotNull(transaction);
    }

    @Test
    public void testAllArgsConstructor() {
        TransactionInsertionDTO transaction = new TransactionInsertionDTO(
                "tx123",
                "type1",
                "acquirer1",
                "branch1",
                "terminal1",
                "status1"
        );
        assertEquals("tx123", transaction.getTransactionId());
        assertEquals("type1", transaction.getFunctionType());
        assertEquals("acquirer1", transaction.getAcquirerId());
        assertEquals("branch1", transaction.getBranchId());
        assertEquals("terminal1", transaction.getTerminalId());
        assertEquals("status1", transaction.getTransactionStatus());
    }

    @Test
    public void testSettersAndGetters() {
        TransactionInsertionDTO transaction = new TransactionInsertionDTO();
        transaction.setTransactionId("tx123");
        transaction.setFunctionType("type1");
        transaction.setAcquirerId("acquirer1");
        transaction.setBranchId("branch1");
        transaction.setTerminalId("terminal1");
        transaction.setTransactionStatus("status1");

        assertEquals("tx123", transaction.getTransactionId());
        assertEquals("type1", transaction.getFunctionType());
        assertEquals("acquirer1", transaction.getAcquirerId());
        assertEquals("branch1", transaction.getBranchId());
        assertEquals("terminal1", transaction.getTerminalId());
        assertEquals("status1", transaction.getTransactionStatus());
    }

    @Test
    public void testToString() {
        TransactionInsertionDTO transaction = new TransactionInsertionDTO(
                "tx123",
                "type1",
                "acquirer1",
                "branch1",
                "terminal1",
                "status1"
        );
        String expected = "TransactionInsertionDTO(transactionId=tx123, functionType=type1, acquirerId=acquirer1, branchId=branch1, terminalId=terminal1, transactionStatus=status1)";
        assertEquals(expected, transaction.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        TransactionInsertionDTO transaction1 = new TransactionInsertionDTO(
                "tx123",
                "type1",
                "acquirer1",
                "branch1",
                "terminal1",
                "status1"
        );
        TransactionInsertionDTO transaction2 = new TransactionInsertionDTO(
                "tx123",
                "type1",
                "acquirer1",
                "branch1",
                "terminal1",
                "status1"
        );
        assertEquals(transaction1, transaction2);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    public void testBuilder() {
        TransactionInsertionDTO transaction = TransactionInsertionDTO.builder()
                .transactionId("tx123")
                .functionType("type1")
                .acquirerId("acquirer1")
                .branchId("branch1")
                .terminalId("terminal1")
                .transactionStatus("status1")
                .build();

        assertEquals("tx123", transaction.getTransactionId());
        assertEquals("type1", transaction.getFunctionType());
        assertEquals("acquirer1", transaction.getAcquirerId());
        assertEquals("branch1", transaction.getBranchId());
        assertEquals("terminal1", transaction.getTerminalId());
        assertEquals("status1", transaction.getTransactionStatus());
    }

    @Test
    public void testNotBlankValidation() {
        TransactionInsertionDTO transaction = new TransactionInsertionDTO();

        Set<ConstraintViolation<TransactionInsertionDTO>> violations = validator.validate(transaction);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("transactionId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("functionType")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("acquirerId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("branchId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("terminalId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("transactionStatus")));
    }
}

