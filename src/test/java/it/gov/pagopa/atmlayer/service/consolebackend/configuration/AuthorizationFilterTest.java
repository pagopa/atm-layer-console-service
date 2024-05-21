package it.gov.pagopa.atmlayer.service.consolebackend.configuration;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfileDto;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorCodeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.AtmLayerException;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static it.gov.pagopa.atmlayer.service.consolebackend.utils.HeadersUtils.getEmailJWT;
import static it.gov.pagopa.atmlayer.service.consolebackend.utils.HeadersUtils.havePermission;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@QuarkusTest
public class AuthorizationFilterTest {

    @Mock
    UserService userService;

    @InjectMocks
    AuthorizationFilter authorizationFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFilterWithAuthorizationEnabled() throws IOException {
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setProfile(UserProfileEnum.ADMIN);
        when(userService.findByUserId(anyString())).thenReturn(
                Uni.createFrom().item(userProfileDto));
        authorizationFilter.filter(requestContext);
        verify(requestContext, never()).abortWith(any());
    }

    @Test
    void testFilterWithAuthorizationDisabled() throws IOException {
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        authorizationFilter.enableAuthorization = false;
        authorizationFilter.filter(requestContext);
        verify(requestContext, never()).abortWith(any());
    }

    @Test
    void testFilterWithAuthorizationEnabledAndAdminUser() throws IOException {
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setProfile(UserProfileEnum.ADMIN);
        when(userService.findByUserId(anyString())).thenReturn(
                Uni.createFrom().item(userProfileDto));
        authorizationFilter.filter(requestContext);
        verify(requestContext, never()).abortWith(any());
    }

    @Test
    void testFilterWithAuthorizationEnabledAndNonAdminUser() throws IOException {
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setProfile(UserProfileEnum.ADMIN);
        when(userService.findByUserId(anyString())).thenReturn(
                Uni.createFrom().item(userProfileDto));
        doThrow(new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED,
                AppErrorCodeEnum.ATMLCB_401)).when(requestContext).abortWith(any());
        authorizationFilter.filter(requestContext);
        verify(requestContext, never()).abortWith(any());
    }

    @Test
    void testFilterWithAuthorizationEnabledAndUserServiceError() throws IOException {
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        when(userService.findByUserId(anyString())).thenReturn(
                Uni.createFrom().failure(new RuntimeException("Errore nel servizio utente")));
        doThrow(new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED,
                AppErrorCodeEnum.ATMLCB_401)).when(requestContext).abortWith(any());
        authorizationFilter.filter(requestContext);
    }
    Header authHeader;
    String email;
    UserProfileDto userProfileDto;
    ContainerRequestContext container;

    @BeforeEach
    void initAuthHeader(){
        authHeader = new Header("Authorization", "eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoiMmhBdXBnaHN3NXkyMUF3TGtxM0p0QSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiNzZIVXM1d2JhX2VBZ0VIbUxrRXIyVGpQbDNuMWxvVzg3cDRfbXlfVnZhZl9oT1dyeHZkOW9vQ19oNFlvOFdlUUxsbkh0dVRBNWMzMWQybmVqbEtvanJrQ20zQkFNRlo3aE1RcGJITjZ5VDFaVXhuaE1TU1dndks5TlNrUEwxUjNGcEJFUzh1UFRNR2ZSMHljY09xU0dGSE4zazRtbjJ3eU05NWEzM0NOUjFzIiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwNjUyMTA2MCwiZXhwIjoxNzA2NjA3NDYwLCJpYXQiOjE3MDY1MjEwNjAsImp0aSI6IjU1YjVjNDEwLTMyZGEtNDA0ZC1hYzk1LTI4OTA3NjQ3ZTg2YyIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.OX02xa6siwktsbKbd9PH_C2xFYjpnMckipx6xLcCzx2iAxhV7ghMeDNqe1sk1mcXDxRatUexTS4nvynSlTy6wWLe81GSZTtkAvlUWQ_-mJT90BDxJp8rNC7IPqUS_4Q7QkMOuNHzh_0nYklIa_w8-sV93I3dJ61sAQUw1ye0kxu3lZ7NKUiBU07W2RH2YcdgWP4yr50s9CQMySYymPs_CP1w8eDm0vNndswP9uN4x3YRe-idQ9Q7qUPL4iZzqftahvODzU7mhTnm_IbMr9mZfEuyLNP070PeyGI7giIWdJStTZ0-8tblVAZ_DuoDkkVOHMPAL55yJTx3HRJ5beK4Uw&token_type=Bearer&expires_in=86400");
        email = "antonio.tarricone@pagopa.it";
        container = mock(ContainerRequestContext.class);
        userProfileDto = new UserProfileDto();
        userProfileDto.setUserId(email);
        userProfileDto.setProfile(UserProfileEnum.ADMIN);
        userProfileDto.setAdmin(true);
        userProfileDto.setVisible(true);
        userProfileDto.setEditable(true);
        doReturn(authHeader.toString()).when(container).getHeaderString("Authorization");
        when(getEmailJWT(container)).thenReturn(Uni.createFrom().item(email).toString());
    }

    @Test
    void testFilter(){
        when(userService.findByUserId(any(String.class))).thenReturn(Uni.createFrom().item(userProfileDto));
        assertTrue(havePermission(userProfileDto, UserProfileEnum.ADMIN));
        assertTrue(havePermission(userProfileDto, UserProfileEnum.GUEST));
        assertTrue(havePermission(userProfileDto, UserProfileEnum.OPERATOR));
    }
}
