package it.gov.pagopa.atmlayer.service.consolebackend.utils;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
class HeadersUtilsTest {


    @Test
    void testExtractTokenMiddlePart_ValidToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        String middlePart = HeadersUtils.extractTokenMiddlePart(token);
        assertEquals("eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ", middlePart);
    }

    @Test
    void testExtractTokenMiddlePart_InvalidToken() {
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ";
        assertThrows(IllegalArgumentException.class, () -> HeadersUtils.extractTokenMiddlePart(invalidToken));
    }

    @Test
    void testGetPayload_ValidBase64String() {
        String base64String = "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ";
        JsonNode payload = HeadersUtils.getPayload(base64String);
        assertNotNull(payload);
        assertEquals("1234567890", payload.get("sub").asText());
        assertEquals("John Doe", payload.get("name").asText());
        assertEquals(1516239022, payload.get("iat").asInt());
    }

    @Test
    void testExtractTokenMiddlePart_NullToken() {
        assertThrows(NullPointerException.class, () -> HeadersUtils.extractTokenMiddlePart(null));
    }

    @Test
    void testGetPayload_InvalidBase64String() {
        String invalidBase64String = "ThisIsNotABase64String";
        assertThrows(RuntimeException.class, () -> HeadersUtils.getPayload(invalidBase64String));
    }

    @Test
    void testGetEmailJWT_NullContainerRequestContext() {
        assertThrows(NullPointerException.class, () -> HeadersUtils.getEmailJWT(null));
    }

    @Test
    void testGetEmailJWT_NullAuthorizationHeader() {
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn(null);

        assertThrows(NullPointerException.class, () -> HeadersUtils.getEmailJWT(containerRequestContext));
    }

    @Test
    void testGetUserIdJWT_NullContainerRequestContext() {
        assertThrows(NullPointerException.class, () -> HeadersUtils.getUserIdJWT(null));
    }

    @Test
    void testGetUserIdJWT_NullAuthorizationHeader() {
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn(null);

        assertThrows(NullPointerException.class, () -> HeadersUtils.getUserIdJWT(containerRequestContext));
    }

}
