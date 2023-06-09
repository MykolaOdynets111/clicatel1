Feature: Endpoint API

  @TestCaseId("https://jira.clickatell.com/browse/CCH-622")
  Scenario Outline: CH :: Admin API : GET /admin/endpoints should return all available endpoints for a provider.
    Given Validate all endpoint detail for Provider via Admin API
      | i.providerID   | i.versionID   | o.errorMessage   | o.responseCode   |
      | <i.providerID> | <i.versionID> | <o.errorMessage> | <o.responseCode> |

    Examples:
      | i.providerID                     | i.versionID | o.responseCode | o.errorMessage                              |
      | 0184f828214f6b7a03c711284b2b8e39 | v1          | 404            | Provider or specification version not found |
      | 0184f828214f6b7a                 | v1.0.0      | 404            | Provider or specification version not found |
      | 0184f828214f6b7a                 | v1          | 404            | Provider or specification version not found |
      #Bug logged:CCH-699 : Provider and VersionID Null|                                  |             |400       |Provider or specification version not found|
      #Bug logged:CCH-699 : Provider Null              |                                  | v1.0.0     |    400       |Bad Request|
      #Bug logged:CCH-699 : VersionID Null             | 0184f828214f6b7a03c711284b2b8e39 |            |    400      |Bad Request|


  @TestCaseId("https://jira.clickatell.com/browse/CCH-676")
  Scenario:CH :: Admin API : User should be able to verify all returned endpoints for a provider
    Given Validate all endpoint detail for Provider via Admin API
      | o.id                             | o.name                         | i.providerID                     | i.versionID | o.responseCode |
      | 0184f82f654de2b793510630141e9990 | Search customer                | 0184f828214f6b7a03c711284b2b8e39 | v1.0.0      | 200            |
      | 0184f82f654d988ff09d6d08e8499c3a | Create lead                    |                                  |             |                |
      | 0184f82f654ef40f88d3929be7ee05c7 | Get customer                   |                                  |             |                |
      | 0184f82f654ef7950075689898286775 | Update lead                    |                                  |             |                |
      | 0184f82f654eb488be883c9412a2e22c | Get User Custom Fields - LEADS |                                  |             |                |
      | 0184f82f654e445ba7e2e46ddb638085 | Get TAGS                       |                                  |             |                |
      | 0184f82f654ec9cdb6eed670afdd7e36 | Retrieve account details       |                                  |             |                |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-626")
  Scenario Outline: CH :: Internal API : GET /internal/endpoints should return available endpoints for a provider.
    Given Validate all endpoint detail for Provider via Internal API
      | i.providerID   | i.versionID   | o.errorMessage   | o.responseCode   |
      | <i.providerID> | <i.versionID> | <o.errorMessage> | <o.responseCode> |

    Examples:
      | i.providerID                     | i.versionID | o.responseCode | o.errorMessage                              |
      | 0184f828214f6b7a03c711284b2b8e39 | v1          | 404            | Provider or specification version not found |
      | 0184f828214f6b7a                 | v1.0.0      | 404            | Provider or specification version not found |
      | 0184f828214f6b7a                 | v1          | 404            | Provider or specification version not found |
      #Bug logged:CCH-699 : Provider and VersionID Null|                                  |             |400       |Provider or specification version not found|
      #Bug logged:CCH-699 : Provider Null              |                                  | v1.0.0     |    400       |Bad Request|
      #Bug logged:CCH-699 : VersionID Null             | 0184f828214f6b7a03c711284b2b8e39 |            |    400      |Bad Request|


  @TestCaseId("https://jira.clickatell.com/browse/CCH-683")
  Scenario:CH :: Internal API : User should be able to verify all returned endpoints for a provider
    Given Validate all endpoint detail for Provider via Internal API
      | o.id                             | o.name                         | i.providerID                     | i.versionID | o.responseCode |
      | 0184f82f654de2b793510630141e9990 | Search customer                | 0184f828214f6b7a03c711284b2b8e39 | v1.0.0      | 200            |
      | 0184f82f654d988ff09d6d08e8499c3a | Create lead                    |                                  |             |                |
      | 0184f82f654ef40f88d3929be7ee05c7 | Get customer                   |                                  |             |                |
      | 0184f82f654ef7950075689898286775 | Update lead                    |                                  |             |                |
      | 0184f82f654eb488be883c9412a2e22c | Get User Custom Fields - LEADS |                                  |             |                |
      | 0184f82f654e445ba7e2e46ddb638085 | Get TAGS                       |                                  |             |                |
      | 0184f82f654ec9cdb6eed670afdd7e36 | Retrieve account details       |                                  |             |                |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-630")
  Scenario Outline: CH :: Admin API : GET /admin/specifications should return all specifications for a provider
    Given Validate all specifications for a provider via Admin API
      | i.providerID                                 | <i.providerID>                                 |
      | o.id                                         | <o.id>                                         |
      | o.errorMessage                               | <o.errorMessage>                               |
      | o.responseCode                               | <o.responseCode>                               |
      | o.authDetails.grantType                      | <o.authDetails.grantType>                      |
      | o.authDetails.authPath                       | <o.authDetails.authPath>                       |
      | o.authDetails.refreshPath                    | <o.authDetails.refreshPath>                    |
      | o.authDetails.tokenPath                      | <o.authDetails.tokenPath>                      |
      | o.authDetails.tokenExpirationDurationSeconds | <o.authDetails.tokenExpirationDurationSeconds> |
      | o.authDetails.scopes                         | <o.authDetails.scopes>                         |
      | o.staticSecrets                              | <o.staticSecrets>                              |
      | o.authDetails.authorizationHeaderValuePrefix | <o.authDetails.authorizationHeaderValuePrefix> |
      | o.authDetails.authType                       | <o.authDetails.authType>                       |
      | o.version                                    | <o.version>                                    |
      | o.openApiSpecS3Key                           | <o.openApiSpecS3Key>                           |

    Examples:
      | i.providerID                     | o.responseCode | o.errorMessage           | o.id                             | o.authDetails.grantType | o.authDetails.authPath | o.authDetails.refreshPath | o.authDetails.tokenPath | o.authDetails.tokenExpirationDurationSeconds | o.authDetails.scopes | o.staticSecrets | o.authDetails.authorizationHeaderValuePrefix | o.authDetails.authType | o.version | o.openApiSpecS3Key                       |
      | 0184f828214f6b7a03c711284b2b8e39 | 200            |                          | 0184f82f61a2613547072daf82115294 | AUTHORIZATION_CODE      | /oauth2/authorize      | /oauth2/token             | /oauth2/token           | 300                                          | read+write+profile   |             | Bearer                                       | OAUTH                  | v1.0.0    | /0184f828214f6b7a03c711284b2b8e39/v1.0.0 |
      | 0184f828214f6b7a03c711284b2false | 404            | Specifications not found |                                  |                         |                        |                           |                         |                                              |                      |                 |                                              |                        |           |                                          |
      #Bug logged:CCH-699|                                  |404            | Bad Request      |                                |                        |                                 |                             |                                     |                                         |      |                                   |                                              |                         |                      |                                             |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-623")
  Scenario Outline:CH :: Admin API : Verify that GET /Admin/endpoints/{endpoint} should return available endpoint details with correct requestParameter details
    Given Validate specific endpoint detail for Provider via Admin api
      | i.endpointID   | o.id   | o.errorMessage   | o.responseCode   | o.operationName   | o.requestParameters.id   | o.requestParameters.label   | o.requestParameters.placeholder   | o.requestParameters.default   | o.requestParameters.required   | o.requestParameters.constraints   | o.requestParameters.parameterType   | o.requestParameters.availableOptions   | o.requestParameters.isArray   | o.requestParameters.presentationType   | o.requestParameters.repeatableGroupId   | o.requestParameters.repeatableGroupName   | o.requestParameters.placementType   | o.requestParameters.destinationPath   | o.responseSample.statusCode   | o.responseSample.properties   |
      | <i.endpointID> | <o.id> | <o.errorMessage> | <o.responseCode> | <o.operationName> | <o.requestParameters.id> | <o.requestParameters.label> | <o.requestParameters.placeholder> | <o.requestParameters.default> | <o.requestParameters.required> | <o.requestParameters.constraints> | <o.requestParameters.parameterType> | <o.requestParameters.availableOptions> | <o.requestParameters.isArray> | <o.requestParameters.presentationType> | <o.requestParameters.repeatableGroupId> | <o.requestParameters.repeatableGroupName> | <o.requestParameters.placementType> | <o.requestParameters.destinationPath> | <o.responseSample.statusCode> | <o.responseSample.properties> |

    Examples:
      | i.endpointID                     | o.id                             | o.errorMessage     | o.responseCode | o.operationName | o.requestParameters.id           | o.requestParameters.label | o.requestParameters.placeholder | o.requestParameters.default | o.requestParameters.required | o.requestParameters.constraints | o.requestParameters.parameterType | o.requestParameters.availableOptions | o.requestParameters.isArray | o.requestParameters.presentationType | o.requestParameters.repeatableGroupId | o.requestParameters.repeatableGroupName | o.requestParameters.placementType | o.requestParameters.destinationPath | o.responseSample.statusCode | o.responseSample.properties |
      | 0184f82664fb33c510da20cc404772ce | 0184f82664fb33c510da20cc404772ce |                    | 200            | Get customer    | 0184f82664fb87852199fc962680a301 | User ID                   | User ID                         |                             | true                         |                                 | NUMBER                            |                                      | false                       | NUMBER                               |                                       |                                         | PATH                              | user_id                             | 200                         |                             |
      | 0184f82664fb33c510da20cIncorrect |                                  | Endpoint not found | 404            |                 |                                  |                           |                                 |                             |                              |                                 |                                   |                                      |                             |                                      |                                       |                                         |                                   |                                     |                             |                             |
      #Bug logged:CCH-699|                                  | 0184f82664fb33c510da20cc404772ce|Bad Request     |404                  |Get customer         |0184f82664fb87852199fc962680a301|User ID                      |User ID                           |                              |  true                      |                               |NUMBER                           |                                    |false                          |NUMBER                                 |                                       |                                       |PATH                             |user_id                             |200                          |                           |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-686")
  Scenario: CH :: Admin API : Verify "response sample" from GET /admin/endpoints/{endpoint}
    Given Validate specific endpoint detail for Provider via Admin api
      | o.sourceRef                         | o.label                        | o.type  | o.isArray | i.endpointID                     | o.responseCode | o.statusCode |
      | user/updated_at                     | Updated At                     | STRING  | false     | 0184f82664fb33c510da20cc404772ce | 200            | 200          |
      | user/verified                       | Verified?                      | BOOLEAN | false     |                                  |                |              |
      | user/url                            | URL                            | STRING  | false     |                                  |                |              |
      | user/two_factor_auth_enabled        | Is Two Factor Auth Enabled?    | BOOLEAN | false     |                                  |                |              |
      | user/time_zone                      | Time Zone                      | STRING  | false     |                                  |                |              |
      | user/ticket_restriction             | Ticket Restriction             | STRING  | false     |                                  |                |              |
      | user/tags                           | Tags                           | STRING  | true      |                                  |                |              |
      | user/suspended                      | Suspended                      | BOOLEAN | false     |                                  |                |              |
      | user/signature                      | Signature                      | STRING  | false     |                                  |                |              |
      | user/shared_phone_number            | Is Shared Phone Number?        | BOOLEAN | false     |                                  |                |              |
      | user/shared_agent                   | Is Shared Agent?               | BOOLEAN | false     |                                  |                |              |
      | user/shared                         | Is Shared?                     | BOOLEAN | false     |                                  |                |              |
      | user/role_type                      | Role Type                      | NUMBER  | false     |                                  |                |              |
      | user/role                           | Role                           | STRING  | false     |                                  |                |              |
      | user/restricted_agent               | Restricted Agent?              | BOOLEAN | false     |                                  |                |              |
      | user/report_csv                     | Is Report CSV?                 | BOOLEAN | false     |                                  |                |              |
      | user/remote_photo_url               | Remote Photo URL               | STRING  | false     |                                  |                |              |
      | user/photo/$thumbnails/size         | Thumbnail Size                 | NUMBER  | false     |                                  |                |              |
      | user/photo/$thumbnails/content_type | Thumbnail Content Type         | STRING  | false     |                                  |                |              |
      | user/photo/$thumbnails/content_url  | Thumbnail Content URL          | STRING  | false     |                                  |                |              |
      | user/photo/$thumbnails/name         | Thumbnail Name                 | STRING  | false     |                                  |                |              |
      | user/photo/$thumbnails/id           | Thumbnail ID                   | NUMBER  | false     |                                  |                |              |
      | user/photo/width                    | Photo Width                    | STRING  | false     |                                  |                |              |
      | user/photo/url                      | Photo URL                      | STRING  | false     |                                  |                |              |
      | user/photo/size                     | Photo Size                     | NUMBER  | false     |                                  |                |              |
      | user/photo/mapped_content_url       | Photo Mapped Content URL       | STRING  | false     |                                  |                |              |
      | user/photo/malware_scan_result      | Photo Malware Scan Result      | STRING  | false     |                                  |                |              |
      | user/photo/malware_access_override  | Photo Malware Access Override? | BOOLEAN | false     |                                  |                |              |
      | user/photo/inline                   | Photo Inline?                  | BOOLEAN | false     |                                  |                |              |
      | user/photo/id                       | Photo ID                       | NUMBER  | false     |                                  |                |              |
      | user/photo/height                   | Photo Height                   | STRING  | false     |                                  |                |              |
      | user/photo/file_name                | Photo File Name                | STRING  | false     |                                  |                |              |
      | user/photo/deleted                  | Photo Deleted?                 | BOOLEAN | false     |                                  |                |              |
      | user/photo/content_url              | Photo Content URL              | STRING  | false     |                                  |                |              |
      | user/photo/content_type             | Photo Content Type             | STRING  | false     |                                  |                |              |
      | user/phone                          | Phone                          | STRING  | false     |                                  |                |              |
      | user/organization_id                | Organization ID                | NUMBER  | false     |                                  |                |              |
      | user/only_private_comments          | Only Private Comments?         | BOOLEAN | false     |                                  |                |              |
      | user/notes                          | Notes                          | STRING  | false     |                                  |                |              |
      | user/name                           | Name                           | STRING  | false     |                                  |                |              |
      | user/moderator                      | Moderator                      | BOOLEAN | false     |                                  |                |              |
      | user/locale_id                      | Locale ID                      | NUMBER  | false     |                                  |                |              |
      | user/locale                         | Locale                         | STRING  | false     |                                  |                |              |
      | user/last_login_at                  | Last Login At                  | STRING  | false     |                                  |                |              |
      | user/id                             | ID                             | NUMBER  | false     |                                  |                |              |
      | user/iana_time_zone                 | IANA Time Zone                 | STRING  | false     |                                  |                |              |
      | user/external_id                    | External ID                    | STRING  | false     |                                  |                |              |
      | user/email                          | Email                          | STRING  | false     |                                  |                |              |
      | user/details                        | Details                        | STRING  | false     |                                  |                |              |
      | user/default_group_id               | Default Group ID               | NUMBER  | false     |                                  |                |              |
      | user/custom_role_id                 | Custom Role ID                 | NUMBER  | false     |                                  |                |              |
      | user/created_at                     | Created At                     | STRING  | false     |                                  |                |              |
      | user/chat_only                      | Chat Only?                     | BOOLEAN | false     |                                  |                |              |
      | user/alias                          | Alias                          | STRING  | false     |                                  |                |              |
      | user/active                         | Active?                        | BOOLEAN | false     |                                  |                |              |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-627")
  Scenario Outline: CH :: Internal API : Verify that GET /internal/endpoints/{endpoint} should return available endpoint details with correct requestParameter details
    Given Validate specific endpoint detail for Provider via Internal Api
      | i.endpointID   | o.id   | o.errorMessage   | o.responseCode   | o.operationName   | o.requestParameters.id   | o.requestParameters.label   | o.requestParameters.placeholder   | o.requestParameters.default   | o.requestParameters.required   | o.requestParameters.constraints   | o.requestParameters.parameterType   | o.requestParameters.availableOptions   | o.requestParameters.isArray   | o.requestParameters.presentationType   | o.requestParameters.repeatableGroupId   | o.requestParameters.repeatableGroupName   | o.requestParameters.placementType   | o.requestParameters.destinationPath   | o.responseSample.statusCode   | o.responseSample.properties   |
      | <i.endpointID> | <o.id> | <o.errorMessage> | <o.responseCode> | <o.operationName> | <o.requestParameters.id> | <o.requestParameters.label> | <o.requestParameters.placeholder> | <o.requestParameters.default> | <o.requestParameters.required> | <o.requestParameters.constraints> | <o.requestParameters.parameterType> | <o.requestParameters.availableOptions> | <o.requestParameters.isArray> | <o.requestParameters.presentationType> | <o.requestParameters.repeatableGroupId> | <o.requestParameters.repeatableGroupName> | <o.requestParameters.placementType> | <o.requestParameters.destinationPath> | <o.responseSample.statusCode> | <o.responseSample.properties> |

    Examples:
      | i.endpointID                     | o.id                             | o.errorMessage     | o.responseCode | o.operationName | o.requestParameters.id           | o.requestParameters.label | o.requestParameters.placeholder | o.requestParameters.default | o.requestParameters.required | o.requestParameters.constraints | o.requestParameters.parameterType | o.requestParameters.availableOptions | o.requestParameters.isArray | o.requestParameters.presentationType | o.requestParameters.repeatableGroupId | o.requestParameters.repeatableGroupName | o.requestParameters.placementType | o.requestParameters.destinationPath | o.responseSample.statusCode | o.responseSample.properties |
      | 0184f82664fb33c510da20cc404772ce | 0184f82664fb33c510da20cc404772ce |                    | 200            | Get customer    | 0184f82664fb87852199fc962680a301 | User ID                   | User ID                         |                             | true                         |                                 | NUMBER                            |                                      | false                       | NUMBER                               |                                       |                                         | PATH                              | user_id                             | 200                         |                             |
      | 0184f82664fb33c510da20cIncorrect |                                  | Endpoint not found | 404            |                 |                                  |                           |                                 |                             |                              |                                 |                                   |                                      |                             |                                      |                                       |                                         |                                   |                                     |                             |                             |
      #Bug logged:CCH-699|                                  |0184f82664fb33c510da20cc404772ce|Endpoint not found     |404                  |Get customer         |0184f82664fb87852199fc962680a301|User ID                      |User ID                           |                              |  true                      |                               |NUMBER                           |                                    |false                          |NUMBER                                 |                                       |                                       |PATH                             |user_id                             |200                          |                           |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-684")
  Scenario: CH :: Internal API : Verify "response sample" from GET /internal/endpoints/{endpoint}
    Given Validate specific endpoint detail for Provider via Internal Api
      | o.sourceRef                         | o.label                        | o.type  | o.isArray | i.endpointID                     | o.responseCode | o.statusCode |
      | user/updated_at                     | Updated At                     | STRING  | false     | 0184f82664fb33c510da20cc404772ce | 200            | 200          |
      | user/verified                       | Verified?                      | BOOLEAN | false     |                                  |                |              |
      | user/url                            | URL                            | STRING  | false     |                                  |                |              |
      | user/two_factor_auth_enabled        | Is Two Factor Auth Enabled?    | BOOLEAN | false     |                                  |                |              |
      | user/time_zone                      | Time Zone                      | STRING  | false     |                                  |                |              |
      | user/ticket_restriction             | Ticket Restriction             | STRING  | false     |                                  |                |              |
      | user/tags                           | Tags                           | STRING  | true      |                                  |                |              |
      | user/suspended                      | Suspended                      | BOOLEAN | false     |                                  |                |              |
      | user/signature                      | Signature                      | STRING  | false     |                                  |                |              |
      | user/shared_phone_number            | Is Shared Phone Number?        | BOOLEAN | false     |                                  |                |              |
      | user/shared_agent                   | Is Shared Agent?               | BOOLEAN | false     |                                  |                |              |
      | user/shared                         | Is Shared?                     | BOOLEAN | false     |                                  |                |              |
      | user/role_type                      | Role Type                      | NUMBER  | false     |                                  |                |              |
      | user/role                           | Role                           | STRING  | false     |                                  |                |              |
      | user/restricted_agent               | Restricted Agent?              | BOOLEAN | false     |                                  |                |              |
      | user/report_csv                     | Is Report CSV?                 | BOOLEAN | false     |                                  |                |              |
      | user/remote_photo_url               | Remote Photo URL               | STRING  | false     |                                  |                |              |
      | user/photo/$thumbnails/size         | Thumbnail Size                 | NUMBER  | false     |                                  |                |              |
      | user/photo/$thumbnails/content_type | Thumbnail Content Type         | STRING  | false     |                                  |                |              |
      | user/photo/$thumbnails/content_url  | Thumbnail Content URL          | STRING  | false     |                                  |                |              |
      | user/photo/$thumbnails/name         | Thumbnail Name                 | STRING  | false     |                                  |                |              |
      | user/photo/$thumbnails/id           | Thumbnail ID                   | NUMBER  | false     |                                  |                |              |
      | user/photo/width                    | Photo Width                    | STRING  | false     |                                  |                |              |
      | user/photo/url                      | Photo URL                      | STRING  | false     |                                  |                |              |
      | user/photo/size                     | Photo Size                     | NUMBER  | false     |                                  |                |              |
      | user/photo/mapped_content_url       | Photo Mapped Content URL       | STRING  | false     |                                  |                |              |
      | user/photo/malware_scan_result      | Photo Malware Scan Result      | STRING  | false     |                                  |                |              |
      | user/photo/malware_access_override  | Photo Malware Access Override? | BOOLEAN | false     |                                  |                |              |
      | user/photo/inline                   | Photo Inline?                  | BOOLEAN | false     |                                  |                |              |
      | user/photo/id                       | Photo ID                       | NUMBER  | false     |                                  |                |              |
      | user/photo/height                   | Photo Height                   | STRING  | false     |                                  |                |              |
      | user/photo/file_name                | Photo File Name                | STRING  | false     |                                  |                |              |
      | user/photo/deleted                  | Photo Deleted?                 | BOOLEAN | false     |                                  |                |              |
      | user/photo/content_url              | Photo Content URL              | STRING  | false     |                                  |                |              |
      | user/photo/content_type             | Photo Content Type             | STRING  | false     |                                  |                |              |
      | user/phone                          | Phone                          | STRING  | false     |                                  |                |              |
      | user/organization_id                | Organization ID                | NUMBER  | false     |                                  |                |              |
      | user/only_private_comments          | Only Private Comments?         | BOOLEAN | false     |                                  |                |              |
      | user/notes                          | Notes                          | STRING  | false     |                                  |                |              |
      | user/name                           | Name                           | STRING  | false     |                                  |                |              |
      | user/moderator                      | Moderator                      | BOOLEAN | false     |                                  |                |              |
      | user/locale_id                      | Locale ID                      | NUMBER  | false     |                                  |                |              |
      | user/locale                         | Locale                         | STRING  | false     |                                  |                |              |
      | user/last_login_at                  | Last Login At                  | STRING  | false     |                                  |                |              |
      | user/id                             | ID                             | NUMBER  | false     |                                  |                |              |
      | user/iana_time_zone                 | IANA Time Zone                 | STRING  | false     |                                  |                |              |
      | user/external_id                    | External ID                    | STRING  | false     |                                  |                |              |
      | user/email                          | Email                          | STRING  | false     |                                  |                |              |
      | user/details                        | Details                        | STRING  | false     |                                  |                |              |
      | user/default_group_id               | Default Group ID               | NUMBER  | false     |                                  |                |              |
      | user/custom_role_id                 | Custom Role ID                 | NUMBER  | false     |                                  |                |              |
      | user/created_at                     | Created At                     | STRING  | false     |                                  |                |              |
      | user/chat_only                      | Chat Only?                     | BOOLEAN | false     |                                  |                |              |
      | user/alias                          | Alias                          | STRING  | false     |                                  |                |              |
      | user/active                         | Active?                        | BOOLEAN | false     |                                  |                |              |