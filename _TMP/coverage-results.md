
# Coverage Report: JaCoCo

* FileS3DtoTest (QuarkusTest)
      
      
| Outcome                 | Value                                                               |
|-------------------------|---------------------------------------------------------------------|
| Code Coverage %         | 91.88%               |
| :heavy_check_mark: Number of Lines Covered | 441    |
| :x: Number of Lines Missed  | 39     |
| Total Number of Lines   | 480     |


## Details:

    
### it/gov/pagopa/atmlayer/service/consolebackend/configuration

<details>
    <summary>
:x: AuthorizationFilter.java
    </summary>

        
#### Lines Missed:
        
</details>

    

<details>
    <summary>
:heavy_check_mark: HttpRequestLogger.java
    </summary>

        
#### All Lines Covered!
        
- Line #13
```
        String uri = requestContext.getUriInfo().getAbsolutePath() != null ? Encode.forJava(requestContext.getUriInfo().getAbsolutePath().toString()) : null;
```
- Line #15
```
        String headers = requestContext.getHeaders() != null ? Encode.forJava(requestContext.getHeaders().toString()) : null;
```
</details>

    

<details>
    <summary>
:heavy_check_mark: CorsFilter.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:heavy_check_mark: HttpResponseLogger.java
    </summary>

        
#### All Lines Covered!
        
</details>

    
### it/gov/pagopa/atmlayer/service/consolebackend/resource

<details>
    <summary>
:x: ProfileResource.java
    </summary>

        
#### Lines Missed:
        
</details>

    

<details>
    <summary>
:x: WorkflowResource.java
    </summary>

        
#### Lines Missed:
        
</details>

    

<details>
    <summary>
:heavy_check_mark: UserProfileResource.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:x: ResourceResource.java
    </summary>

        
#### Lines Missed:
        
</details>

    

<details>
    <summary>
:heavy_check_mark: TaskResource.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:x: BpmnResource.java
    </summary>

        
#### Lines Missed:
        
</details>

    

<details>
    <summary>
:heavy_check_mark: InfoResource.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:x: UserResource.java
    </summary>

        
#### Lines Missed:
        
</details>

    
### it/gov/pagopa/atmlayer/service/consolebackend/enums

<details>
    <summary>
:heavy_check_mark: OutcomeEnum.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:heavy_check_mark: NoDeployableResourceType.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:heavy_check_mark: DeployableResourceType.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:heavy_check_mark: StatusEnum.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:heavy_check_mark: S3ResourceTypeEnum.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:heavy_check_mark: AppErrorCodeEnum.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:x: UserProfileEnum.java
    </summary>

        
#### Lines Missed:
        
</details>

    

<details>
    <summary>
:heavy_check_mark: Channel.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:heavy_check_mark: PeripheralStatus.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:heavy_check_mark: Command.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:heavy_check_mark: AppErrorType.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:heavy_check_mark: EppMode.java
    </summary>

        
#### All Lines Covered!
        
</details>

    
### it/gov/pagopa/atmlayer/service/consolebackend/service

<details>
    <summary>
:x: TaskService.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: WorkflowService.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: UserProfileService.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: ResourceService.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: UserService.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: ProfileService.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: BpmnService.java
    </summary>

        
</details>

    
### it/gov/pagopa/atmlayer/service/consolebackend/utils

<details>
    <summary>
:x: HeadersUtils.java
    </summary>

        
#### Lines Missed:
        
- Line #52
```
        }catch (Exception exception){
```
- Line #74
```
        } catch (IOException e) {
```
- Line #76
```
        }
```
</details>

    

<details>
    <summary>
:x: ConstraintViolationMappingUtils.java
    </summary>

        
</details>

    

<details>
    <summary>
:heavy_check_mark: ConstraintViolationMappingUtilsImpl.java
    </summary>

        
#### All Lines Covered!
        
</details>

    
### it/gov/pagopa/atmlayer/service/consolebackend/clientdto

<details>
    <summary>
:x: VerifyResponse.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: FileS3Dto.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: BpmnBankConfigDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: ResourceFrontEndDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: WorkflowResourceCreationDto.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: BpmnVersionFrontEndDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: ProfileDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: ResourceCreationDto.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: ResourceMultipleCreationDtoJSON.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: ResourceMultipleCreationDto.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: UserProfilesInsertionDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: UserInsertionWithProfilesDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: BpmnCreationDto.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: UserProfilesDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: WorkflowResourceDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: BpmnUpgradeDto.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: ResourceDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: ResourceFileDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: BpmnDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: WorkflowResourceFrontEndDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: UserDTO.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: BankConfigTripletDto.java
    </summary>

        
#### Lines Missed:
        
- Line #29
```
                bankConfigTripletDto.terminalId.equals(this.terminalId));
```
</details>

    

<details>
    <summary>
:x: BpmEmulatorUpgradeDto.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: UserInsertionDTO.java
    </summary>

        
</details>

    
### it/gov/pagopa/atmlayer/service/consolebackend/exception/mapper

<details>
    <summary>
:x: GlobalExceptionMapperImpl.java
    </summary>

        
#### Lines Missed:
        
</details>

    
### it/gov/pagopa/atmlayer/service/consolebackend/model

<details>
    <summary>
:x: ATMLayerValidationErrorResponse.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: ATMLayerErrorResponse.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: PageInfo.java
    </summary>

        
</details>

    
### it/gov/pagopa/atmlayer/service/consolebackend/exception

<details>
    <summary>
:heavy_check_mark: AtmLayerException.java
    </summary>

        
#### All Lines Covered!
        
</details>

    
### it/gov/pagopa/atmlayer/service/consolebackend/clientdto/taskdto

<details>
    <summary>
:x: Button.java
    </summary>

        
#### Lines Missed:
        
</details>

    

<details>
    <summary>
:x: Task.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: Device.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: OutcomeResponse.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: Peripheral.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: PanInfo.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: Template.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: Scene.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: State.java
    </summary>

        
</details>

    
### it/gov/pagopa/atmlayer/service/consolebackend/appdto

<details>
    <summary>
:x: FileS3Dto.java
    </summary>

        
</details>

    
### it/gov/pagopa/atmlayer/service/consolebackend/service/impl

<details>
    <summary>
:x: BpmnServiceImpl.java
    </summary>

        
#### Lines Missed:
        
</details>

    

<details>
    <summary>
:x: ProfileServiceImpl.java
    </summary>

        
#### Lines Missed:
        
</details>

    

<details>
    <summary>
:heavy_check_mark: UserProfileServiceImpl.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:heavy_check_mark: WorkflowServiceImpl.java
    </summary>

        
#### All Lines Covered!
        
</details>

    

<details>
    <summary>
:x: ResourceServiceImpl.java
    </summary>

        
#### Lines Missed:
        
</details>

    

<details>
    <summary>
:x: UserServiceImpl.java
    </summary>

        
#### Lines Missed:
        
</details>

    

<details>
    <summary>
:heavy_check_mark: TaskServiceImpl.java
    </summary>

        
#### All Lines Covered!
        
</details>

    
### it/gov/pagopa/atmlayer/service/consolebackend/client

<details>
    <summary>
:x: WorkflowWebClient.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: TaskWebClient.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: UserWebClient.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: ProfileWebClient.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: UserProfileWebClient.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: CamundaWebClient.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: BpmnWebClient.java
    </summary>

        
</details>

    

<details>
    <summary>
:x: ResourceWebClient.java
    </summary>

        
</details>

    
### it/gov/pagopa/atmlayer/service/consolebackend

<details>
    <summary>
:heavy_check_mark: App.java
    </summary>

        
#### All Lines Covered!
        
</details>

    
