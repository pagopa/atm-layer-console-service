package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.ResourceService;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
class ResourceResourceTest {

    @InjectMock
    ResourceService resourceService;

    @InjectMock
    UserService userService;
    Header authHeader;

    @BeforeEach
    void initAuthHeader(){
        authHeader = new Header("Authorization", "eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoiMmhBdXBnaHN3NXkyMUF3TGtxM0p0QSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiNzZIVXM1d2JhX2VBZ0VIbUxrRXIyVGpQbDNuMWxvVzg3cDRfbXlfVnZhZl9oT1dyeHZkOW9vQ19oNFlvOFdlUUxsbkh0dVRBNWMzMWQybmVqbEtvanJrQ20zQkFNRlo3aE1RcGJITjZ5VDFaVXhuaE1TU1dndks5TlNrUEwxUjNGcEJFUzh1UFRNR2ZSMHljY09xU0dGSE4zazRtbjJ3eU05NWEzM0NOUjFzIiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwNjUyMTA2MCwiZXhwIjoxNzA2NjA3NDYwLCJpYXQiOjE3MDY1MjEwNjAsImp0aSI6IjU1YjVjNDEwLTMyZGEtNDA0ZC1hYzk1LTI4OTA3NjQ3ZTg2YyIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.OX02xa6siwktsbKbd9PH_C2xFYjpnMckipx6xLcCzx2iAxhV7ghMeDNqe1sk1mcXDxRatUexTS4nvynSlTy6wWLe81GSZTtkAvlUWQ_-mJT90BDxJp8rNC7IPqUS_4Q7QkMOuNHzh_0nYklIa_w8-sV93I3dJ61sAQUw1ye0kxu3lZ7NKUiBU07W2RH2YcdgWP4yr50s9CQMySYymPs_CP1w8eDm0vNndswP9uN4x3YRe-idQ9Q7qUPL4iZzqftahvODzU7mhTnm_IbMr9mZfEuyLNP070PeyGI7giIWdJStTZ0-8tblVAZ_DuoDkkVOHMPAL55yJTx3HRJ5beK4Uw&token_type=Bearer&expires_in=86400");
    }

    @Test
    void testGetResourceFiltered() {
        UUID resourceId = UUID.randomUUID();
        ResourceFrontEndDTO dto = new ResourceFrontEndDTO();
        List<ResourceFrontEndDTO> dtoList = new ArrayList<>();
        dtoList.add(dto);
        PageInfo<ResourceFrontEndDTO> response = new PageInfo<>(0, 1, 1, 1 , dtoList);
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(resourceService.getResourceFiltered(0, 1, resourceId, "sha256", NoDeployableResourceType.OTHER, "fileName", "storageKey", "extension"))
                .thenReturn(Uni.createFrom().item(response));
        PageInfo result = given()
                .header(authHeader)
                .queryParam("pageIndex", 0)
                .queryParam("pageSize", 1)
                .queryParam("resourceId",resourceId)
                .queryParam("sha256","sha256")
                .queryParam("noDeployableResourceType", NoDeployableResourceType.OTHER)
                .queryParam("fileName", "fileName")
                .queryParam("storageKey", "storageKey")
                .queryParam("extension","extension")
                .when().get("/api/v1/console-service/resources/filter")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PageInfo.class);
        assertEquals(result.getItemsFound(), response.getItemsFound());
    }

    @Test
    void testCreateResource() {
        ResourceDTO response= new ResourceDTO();
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(resourceService.createResource(any(ResourceCreationDto.class)))
                .thenReturn(Uni.createFrom().item(response));

       ResourceDTO result = given()
                .header(authHeader)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .multiPart("file", new File("src/test/resources/Test.bpmn"))
                .formParam("filename", "name.bpmn")
                .formParam("resourceType", "OTHER")
                .formParam("path", "path")
                .formParam("description", "description")
                .when().post("/api/v1/console-service/resources")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(ResourceDTO.class);
       assertEquals(response, result);
    }

    @Test
    void testUpdateResource() {
        ResourceDTO response= new ResourceDTO();
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(resourceService.updateResource(any(File.class), any(UUID.class)))
                .thenReturn(Uni.createFrom().item(response));
        UUID uuid = UUID.randomUUID();
        ResourceDTO result = given()
                .header(authHeader)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .multiPart("file", new File("src/test/resources/TestMalformed.bpmn"))
                .pathParam("uuid", uuid.toString())
                .when().put("/api/v1/console-service/resources/{uuid}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(ResourceDTO.class);

        assertEquals(response, result);
    }

    @Test
    void testDisableResource() {
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(resourceService.disable(any(UUID.class)))
                .thenReturn(Uni.createFrom().voidItem());
        UUID uuid = UUID.randomUUID();
        given()
                .header(authHeader)
                .pathParam("uuid", uuid.toString())
                .when().post("/api/v1/console-service/resources/disable/{uuid}")
                .then()
                .statusCode(204)
                .extract()
                .body();
    }
}