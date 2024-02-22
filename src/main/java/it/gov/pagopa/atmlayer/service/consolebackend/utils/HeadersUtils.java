package it.gov.pagopa.atmlayer.service.consolebackend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfileDto;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import jakarta.ws.rs.container.ContainerRequestContext;

import java.util.Base64;

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
        return getPayload(middlePart).get(CLAIM_EMAIL).asText();

    }

    public static boolean havePermission(UserProfileDto userProfileDto, UserProfileEnum vision) {
        if (userProfileDto == null) {
            return false;
        }
        if (vision == UserProfileEnum.GUEST) {
            return true;
        } else if (vision == UserProfileEnum.OPERATOR) {
            return userProfileDto.getProfile() == UserProfileEnum.OPERATOR || userProfileDto.getProfile() == UserProfileEnum.ADMIN;
        } else {
            return userProfileDto.getProfile() == UserProfileEnum.ADMIN;
        }
    }

}
