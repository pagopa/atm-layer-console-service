package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.Channel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "Informazioni del device")
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Device {

    /*
     * Device bank ID.
     */
    @NotNull(message = "BankID non può essere null")
    @Schema(required = true, description = "Il codice identificativo della banca (o codice ABI)", example = "02008", maxLength = 255)
    private String bankId;

    /*
     * Device branch ID.
     */
    @Schema(required = true, description = "Il codice identificativo della filiale (o codice CAB)", example = "12345", maxLength = 255)
    private String branchId;

    /*
     * Device ID.
     */
    @Pattern(regexp = "^[0-9]{1,4}$", message = "Device ID deve matchare l'espressione regolare")
    @Schema(description = "Il codice identificativo dello sportello ATM (Codice Sportello o S.A. del Quadro Informativo. SPE-DEF-200)", example = "0001", maxLength = 255)
    private String code;

    /*
     * Terminal ID.
     */
    @Pattern(regexp = "^[0-9a-zA-Z]{1,10}$", message = "Terminal ID deve matchare l'espressione regolare\"")
    @Schema(description = "Il codice identificativo del dispositivo (o Terminal ID)", example = "ABCD1234", maxLength = 255)
    private String terminalId;

    /*
     * Terminal operation timestamp.
     */
    @Schema(description = "Timestamp della richiesta",
            format = "date-time",
            pattern = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$",
            maxLength = 20,
            example = "2023-10-31T17:30:00")
    private Date opTimestamp;

    /*
     * Terminal channel.
     */
    @Schema(description = "Identificativo del canale del dispositivo", implementation = Channel.class)
    private Channel channel;

    @Schema(description = "Lista delle periferiche del device", maxItems = 1000)
    private List<Peripheral> peripherals;
}
