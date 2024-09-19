package it.gov.pagopa.atmlayer.service.consolebackend.appdto;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class FileS3Dto {

    @Schema (format = "byte", maxLength = 5000)
    String fileContent;
}
