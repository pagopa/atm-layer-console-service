package it.gov.pagopa.atmlayer.service.consolebackend.configuration;


import io.quarkus.test.junit.QuarkusTest;
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
    public void testFilterWithAuthorizationEnabled() throws IOException {
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setProfile(UserProfileEnum.ADMIN);
        when(userService.findByUserId(anyString())).thenReturn(
                Uni.createFrom().item(userProfileDto));
        authorizationFilter.filter(requestContext);
    }

    @Test
    public void testFilterWithAuthorizationDisabled() throws IOException {
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        authorizationFilter.enableAuthorization = false;
        authorizationFilter.filter(requestContext);
    }

    @Test
    public void testFilterWithAuthorizationEnabledAndAdminUser() throws IOException {
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setProfile(UserProfileEnum.ADMIN);
        when(userService.findByUserId(anyString())).thenReturn(
                Uni.createFrom().item(userProfileDto));
        authorizationFilter.filter(requestContext);
    }

    @Test
    public void testFilterWithAuthorizationEnabledAndNonAdminUser() throws IOException {
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setProfile(UserProfileEnum.ADMIN);
        when(userService.findByUserId(anyString())).thenReturn(
                Uni.createFrom().item(userProfileDto));
        doThrow(new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED,
                AppErrorCodeEnum.ATMLCB_401)).when(requestContext).abortWith(any());
        authorizationFilter.filter(requestContext);
    }

    @Test
    public void testFilterWithAuthorizationEnabledAndUserServiceError() throws IOException {
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        when(userService.findByUserId(anyString())).thenReturn(
                Uni.createFrom().failure(new RuntimeException("Errore nel servizio utente")));
        doThrow(new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED,
                AppErrorCodeEnum.ATMLCB_401)).when(requestContext).abortWith(any());
        authorizationFilter.filter(requestContext);
    }

}
