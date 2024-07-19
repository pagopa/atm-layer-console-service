package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.UserProfileWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfilesDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfilesInsertionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    UserProfileWebClient userProfileWebClient;

    @InjectMocks
    UserProfileServiceImpl userProfileServiceImpl;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testInsertUserProfiles() {
        UserProfilesInsertionDTO userProfilesInsertionDTO = new UserProfilesInsertionDTO();
        List<UserProfilesDTO> userProfilesDTOList = List.of(new UserProfilesDTO());
        when(userProfileWebClient.insert(any(UserProfilesInsertionDTO.class))).thenReturn(Uni.createFrom().item(userProfilesDTOList));

        Uni<List<UserProfilesDTO>> result = userProfileServiceImpl.insertUserProfiles(userProfilesInsertionDTO);

        assertEquals(userProfilesDTOList, result.await().indefinitely());
        verify(userProfileWebClient).insert(userProfilesInsertionDTO);
    }

    @Test
    void testFindById() {
        String userId = "test-user-id";
        int profileId = 1;
        UserProfilesDTO userProfilesDTO = new UserProfilesDTO();
        when(userProfileWebClient.getById(anyString(), anyInt())).thenReturn(Uni.createFrom().item(userProfilesDTO));

        Uni<UserProfilesDTO> result = userProfileServiceImpl.findById(userId, profileId);

        assertEquals(userProfilesDTO, result.await().indefinitely());
        verify(userProfileWebClient).getById(userId, profileId);
    }

    @Test
    void testDeleteUserProfiles() {
        String userId = "test-user-id";
        int profileId = 1;
        when(userProfileWebClient.deleteUserProfiles(anyString(), anyInt())).thenReturn(Uni.createFrom().voidItem());

        Uni<Void> result = userProfileServiceImpl.deleteUserProfiles(userId, profileId);

        assertNull(result.await().indefinitely());
        verify(userProfileWebClient).deleteUserProfiles(userId, profileId);
    }
}

