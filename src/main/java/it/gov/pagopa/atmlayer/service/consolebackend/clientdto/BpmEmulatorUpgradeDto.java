package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.FormParam;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class BpmEmulatorUpgradeDto {

    @FormParam("uuid")
    @NotNull(message = "uuid is required")
    private UUID uuid;

    @FormParam("functionType")
    @NotNull(message = "function type is required")
    private String functionType;

    @FormParam("version")
    @NotNull(message = "version is required")
    private Long version;

}
