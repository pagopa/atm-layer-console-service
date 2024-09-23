package it.gov.pagopa.atmlayer.service.consolebackend.utils;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.AtmLayerException;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

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
    void testGetEmailJWT_AccessDeniedException() {
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("test.token.extraction");
        assertThrows(AtmLayerException.class, () -> HeadersUtils.getEmailJWT(containerRequestContext));
    }

    @Test
    void testGetUserIdJWT_NullContainerRequestContext() {
        assertThrows(NullPointerException.class, () -> HeadersUtils.getUserIdJWT(null));
    }

    @Test
    void testGetUserIdJWT_AccessDeniedException() {
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("test.token.extraction");

        assertThrows(AtmLayerException.class, () -> HeadersUtils.getUserIdJWT(containerRequestContext));
    }

    @Test
    void testHavePermission_nullProfiles() {
        UserDTO userDTO = new UserDTO();
        assertFalse(HeadersUtils.havePermission(userDTO, UserProfileEnum.GESTIONE_UTENTI));
    }

    @Test
    void testHavePermission_emptyProfiles() {
        UserDTO userDTO = new UserDTO();
        userDTO.setProfiles(new ArrayList<>());
        assertFalse(HeadersUtils.havePermission(userDTO, UserProfileEnum.GESTIONE_UTENTI));
    }

    @Test
    void testFromFileToString(){
        File file = new File("src/test/resources/Test.bpmn");
        String expectedBase64 = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPGJwbW46ZGVmaW5pdGlvbnMgeG1sbnM6YnBtbj0iaHR0cDovL3d3dy5vbWcub3JnL3NwZWMvQlBNTi8yMDEwMDUyNC9NT0RFTCIgeG1sbnM6YnBtbmRpPSJodHRwOi8vd3d3Lm9tZy5vcmcvc3BlYy9CUE1OLzIwMTAwNTI0L0RJIiB4bWxuczpkYz0iaHR0cDovL3d3dy5vbWcub3JnL3NwZWMvREQvMjAxMDA1MjQvREMiIHhtbG5zOmNhbXVuZGE9Imh0dHA6Ly9jYW11bmRhLm9yZy9zY2hlbWEvMS4wL2JwbW4iIHhtbG5zOmRpPSJodHRwOi8vd3d3Lm9tZy5vcmcvc3BlYy9ERC8yMDEwMDUyNC9ESSIgeG1sbnM6bW9kZWxlcj0iaHR0cDovL2NhbXVuZGEub3JnL3NjaGVtYS9tb2RlbGVyLzEuMCIgaWQ9IkRlZmluaXRpb25zXzBqY3BqMHEiIHRhcmdldE5hbWVzcGFjZT0iaHR0cDovL2JwbW4uaW8vc2NoZW1hL2JwbW4iIGV4cG9ydGVyPSJDYW11bmRhIE1vZGVsZXIiIGV4cG9ydGVyVmVyc2lvbj0iNS4xNS4yIiBtb2RlbGVyOmV4ZWN1dGlvblBsYXRmb3JtPSJDYW11bmRhIFBsYXRmb3JtIiBtb2RlbGVyOmV4ZWN1dGlvblBsYXRmb3JtVmVyc2lvbj0iNy4xOS4wIj4KICA8YnBtbjpwcm9jZXNzIGlkPSJkZW1vMTFfMDYiIGlzRXhlY3V0YWJsZT0idHJ1ZSIgY2FtdW5kYTpoaXN0b3J5VGltZVRvTGl2ZT0iMTgwIj4KICAgIDxicG1uOnN0YXJ0RXZlbnQgaWQ9IlN0YXJ0RXZlbnRfMSI+CiAgICAgIDxicG1uOmV4dGVuc2lvbkVsZW1lbnRzPgogICAgICAgIDxjYW11bmRhOmV4ZWN1dGlvbkxpc3RlbmVyIGRlbGVnYXRlRXhwcmVzc2lvbj0iJHtwb3B1bGF0ZUJ1c2luZXNzS2V5fSIgZXZlbnQ9InN0YXJ0IiAvPgogICAgICA8L2JwbW46ZXh0ZW5zaW9uRWxlbWVudHM+CiAgICAgIDxicG1uOm91dGdvaW5nPkZsb3dfMG0xdDZjODwvYnBtbjpvdXRnb2luZz4KICAgIDwvYnBtbjpzdGFydEV2ZW50PgogICAgPGJwbW46c2VxdWVuY2VGbG93IGlkPSJGbG93XzBtMXQ2YzgiIHNvdXJjZVJlZj0iU3RhcnRFdmVudF8xIiB0YXJnZXRSZWY9InJlc3RfY2FsbCIgLz4KICAgIDxicG1uOnNlcnZpY2VUYXNrIGlkPSJyZXN0X2NhbGwiIG5hbWU9ImZpcnN0VGFzayI+CiAgICAgIDxicG1uOmV4dGVuc2lvbkVsZW1lbnRzPgogICAgICAgIDxjYW11bmRhOmNvbm5lY3Rvcj4KICAgICAgICAgIDxjYW11bmRhOmlucHV0T3V0cHV0PgogICAgICAgICAgICA8Y2FtdW5kYTppbnB1dFBhcmFtZXRlciBuYW1lPSJtZXRob2QiPkdFVDwvY2FtdW5kYTppbnB1dFBhcmFtZXRlcj4KICAgICAgICAgICAgPGNhbXVuZGE6aW5wdXRQYXJhbWV0ZXIgbmFtZT0idXJsIj5odHRwczovL3JlcXJlcy5pbi9hcGkvdXNlcnM/cGFnZT0yPC9jYW11bmRhOmlucHV0UGFyYW1ldGVyPgogICAgICAgICAgICA8Y2FtdW5kYTppbnB1dFBhcmFtZXRlciBuYW1lPSJoZWFkZXJzIj4KICAgICAgICAgICAgICA8Y2FtdW5kYTptYXA+CiAgICAgICAgICAgICAgICA8Y2FtdW5kYTplbnRyeSBrZXk9IkNvbnRlbnQtVHlwZSI+YXBwbGljYXRpb24vanNvbjwvY2FtdW5kYTplbnRyeT4KICAgICAgICAgICAgICA8L2NhbXVuZGE6bWFwPgogICAgICAgICAgICA8L2NhbXVuZGE6aW5wdXRQYXJhbWV0ZXI+CiAgICAgICAgICAgIDxjYW11bmRhOm91dHB1dFBhcmFtZXRlciBuYW1lPSJyZXMiPiR7UyhyZXNwb25zZSkucHJvcCgiZGF0YSIpfTwvY2FtdW5kYTpvdXRwdXRQYXJhbWV0ZXI+CiAgICAgICAgICA8L2NhbXVuZGE6aW5wdXRPdXRwdXQ+CiAgICAgICAgICA8Y2FtdW5kYTpjb25uZWN0b3JJZD5odHRwLWNvbm5lY3RvcjwvY2FtdW5kYTpjb25uZWN0b3JJZD4KICAgICAgICA8L2NhbXVuZGE6Y29ubmVjdG9yPgogICAgICA8L2JwbW46ZXh0ZW5zaW9uRWxlbWVudHM+CiAgICAgIDxicG1uOmluY29taW5nPkZsb3dfMG0xdDZjODwvYnBtbjppbmNvbWluZz4KICAgICAgPGJwbW46b3V0Z29pbmc+Rmxvd18wNnYycG02PC9icG1uOm91dGdvaW5nPgogICAgPC9icG1uOnNlcnZpY2VUYXNrPgogICAgPGJwbW46c2VxdWVuY2VGbG93IGlkPSJGbG93XzA2djJwbTYiIHNvdXJjZVJlZj0icmVzdF9jYWxsIiB0YXJnZXRSZWY9IkFjdGl2aXR5XzFuaTB6aTYiIC8+CiAgICA8YnBtbjp1c2VyVGFzayBpZD0iQWN0aXZpdHlfMW5pMHppNiI+CiAgICAgIDxicG1uOmluY29taW5nPkZsb3dfMDZ2MnBtNjwvYnBtbjppbmNvbWluZz4KICAgIDwvYnBtbjp1c2VyVGFzaz4KICA8L2JwbW46cHJvY2Vzcz4KICA8YnBtbmRpOkJQTU5EaWFncmFtIGlkPSJCUE1ORGlhZ3JhbV8xIj4KICAgIDxicG1uZGk6QlBNTlBsYW5lIGlkPSJCUE1OUGxhbmVfMSIgYnBtbkVsZW1lbnQ9IlByb2Nlc3NfMXF4c3ZwZCI+CiAgICAgIDxicG1uZGk6QlBNTlNoYXBlIGlkPSJfQlBNTlNoYXBlX1N0YXJ0RXZlbnRfMiIgYnBtbkVsZW1lbnQ9IlN0YXJ0RXZlbnRfMSI+CiAgICAgICAgPGRjOkJvdW5kcyB4PSIxNzkiIHk9Ijk5IiB3aWR0aD0iMzYiIGhlaWdodD0iMzYiIC8+CiAgICAgIDwvYnBtbmRpOkJQTU5TaGFwZT4KICAgICAgPGJwbW5kaTpCUE1OU2hhcGUgaWQ9IkFjdGl2aXR5XzFnZWs5a2VfZGkiIGJwbW5FbGVtZW50PSJyZXN0X2NhbGwiPgogICAgICAgIDxkYzpCb3VuZHMgeD0iMzEwIiB5PSI3NyIgd2lkdGg9IjEwMCIgaGVpZ2h0PSI4MCIgLz4KICAgICAgPC9icG1uZGk6QlBNTlNoYXBlPgogICAgICA8YnBtbmRpOkJQTU5TaGFwZSBpZD0iQWN0aXZpdHlfMTU4YW04a19kaSIgYnBtbkVsZW1lbnQ9IkFjdGl2aXR5XzFuaTB6aTYiPgogICAgICAgIDxkYzpCb3VuZHMgeD0iNzEwIiB5PSI3NyIgd2lkdGg9IjEwMCIgaGVpZ2h0PSI4MCIgLz4KICAgICAgPC9icG1uZGk6QlBNTlNoYXBlPgogICAgICA8YnBtbmRpOkJQTU5FZGdlIGlkPSJGbG93XzBtMXQ2YzhfZGkiIGJwbW5FbGVtZW50PSJGbG93XzBtMXQ2YzgiPgogICAgICAgIDxkaTp3YXlwb2ludCB4PSIyMTUiIHk9IjExNyIgLz4KICAgICAgICA8ZGk6d2F5cG9pbnQgeD0iMzEwIiB5PSIxMTciIC8+CiAgICAgIDwvYnBtbmRpOkJQTU5FZGdlPgogICAgICA8YnBtbmRpOkJQTU5FZGdlIGlkPSJGbG93XzA2djJwbTZfZGkiIGJwbW5FbGVtZW50PSJGbG93XzA2djJwbTYiPgogICAgICAgIDxkaTp3YXlwb2ludCB4PSI0MTAiIHk9IjExNyIgLz4KICAgICAgICA8ZGk6d2F5cG9pbnQgeD0iNzEwIiB5PSIxMTciIC8+CiAgICAgIDwvYnBtbmRpOkJQTU5FZGdlPgogICAgPC9icG1uZGk6QlBNTlBsYW5lPgogIDwvYnBtbmRpOkJQTU5EaWFncmFtPgo8L2JwbW46ZGVmaW5pdGlvbnM+Cg==";
        assertEquals(expectedBase64, HeadersUtils.fromFileToString(file));
    }

}
