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
public class TransactionUpdateDTOTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testNoArgsConstructor() {
        TransactionUpdateDTO transaction = new TransactionUpdateDTO();
        assertNotNull(transaction);
    }

    @Test
    public void testAllArgsConstructor() {
        TransactionUpdateDTO transaction = new TransactionUpdateDTO(
                "tx123",
                "status1",
                "type1"
        );
        assertEquals("tx123", transaction.getTransactionId());
        assertEquals("status1", transaction.getTransactionStatus());
        assertEquals("type1", transaction.getFunctionType());
    }

    @Test
    public void testSettersAndGetters() {
        TransactionUpdateDTO transaction = new TransactionUpdateDTO();
        transaction.setTransactionId("tx123");
        transaction.setTransactionStatus("status1");
        transaction.setFunctionType("type1");

        assertEquals("tx123", transaction.getTransactionId());
        assertEquals("status1", transaction.getTransactionStatus());
        assertEquals("type1", transaction.getFunctionType());
    }

    @Test
    public void testToString() {
        TransactionUpdateDTO transaction = new TransactionUpdateDTO(
                "tx123",
                "status1",
                "type1"
        );
        String expected = "TransactionUpdateDTO(transactionId=tx123, transactionStatus=status1, functionType=type1)";
        assertEquals(expected, transaction.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        TransactionUpdateDTO transaction1 = new TransactionUpdateDTO(
                "tx123",
                "status1",
                "type1"
        );
        TransactionUpdateDTO transaction2 = new TransactionUpdateDTO(
                "tx123",
                "status1",
                "type1"
        );
        assertEquals(transaction1, transaction2);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    public void testBuilder() {
        TransactionUpdateDTO transaction = TransactionUpdateDTO.builder()
                .transactionId("tx123")
                .transactionStatus("status1")
                .functionType("type1")
                .build();

        assertEquals("tx123", transaction.getTransactionId());
        assertEquals("status1", transaction.getTransactionStatus());
        assertEquals("type1", transaction.getFunctionType());
    }

    @Test
    public void testNotBlankValidation() {
        TransactionUpdateDTO transaction = new TransactionUpdateDTO();
        transaction.setTransactionId("");
        transaction.setTransactionStatus("status1");
        transaction.setFunctionType("type1");

        Set<ConstraintViolation<TransactionUpdateDTO>> violations = validator.validate(transaction);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("transactionId")));
    }

    @Test
    public void testNotNullValidation() {
        TransactionUpdateDTO transaction = new TransactionUpdateDTO();
        transaction.setTransactionId("tx123");

        Set<ConstraintViolation<TransactionUpdateDTO>> violations = validator.validate(transaction);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("transactionStatus")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("functionType")));
    }
}

