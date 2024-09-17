package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankPresentationDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankUpdateDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BankService;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@QuarkusTest
class BankResourceTest {

    @InjectMock
    BankService bankService;

    @InjectMock
    UserService userService;

    public BankResourceTest() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void testInsert() {
        BankPresentationDTO expectedResponse = new BankPresentationDTO();
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bankService.insert(any(BankInsertionDTO.class)))
                .thenReturn(Uni.createFrom().item(expectedResponse));

        BankInsertionDTO requestDTO = new BankInsertionDTO();
        requestDTO.setAcquirerId("12345");
        requestDTO.setDenomination("Test Bank");

        BankPresentationDTO actualResponse = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestDTO)
                .when()
                .post("/api/v1/console-service/banks/insert")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(BankPresentationDTO.class);

        assertEquals(expectedResponse, actualResponse);
    }*/

    /*@Test
    void testUpdate() {
        BankPresentationDTO expectedResponse = new BankPresentationDTO();
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bankService.update(any(BankUpdateDTO.class)))
                .thenReturn(Uni.createFrom().item(expectedResponse));

        BankUpdateDTO requestDTO = new BankUpdateDTO();
        requestDTO.setAcquirerId("12345");

        BankPresentationDTO actualResponse = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestDTO)
                .when()
                .put("/api/v1/console-service/banks/update")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(BankPresentationDTO.class);

        assertEquals(expectedResponse, actualResponse);
    }*/

    /*@Test
    void testDisable() {
        String acquirerId = "12345";
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bankService.disable(anyString()))
                .thenReturn(Uni.createFrom().voidItem());

        given()
                .when()
                .post("/api/v1/console-service/banks/disable/{acquirerId}", acquirerId)
                .then()
                .statusCode(204);
    }*/

    @Test
    void testGetBank() {
        String acquirerId = "12345";
        BankPresentationDTO expectedResponse = new BankPresentationDTO();
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bankService.findByAcquirerId(anyString()))
                .thenReturn(Uni.createFrom().item(expectedResponse));

        BankPresentationDTO actualResponse = given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .get("/api/v1/console-service/banks/{acquirerId}", acquirerId)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(BankPresentationDTO.class);

        assertEquals(expectedResponse, actualResponse);
    }
}
