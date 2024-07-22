package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserInsertionWithProfilesDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
public class UserResourceTest {

    @InjectMock
    UserService userService;

    Header authHeader;

    @BeforeEach
    void initAuthHeader(){
        authHeader = new Header("Authorization", "eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoiMmhBdXBnaHN3NXkyMUF3TGtxM0p0QSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiNzZIVXM1d2JhX2VBZ0VIbUxrRXIyVGpQbDNuMWxvVzg3cDRfbXlfVnZhZl9oT1dyeHZkOW9vQ19oNFlvOFdlUUxsbkh0dVRBNWMzMWQybmVqbEtvanJrQ20zQkFNRlo3aE1RcGJITjZ5VDFaVXhuaE1TU1dndks5TlNrUEwxUjNGcEJFUzh1UFRNR2ZSMHljY09xU0dGSE4zazRtbjJ3eU05NWEzM0NOUjFzIiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwNjUyMTA2MCwiZXhwIjoxNzA2NjA3NDYwLCJpYXQiOjE3MDY1MjEwNjAsImp0aSI6IjU1YjVjNDEwLTMyZGEtNDA0ZC1hYzk1LTI4OTA3NjQ3ZTg2YyIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.OX02xa6siwktsbKbd9PH_C2xFYjpnMckipx6xLcCzx2iAxhV7ghMeDNqe1sk1mcXDxRatUexTS4nvynSlTy6wWLe81GSZTtkAvlUWQ_-mJT90BDxJp8rNC7IPqUS_4Q7QkMOuNHzh_0nYklIa_w8-sV93I3dJ61sAQUw1ye0kxu3lZ7NKUiBU07W2RH2YcdgWP4yr50s9CQMySYymPs_CP1w8eDm0vNndswP9uN4x3YRe-idQ9Q7qUPL4iZzqftahvODzU7mhTnm_IbMr9mZfEuyLNP070PeyGI7giIWdJStTZ0-8tblVAZ_DuoDkkVOHMPAL55yJTx3HRJ5beK4Uw&token_type=Bearer&expires_in=86400");
    }

    @Test
    void testCreateUser() {
        UserInsertionDTO request = new UserInsertionDTO();
        request.setUserId("a@b.it");
        request.setName("a");
        request.setSurname("b");
        UserDTO response= new UserDTO();
        response.setUserId("a@b.it");
        response.setName("a");
        response.setSurname("b");
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(userService.createUser(request))
                .thenReturn(Uni.createFrom().item(response));
        UserDTO result = given()
                .header(authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .when().post("/api/v1/console-service/user/insert")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(UserDTO.class);
        Assertions.assertEquals(response, result);
    }

    @Test
    void testDeleteUser() {
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(userService.deleteUser( "a@b.it")).thenReturn(Uni.createFrom().voidItem());
        given()
                .header(authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("userId", "a@b.it")
                .when().delete("/api/v1/console-service/user/delete/userId/{userId}")
                .then()
                .statusCode(204)
                .extract()
                .body();
    }

    @Test
    void testGetById() {

        UserDTO response= new UserDTO();
        response.setUserId("a@b.it");
        response.setName("a");
        response.setSurname("b");


        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(userService.getUserById("a@b.it")).thenReturn(Uni.createFrom().item(response));

        UserDTO result = given()
                .header(authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("userId", "a@b.it")
                .when().get("/api/v1/console-service/user/{userId}")
                .then()
                .statusCode(200)
                .extract()
                .body().as(UserDTO.class);
        Assertions.assertEquals(result, response);
    }

    @Test
    void testUpdateWithProfiles() {
        UserInsertionWithProfilesDTO request = UserInsertionWithProfilesDTO.builder()
                .userId("a@b.it")
                .name("a")
                .surname("b")
                .profileIds(List.of(1, 2, 3))
                .build();
        UserDTO response = new UserDTO();
        response.setUserId("a@b.it");
        response.setName("a");
        response.setSurname("b");

        when(userService.updateWithProfiles(any())).thenReturn(Uni.createFrom().item(response));

        UserDTO result = given()
                .header(authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .when().put("/api/v1/console-service/user/update-with-profiles")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(UserDTO.class);
        Assertions.assertEquals(response, result);
    }
}
