package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class FileS3Dto {

    @Schema(format = "binary", maxLength = 5000)
    String fileContent;
}
