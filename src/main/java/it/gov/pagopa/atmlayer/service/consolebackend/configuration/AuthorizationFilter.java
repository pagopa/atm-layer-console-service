package it.gov.pagopa.atmlayer.service.consolebackend.configuration;

import it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorCodeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.AtmLayerException;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

import static it.gov.pagopa.atmlayer.service.consolebackend.utils.HeadersUtils.getEmailJWT;
import static it.gov.pagopa.atmlayer.service.consolebackend.utils.HeadersUtils.havePermission;

@Provider
@PreMatching
public class AuthorizationFilter implements ContainerRequestFilter {

    @ConfigProperty(name = "enable.authorization", defaultValue = "true")
    boolean enableAuthorization;

    @Inject
    UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if(enableAuthorization){
            userService.findByUserId(getEmailJWT(requestContext)).onItem().transformToUni(userProfile -> {
                if (!havePermission(userProfile, UserProfileEnum.ADMIN)) {
                    throw new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED, AppErrorCodeEnum.ATMLCB_401);
                }
                return null;
            });
        }
    }
}
