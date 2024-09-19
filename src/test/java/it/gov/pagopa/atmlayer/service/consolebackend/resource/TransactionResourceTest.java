package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionUpdateDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.TransactionService;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
public class TransactionResourceTest {
    @InjectMock
    TransactionService transactionService;

    @InjectMock
    UserService userService;
    Header authHeader;
    private TransactionDTO transactionDTO;
    private TransactionInsertionDTO transactionInsertionDTO;
    private TransactionUpdateDTO transactionUpdateDTO;
    private PageInfo<TransactionDTO> pageInfo;

    @BeforeEach
    public void setUp() {
        authHeader = new Header("Authorization", "eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoiMmhBdXBnaHN3NXkyMUF3TGtxM0p0QSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiNzZIVXM1d2JhX2VBZ0VIbUxrRXIyVGpQbDNuMWxvVzg3cDRfbXlfVnZhZl9oT1dyeHZkOW9vQ19oNFlvOFdlUUxsbkh0dVRBNWMzMWQybmVqbEtvanJrQ20zQkFNRlo3aE1RcGJITjZ5VDFaVXhuaE1TU1dndks5TlNrUEwxUjNGcEJFUzh1UFRNR2ZSMHljY09xU0dGSE4zazRtbjJ3eU05NWEzM0NOUjFzIiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwNjUyMTA2MCwiZXhwIjoxNzA2NjA3NDYwLCJpYXQiOjE3MDY1MjEwNjAsImp0aSI6IjU1YjVjNDEwLTMyZGEtNDA0ZC1hYzk1LTI4OTA3NjQ3ZTg2YyIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.OX02xa6siwktsbKbd9PH_C2xFYjpnMckipx6xLcCzx2iAxhV7ghMeDNqe1sk1mcXDxRatUexTS4nvynSlTy6wWLe81GSZTtkAvlUWQ_-mJT90BDxJp8rNC7IPqUS_4Q7QkMOuNHzh_0nYklIa_w8-sV93I3dJ61sAQUw1ye0kxu3lZ7NKUiBU07W2RH2YcdgWP4yr50s9CQMySYymPs_CP1w8eDm0vNndswP9uN4x3YRe-idQ9Q7qUPL4iZzqftahvODzU7mhTnm_IbMr9mZfEuyLNP070PeyGI7giIWdJStTZ0-8tblVAZ_DuoDkkVOHMPAL55yJTx3HRJ5beK4Uw&token_type=Bearer&expires_in=86400");

        transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId("txn123");
        transactionDTO.setFunctionType("funcType");
        transactionDTO.setAcquirerId("acq123");
        transactionDTO.setBranchId("branch123");
        transactionDTO.setTerminalId("term123");
        transactionDTO.setTransactionStatus("completed");

        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        Timestamp lastUpdatedAt = new Timestamp(System.currentTimeMillis());

        transactionDTO.setCreatedAt(createdAt);
        transactionDTO.setLastUpdatedAt(lastUpdatedAt);

        transactionInsertionDTO = new TransactionInsertionDTO();
        transactionInsertionDTO.setTransactionId("txn123");
        transactionInsertionDTO.setFunctionType("funcType");
        transactionInsertionDTO.setAcquirerId("acq123");
        transactionInsertionDTO.setBranchId("branch123");
        transactionInsertionDTO.setTerminalId("term123");
        transactionInsertionDTO.setTransactionStatus("pending");

        transactionUpdateDTO = new TransactionUpdateDTO();
        transactionUpdateDTO.setTransactionId("txn123");
        transactionUpdateDTO.setTransactionStatus("completed");
        transactionUpdateDTO.setFunctionType("funcType");

        pageInfo = new PageInfo<>(0, 10, 1, 1, Arrays.asList(transactionDTO));
    }
    @Test
    public void testInsert() {
        when(transactionService.insert(transactionInsertionDTO)).thenReturn(Uni.createFrom().item(transactionDTO));
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());

        TransactionDTO result = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(transactionInsertionDTO)
                .when()
                .post("/api/v1/console-service/transactions/insert")
                .then()
                .statusCode(200)
                .extract()
                .body().as(TransactionDTO.class);
        assertEquals(result, transactionDTO);
    }
    @Test
    public void testSearchTransaction() {
        when(transactionService.searchTransaction(0, 10, "txn123", "funcType", "acq123", "branch123", "term123", "completed", null, null))
                .thenReturn(Uni.createFrom().item(pageInfo));
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());

        PageInfo result = given()
                .header(authHeader)
                .queryParam("pageIndex", 0)
                .queryParam("pageSize", 10)
                .queryParam("transactionId", "txn123")
                .queryParam("functionType", "funcType")
                .queryParam("acquirerId", "acq123")
                .queryParam("branchId", "branch123")
                .queryParam("terminalId", "term123")
                .queryParam("transactionStatus", "completed")
                .when()
                .get("/api/v1/console-service/transactions/search")
                .then()
                .statusCode(200)
                .extract()
                .body().as(PageInfo.class);

        assertEquals(result.getItemsFound(), pageInfo.getItemsFound());
    }


    @Test
    public void testUpdate() {
        when(transactionService.update(transactionUpdateDTO)).thenReturn(Uni.createFrom().item(transactionDTO));
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());

        TransactionDTO result = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(transactionUpdateDTO)
                .header(authHeader)
                .when()
                .put("/api/v1/console-service/transactions/update")
                .then()
                .statusCode(200)
                .extract()
                .body().as(TransactionDTO.class);

        assertEquals(result, transactionDTO);
    }
    @Test
    public void testDelete() {
        when(transactionService.delete("txn123")).thenReturn(Uni.createFrom().voidItem());
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());

        given()
                .header(authHeader)
                .when()
                .delete("/api/v1/console-service/transactions/delete/txn123")
                .then()
                .statusCode(204);
    }
}
