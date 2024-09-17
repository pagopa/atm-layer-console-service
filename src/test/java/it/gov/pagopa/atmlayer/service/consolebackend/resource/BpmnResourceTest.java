package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BpmnService;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum.CREATED;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
class BpmnResourceTest {

    @InjectMock
    BpmnService bpmnService;

    @InjectMock
    UserService userService;

    Header authHeader;

    @BeforeEach
    void initAuthHeader() {
        authHeader = new Header("Authorization", "eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoiMmhBdXBnaHN3NXkyMUF3TGtxM0p0QSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiNzZIVXM1d2JhX2VBZ0VIbUxrRXIyVGpQbDNuMWxvVzg3cDRfbXlfVnZhZl9oT1dyeHZkOW9vQ19oNFlvOFdlUUxsbkh0dVRBNWMzMWQybmVqbEtvanJrQ20zQkFNRlo3aE1RcGJITjZ5VDFaVXhuaE1TU1dndks5TlNrUEwxUjNGcEJFUzh1UFRNR2ZSMHljY09xU0dGSE4zazRtbjJ3eU05NWEzM0NOUjFzIiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwNjUyMTA2MCwiZXhwIjoxNzA2NjA3NDYwLCJpYXQiOjE3MDY1MjEwNjAsImp0aSI6IjU1YjVjNDEwLTMyZGEtNDA0ZC1hYzk1LTI4OTA3NjQ3ZTg2YyIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.OX02xa6siwktsbKbd9PH_C2xFYjpnMckipx6xLcCzx2iAxhV7ghMeDNqe1sk1mcXDxRatUexTS4nvynSlTy6wWLe81GSZTtkAvlUWQ_-mJT90BDxJp8rNC7IPqUS_4Q7QkMOuNHzh_0nYklIa_w8-sV93I3dJ61sAQUw1ye0kxu3lZ7NKUiBU07W2RH2YcdgWP4yr50s9CQMySYymPs_CP1w8eDm0vNndswP9uN4x3YRe-idQ9Q7qUPL4iZzqftahvODzU7mhTnm_IbMr9mZfEuyLNP070PeyGI7giIWdJStTZ0-8tblVAZ_DuoDkkVOHMPAL55yJTx3HRJ5beK4Uw&token_type=Bearer&expires_in=86400");
    }

    @Test
    void testGetAllBpmn() {
        UUID uuid = UUID.randomUUID();
        BpmnVersionFrontEndDTO bpmnVersionFrontEndDTO = new BpmnVersionFrontEndDTO();
        List<BpmnVersionFrontEndDTO> dtoList = new ArrayList<>();
        dtoList.add(bpmnVersionFrontEndDTO);
        PageInfo<BpmnVersionFrontEndDTO> response = new PageInfo<>(0, 1, 1, 1, dtoList);

        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bpmnService.getBpmnFiltered(0, 1, "functionType", "modelVersion", "1", uuid, uuid, "camundaDefinitionId", "definitionKey", "deployedFileName", "resource", "sha256", CREATED, "acquirerId", "branchId", "terminalId", "fileName")).thenReturn(Uni.createFrom().item(response));

        PageInfo result = given()
                .header(authHeader)
                .queryParam("pageIndex", 0)
                .queryParam("pageSize", 1)
                .queryParam("functionType", "functionType")
                .queryParam("modelVersion", "modelVersion")
                .queryParam("definitionVersionCamunda", "1")
                .queryParam("bpmnId", uuid)
                .queryParam("deploymentId", uuid)
                .queryParam("camundaDefinitionId", "camundaDefinitionId")
                .queryParam("definitionKey", "definitionKey")
                .queryParam("deployedFileName", "deployedFileName")
                .queryParam("resource", "resource")
                .queryParam("sha256", "sha256")
                .queryParam("branchId", "branchId")
                .queryParam("terminalId", "terminalId")
                .queryParam("status", CREATED)
                .queryParam("acquirerId", "acquirerId")
                .queryParam("fileName", "fileName")
                .when().get("/api/v1/console-service/bpmn/filter")
                .then()
                .statusCode(200)
                .extract()
                .body().as(PageInfo.class);
        assertEquals(result.getItemsFound(), response.getItemsFound());
    }

    @Test
    void testCreateBpmn() {
        BpmnDTO response = new BpmnDTO();
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bpmnService.createBpmn(any(ContainerRequestContext.class), any(BpmnCreationDto.class)))
                .thenReturn(Uni.createFrom().item(response));
        BpmnDTO result = given()
                .header(authHeader)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .multiPart("file", new File("src/test/resources/Test.bpmn"))
                .formParam("filename", "name")
                .formParam("functionType", "functionType")
                .when().post("/api/v1/console-service/bpmn")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(BpmnDTO.class);
        assertEquals(response, result);
    }

    @Test
    void testGetAssociationsByBpmn() {
        UUID uuid = UUID.randomUUID();
        BpmnBankConfigDTO bpmnBankConfigDTO = new BpmnBankConfigDTO();
        List<BpmnBankConfigDTO> dtoList = new ArrayList<>();
        dtoList.add(bpmnBankConfigDTO);
        PageInfo<BpmnBankConfigDTO> response = new PageInfo<>(0, 1, 1, 1, dtoList);
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bpmnService.getAssociationsByBpmn(0, 1, uuid, 1L)).thenReturn(Uni.createFrom().item(response));
        PageInfo result = given()
                .header(authHeader)
                .pathParam("uuid", uuid.toString())
                .pathParam("version", 1)
                .queryParam("pageIndex", 0)
                .queryParam("pageSize", 1)
                .when().get("/api/v1/console-service/bpmn/associations/{uuid}/version/{version}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PageInfo.class);
        assertEquals(result.getItemsFound(), response.getItemsFound());
    }

//    @Test
//    void testAddAssociation() {
//        UUID uuid = UUID.randomUUID();
//        BpmnBankConfigDTO response = new BpmnBankConfigDTO();
//        BankConfigTripletDto inputDto = new BankConfigTripletDto(any(), any(), any());
//        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
//        when(bpmnService.addSingleAssociation( uuid, 1L, inputDto)).thenReturn(Uni.createFrom().item(response));
//        BpmnBankConfigDTO result = given()
//                .header(authHeader)
//                .contentType(MediaType.APPLICATION_JSON)
//                .pathParam("uuid", uuid.toString())
//                .pathParam("version", 1)
//                .when().post("/api/v1/console-service/bpmn/associations/{uuid}/version/{version}")
//                .then()
//                .statusCode(200)
//                .extract()
//                .body()
//                .as(BpmnBankConfigDTO.class);
//        assertEquals(result, response);
//    }

    @Test
    void testDeleteAssociation() {
        UUID uuid = UUID.randomUUID();
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoielVwVE1mQ1BtdDloOURkWnd6MnNUQSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiZVB6MDQ3MWx0UEp3MDkwZXZzOUI2RXBiVURyS2ZrLUxrRDN0WjRBNVR3ZERKU2ZwbWc2NnBTQTNRM3o4NWpZcFN6Q1NuQmJVYi1CZjYzMXRLM3lLZkgwcEViSDBNUEdTWGNfVXdRLXpOWEpwSmVZLUNUNTJwOUMweFluMXQ0cUVaa004TUgtQm1DUzlJN1Y0akYyazUtQ1gzaTlTR29Rc0RoUFhSczdISUV3IiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwOTkwNDYzNiwiZXhwIjoxNzA5OTkxMDM2LCJpYXQiOjE3MDk5MDQ2MzYsImp0aSI6ImE3OTI0MmM5LWFiNWQtNDgzOS04ZmQ0LTYxOWZmZDNkOTMzZiIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.agPEYHTxZtWsDpS1vcOxMnkZJbBWATw2AXhk2_ZFyzTbg1pNVep9IwXGNVwBrfi5Fx5HCfXebvcC0BLM40naEWdW1-Xc5MhsXbnq4TzU_XdXb6KFgrSJUUMGHUS4eUOio9z00w1fhdbBx4Tp0mrbu1tNxluIel6Y12cfSxfJq0JFX_sD80Zg-2O5sGiGe-zUjGTRw6jgk8MTkZRz9XbInDwg2Y826sZYtl-cy5uuPJZq1OxZqILMMacGmhGOvQGliD7a32uxQXApuTWzItaIV2VC_qRE69DskQbK_RFor9XubZNUYFPOSDfeStatl_K56HnEm2DLW6UpNjWcZTkcEw");
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bpmnService.deleteSingleAssociation(uuid, 1L, "acquirerId", "branchId", "terminalId", containerRequestContext)).thenReturn(Uni.createFrom().voidItem());
        given()
                .header(authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("uuid", uuid.toString())
                .pathParam("version", 1)
                .queryParam("acquirerId", "acquirerId")
                .queryParam("branchId", "branchId")
                .queryParam("terminalId", "terminalId")
                .when().delete("/api/v1/console-service/bpmn/associations/{uuid}/version/{version}")
                .then()
                .statusCode(204)
                .extract()
                .body();
    }

//    @Test
//    void testReplaceSingleAssociation() {
//        UUID uuid = UUID.randomUUID();
//        BpmnBankConfigDTO response = new BpmnBankConfigDTO();
//        BankConfigTripletDto inputDto = new BankConfigTripletDto(any(), any(), any());
//        when(userService.checkAuthorizationUser(any(ContainerRequestContext.class), any(UserProfileEnum.class))).thenReturn(Uni.createFrom().voidItem());
//        when(bpmnService.replaceSingleAssociation( any(), anyLong(), any())).thenReturn(Uni.createFrom().item(response));
//        BpmnBankConfigDTO result = given()
//                .header(authHeader)
//                .contentType(MediaType.APPLICATION_JSON)
//                .pathParam("uuid", uuid.toString())
//                .pathParam("version", 1)
//                .when().put("/api/v1/console-service/bpmn/associations/{uuid}/version/{version}")
//                .then()
//                .statusCode(200)
//                .extract()
//                .body()
//                .as(BpmnBankConfigDTO.class);
//        assertEquals(result, response);
//    }

    @Test
    void testDeployBpmn() {
        UUID uuid = UUID.randomUUID();
        BpmnDTO response = new BpmnDTO();
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bpmnService.deployBPMN(any(UUID.class), any(Long.class), any(ContainerRequestContext.class))).thenReturn(Uni.createFrom().item(response));
        BpmnDTO result = given()
                .header(authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("uuid", uuid.toString())
                .pathParam("version", 1)
                .when().post("/api/v1/console-service/bpmn/deploy/{uuid}/version/{version}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(BpmnDTO.class);
        assertEquals(result, response);
    }

    @Test
    void testDownloadBpmnFrontend() {
        UUID uuid = UUID.randomUUID();
        FileS3Dto response = new FileS3Dto();
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bpmnService.downloadBpmnFrontEnd(uuid, 1L)).thenReturn(Uni.createFrom().item(response));
        FileS3Dto result = given()
                .header(authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("uuid", uuid.toString())
                .pathParam("version", 1)
                .when().get("/api/v1/console-service/bpmn/downloadFrontEnd/{uuid}/version/{version}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(FileS3Dto.class);
        assertEquals(result, response);
    }

    @Test
    void testDisableBpmn() {
        UUID uuid = UUID.randomUUID();
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoielVwVE1mQ1BtdDloOURkWnd6MnNUQSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiZVB6MDQ3MWx0UEp3MDkwZXZzOUI2RXBiVURyS2ZrLUxrRDN0WjRBNVR3ZERKU2ZwbWc2NnBTQTNRM3o4NWpZcFN6Q1NuQmJVYi1CZjYzMXRLM3lLZkgwcEViSDBNUEdTWGNfVXdRLXpOWEpwSmVZLUNUNTJwOUMweFluMXQ0cUVaa004TUgtQm1DUzlJN1Y0akYyazUtQ1gzaTlTR29Rc0RoUFhSczdISUV3IiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwOTkwNDYzNiwiZXhwIjoxNzA5OTkxMDM2LCJpYXQiOjE3MDk5MDQ2MzYsImp0aSI6ImE3OTI0MmM5LWFiNWQtNDgzOS04ZmQ0LTYxOWZmZDNkOTMzZiIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.agPEYHTxZtWsDpS1vcOxMnkZJbBWATw2AXhk2_ZFyzTbg1pNVep9IwXGNVwBrfi5Fx5HCfXebvcC0BLM40naEWdW1-Xc5MhsXbnq4TzU_XdXb6KFgrSJUUMGHUS4eUOio9z00w1fhdbBx4Tp0mrbu1tNxluIel6Y12cfSxfJq0JFX_sD80Zg-2O5sGiGe-zUjGTRw6jgk8MTkZRz9XbInDwg2Y826sZYtl-cy5uuPJZq1OxZqILMMacGmhGOvQGliD7a32uxQXApuTWzItaIV2VC_qRE69DskQbK_RFor9XubZNUYFPOSDfeStatl_K56HnEm2DLW6UpNjWcZTkcEw");
        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bpmnService.disableBPMN(uuid, 1L, containerRequestContext)).thenReturn(Uni.createFrom().voidItem());
        given()
                .header(authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .pathParam("uuid", uuid.toString())
                .pathParam("version", 1)
                .when().post("/api/v1/console-service/bpmn/disable/{uuid}/version/{version}")
                .then()
                .statusCode(204)
                .extract()
                .body();
    }

    @Test
    void testUpgradeBPMN() {
        UUID uuid = UUID.randomUUID();
        BpmnDTO response = new BpmnDTO();
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoielVwVE1mQ1BtdDloOURkWnd6MnNUQSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiZVB6MDQ3MWx0UEp3MDkwZXZzOUI2RXBiVURyS2ZrLUxrRDN0WjRBNVR3ZERKU2ZwbWc2NnBTQTNRM3o4NWpZcFN6Q1NuQmJVYi1CZjYzMXRLM3lLZkgwcEViSDBNUEdTWGNfVXdRLXpOWEpwSmVZLUNUNTJwOUMweFluMXQ0cUVaa004TUgtQm1DUzlJN1Y0akYyazUtQ1gzaTlTR29Rc0RoUFhSczdISUV3IiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwOTkwNDYzNiwiZXhwIjoxNzA5OTkxMDM2LCJpYXQiOjE3MDk5MDQ2MzYsImp0aSI6ImE3OTI0MmM5LWFiNWQtNDgzOS04ZmQ0LTYxOWZmZDNkOTMzZiIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.agPEYHTxZtWsDpS1vcOxMnkZJbBWATw2AXhk2_ZFyzTbg1pNVep9IwXGNVwBrfi5Fx5HCfXebvcC0BLM40naEWdW1-Xc5MhsXbnq4TzU_XdXb6KFgrSJUUMGHUS4eUOio9z00w1fhdbBx4Tp0mrbu1tNxluIel6Y12cfSxfJq0JFX_sD80Zg-2O5sGiGe-zUjGTRw6jgk8MTkZRz9XbInDwg2Y826sZYtl-cy5uuPJZq1OxZqILMMacGmhGOvQGliD7a32uxQXApuTWzItaIV2VC_qRE69DskQbK_RFor9XubZNUYFPOSDfeStatl_K56HnEm2DLW6UpNjWcZTkcEw");

        when(userService.checkAuthorizationUser(any(), any())).thenReturn(Uni.createFrom().voidItem());
        when(bpmnService.upgradeBPMN(any(BpmnUpgradeDto.class), any(ContainerRequestContext.class))).thenReturn(Uni.createFrom().item(response));
        BpmnDTO result = given()
                .header(authHeader)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .multiPart("file", new File("src/test/resources/Test.bpmn"))
                .formParam("uuid", uuid)
                .formParam("filename", "name")
                .formParam("functionType", "functionType")
                .when().post("/api/v1/console-service/bpmn/upgrade")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(BpmnDTO.class);
        assertEquals(result, response);
    }

}