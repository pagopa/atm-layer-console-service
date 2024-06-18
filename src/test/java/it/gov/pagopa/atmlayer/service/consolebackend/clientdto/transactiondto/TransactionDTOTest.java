package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TransactionDTOTest {

    @Test
    public void testNoArgsConstructor() {
        TransactionDTO transaction = new TransactionDTO();
        assertNotNull(transaction);
    }

    @Test
    public void testAllArgsConstructor() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        TransactionDTO transaction = new TransactionDTO(
                "tx123",
                "type1",
                "acquirer1",
                "branch1",
                "terminal1",
                "status1",
                now,
                now
        );
        assertEquals("tx123", transaction.getTransactionId());
        assertEquals("type1", transaction.getFunctionType());
        assertEquals("acquirer1", transaction.getAcquirerId());
        assertEquals("branch1", transaction.getBranchId());
        assertEquals("terminal1", transaction.getTerminalId());
        assertEquals("status1", transaction.getTransactionStatus());
        assertEquals(now, transaction.getCreatedAt());
        assertEquals(now, transaction.getLastUpdatedAt());
    }

    @Test
    public void testSettersAndGetters() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        TransactionDTO transaction = new TransactionDTO();
        transaction.setTransactionId("tx123");
        transaction.setFunctionType("type1");
        transaction.setAcquirerId("acquirer1");
        transaction.setBranchId("branch1");
        transaction.setTerminalId("terminal1");
        transaction.setTransactionStatus("status1");
        transaction.setCreatedAt(now);
        transaction.setLastUpdatedAt(now);

        assertEquals("tx123", transaction.getTransactionId());
        assertEquals("type1", transaction.getFunctionType());
        assertEquals("acquirer1", transaction.getAcquirerId());
        assertEquals("branch1", transaction.getBranchId());
        assertEquals("terminal1", transaction.getTerminalId());
        assertEquals("status1", transaction.getTransactionStatus());
        assertEquals(now, transaction.getCreatedAt());
        assertEquals(now, transaction.getLastUpdatedAt());
    }

    @Test
    public void testToString() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        TransactionDTO transaction = new TransactionDTO(
                "tx123",
                "type1",
                "acquirer1",
                "branch1",
                "terminal1",
                "status1",
                now,
                now
        );
        String expected = "TransactionDTO(transactionId=tx123, functionType=type1, acquirerId=acquirer1, branchId=branch1, terminalId=terminal1, transactionStatus=status1, createdAt=" + now + ", lastUpdatedAt=" + now + ")";
        assertEquals(expected, transaction.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        TransactionDTO transaction1 = new TransactionDTO(
                "tx123",
                "type1",
                "acquirer1",
                "branch1",
                "terminal1",
                "status1",
                now,
                now
        );
        TransactionDTO transaction2 = new TransactionDTO(
                "tx123",
                "type1",
                "acquirer1",
                "branch1",
                "terminal1",
                "status1",
                now,
                now
        );
        assertEquals(transaction1, transaction2);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    public void testBuilder() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        TransactionDTO transaction = TransactionDTO.builder()
                .transactionId("tx123")
                .functionType("type1")
                .acquirerId("acquirer1")
                .branchId("branch1")
                .terminalId("terminal1")
                .transactionStatus("status1")
                .createdAt(now)
                .lastUpdatedAt(now)
                .build();

        assertEquals("tx123", transaction.getTransactionId());
        assertEquals("type1", transaction.getFunctionType());
        assertEquals("acquirer1", transaction.getAcquirerId());
        assertEquals("branch1", transaction.getBranchId());
        assertEquals("terminal1", transaction.getTerminalId());
        assertEquals("status1", transaction.getTransactionStatus());
        assertEquals(now, transaction.getCreatedAt());
        assertEquals(now, transaction.getLastUpdatedAt());
    }
}

