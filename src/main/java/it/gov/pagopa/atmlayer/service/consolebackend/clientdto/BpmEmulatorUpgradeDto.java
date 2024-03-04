package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.reactive.PartType;

import java.io.File;
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
