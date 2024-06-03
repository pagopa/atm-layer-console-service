package it.gov.pagopa.atmlayer.service.consolebackend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ProfileDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorCodeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.AtmLayerException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;

import java.util.Base64;

@NoArgsConstructor
public class HeadersUtils {

    private static final String  HEADER_AUTHORIZATION = "Authorization";
    private static final String CLAIM_EMAIL = "email";

    public static String extractTokenMiddlePart(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid token format");
        }
        return parts[1];
    }

    public static JsonNode getPayload(String base64String) {
        String payload = new String(Base64.getUrlDecoder().decode(base64String));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return rootNode;
    }

    public static String getEmailJWT(ContainerRequestContext containerRequestContext) {
        String middlePart = extractTokenMiddlePart(containerRequestContext.getHeaderString(HEADER_AUTHORIZATION));
        try {
            return getPayload(middlePart).get(CLAIM_EMAIL).asText();
        }catch (Exception exception){
            throw new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED, AppErrorCodeEnum.ATMLCB_401);
        }

    }

    public static boolean havePermission(UserDTO userDTO, UserProfileEnum vision) {
        if (userDTO.getProfiles() == null || userDTO.getProfiles().isEmpty()) {
            return false;
        }
        return userDTO.getProfiles().stream()
                .anyMatch(profile -> profile.getProfileId() == vision.getValue());
    }

}
