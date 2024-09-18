package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankPresentationDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankUpdateDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BankService;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
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

    @Test
    void testInsert() {
        BankPresentationDTO expectedResponse = new BankPresentationDTO();
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoielVwVE1mQ1BtdDloOURkWnd6MnNUQSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiZVB6MDQ3MWx0UEp3MDkwZXZzOUI2RXBiVURyS2ZrLUxrRDN0WjRBNVR3ZERKU2ZwbWc2NnBTQTNRM3o4NWpZcFN6Q1NuQmJVYi1CZjYzMXRLM3lLZkgwcEViSDBNUEdTWGNfVXdRLXpOWEpwSmVZLUNUNTJwOUMweFluMXQ0cUVaa004TUgtQm1DUzlJN1Y0akYyazUtQ1gzaTlTR29Rc0RoUFhSczdISUV3IiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwOTkwNDYzNiwiZXhwIjoxNzA5OTkxMDM2LCJpYXQiOjE3MDk5MDQ2MzYsImp0aSI6ImE3OTI0MmM5LWFiNWQtNDgzOS04ZmQ0LTYxOWZmZDNkOTMzZiIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.agPEYHTxZtWsDpS1vcOxMnkZJbBWATw2AXhk2_ZFyzTbg1pNVep9IwXGNVwBrfi5Fx5HCfXebvcC0BLM40naEWdW1-Xc5MhsXbnq4TzU_XdXb6KFgrSJUUMGHUS4eUOio9z00w1fhdbBx4Tp0mrbu1tNxluIel6Y12cfSxfJq0JFX_sD80Zg-2O5sGiGe-zUjGTRw6jgk8MTkZRz9XbInDwg2Y826sZYtl-cy5uuPJZq1OxZqILMMacGmhGOvQGliD7a32uxQXApuTWzItaIV2VC_qRE69DskQbK_RFor9XubZNUYFPOSDfeStatl_K56HnEm2DLW6UpNjWcZTkcEw");

        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bankService.insert(any(BankInsertionDTO.class), any(ContainerRequestContext.class)))
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
    }

    @Test
    void testUpdate() {
        BankPresentationDTO expectedResponse = new BankPresentationDTO();
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoielVwVE1mQ1BtdDloOURkWnd6MnNUQSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiZVB6MDQ3MWx0UEp3MDkwZXZzOUI2RXBiVURyS2ZrLUxrRDN0WjRBNVR3ZERKU2ZwbWc2NnBTQTNRM3o4NWpZcFN6Q1NuQmJVYi1CZjYzMXRLM3lLZkgwcEViSDBNUEdTWGNfVXdRLXpOWEpwSmVZLUNUNTJwOUMweFluMXQ0cUVaa004TUgtQm1DUzlJN1Y0akYyazUtQ1gzaTlTR29Rc0RoUFhSczdISUV3IiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwOTkwNDYzNiwiZXhwIjoxNzA5OTkxMDM2LCJpYXQiOjE3MDk5MDQ2MzYsImp0aSI6ImE3OTI0MmM5LWFiNWQtNDgzOS04ZmQ0LTYxOWZmZDNkOTMzZiIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.agPEYHTxZtWsDpS1vcOxMnkZJbBWATw2AXhk2_ZFyzTbg1pNVep9IwXGNVwBrfi5Fx5HCfXebvcC0BLM40naEWdW1-Xc5MhsXbnq4TzU_XdXb6KFgrSJUUMGHUS4eUOio9z00w1fhdbBx4Tp0mrbu1tNxluIel6Y12cfSxfJq0JFX_sD80Zg-2O5sGiGe-zUjGTRw6jgk8MTkZRz9XbInDwg2Y826sZYtl-cy5uuPJZq1OxZqILMMacGmhGOvQGliD7a32uxQXApuTWzItaIV2VC_qRE69DskQbK_RFor9XubZNUYFPOSDfeStatl_K56HnEm2DLW6UpNjWcZTkcEw");

        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bankService.update(any(BankUpdateDTO.class), any(ContainerRequestContext.class)))
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
    }

    @Test
    void testDisable() {
        String acquirerId = "12345";
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoielVwVE1mQ1BtdDloOURkWnd6MnNUQSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiZVB6MDQ3MWx0UEp3MDkwZXZzOUI2RXBiVURyS2ZrLUxrRDN0WjRBNVR3ZERKU2ZwbWc2NnBTQTNRM3o4NWpZcFN6Q1NuQmJVYi1CZjYzMXRLM3lLZkgwcEViSDBNUEdTWGNfVXdRLXpOWEpwSmVZLUNUNTJwOUMweFluMXQ0cUVaa004TUgtQm1DUzlJN1Y0akYyazUtQ1gzaTlTR29Rc0RoUFhSczdISUV3IiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwOTkwNDYzNiwiZXhwIjoxNzA5OTkxMDM2LCJpYXQiOjE3MDk5MDQ2MzYsImp0aSI6ImE3OTI0MmM5LWFiNWQtNDgzOS04ZmQ0LTYxOWZmZDNkOTMzZiIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.agPEYHTxZtWsDpS1vcOxMnkZJbBWATw2AXhk2_ZFyzTbg1pNVep9IwXGNVwBrfi5Fx5HCfXebvcC0BLM40naEWdW1-Xc5MhsXbnq4TzU_XdXb6KFgrSJUUMGHUS4eUOio9z00w1fhdbBx4Tp0mrbu1tNxluIel6Y12cfSxfJq0JFX_sD80Zg-2O5sGiGe-zUjGTRw6jgk8MTkZRz9XbInDwg2Y826sZYtl-cy5uuPJZq1OxZqILMMacGmhGOvQGliD7a32uxQXApuTWzItaIV2VC_qRE69DskQbK_RFor9XubZNUYFPOSDfeStatl_K56HnEm2DLW6UpNjWcZTkcEw");

        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bankService.disable(anyString(), any(ContainerRequestContext.class)))
                .thenReturn(Uni.createFrom().voidItem());

        given()
                .when()
                .post("/api/v1/console-service/banks/disable/{acquirerId}", acquirerId)
                .then()
                .statusCode(204);
    }

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
