package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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

    private String path;

    private String description;
}
