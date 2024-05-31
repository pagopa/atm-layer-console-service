package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum UserProfileEnum {

    READ_GESTIONE_FLUSSI(1, "Gestione flussi in lettura"),
    WRITE_GESTIONE_FLUSSI(2, "Gestione flussi in scrittura"),
    DEPLOY_BPMN(3, "Rilascio BPM"),
    EMULATOR(4, "Emulator"),
    GESTIONE_UTENTI(5, "Gestione utenti");

    private final int value;
    private final String description;
    private static final Map<Integer, UserProfileEnum> map = new HashMap<>();

    static {
        for (UserProfileEnum profileEnum : UserProfileEnum.values()) {
            map.put(profileEnum.value, profileEnum);
        }
    }

    public static UserProfileEnum valueOf(int profile) {
        UserProfileEnum result = map.get(profile);
        if (result == null) {
            throw new IllegalArgumentException("No enum constant with value " + profile);
        }
        return map.get(profile);
    }
}
