package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@NoArgsConstructor
public class ResourceMultipleCreationDtoJSON {

    @NonNull
    private List<String> fileList;
    @NonNull
    private List<String> filenameList;
    @NonNull
    private NoDeployableResourceType resourceType;
    @Length(max = 150)
    private String path;
    @Length(max = 255)
    private String description;
}
