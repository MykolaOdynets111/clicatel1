Feature: Endpoint API

  @TestCaseId("https://jira.clickatell.com/browse/CCH-622")
  Scenario Outline: CH :: Admin API : GET /admin/endpoints should return all available endpoints for a provider
    Given User is able to get all endpoint detail for Provider
      | i.providerID   | <i.providerID>   |
      |i.versionID     | <i.versionID>    |
      | o.errorMessage | <o.errorMessage> |
      | o.responseCode | <o.responseCode> |

    Examples:
      | i.providerID                     | i.versionID|o.responseCode|o.errorMessage                             |
      | 0184f828214f6b7a03c711284b2b8e39 | v1.0.0     |    200       |                                           |
      | 0184f828214f6b7a03c711284b2b8e39 | v1         |    404       |Provided or specification version not found|
      | 0184f828214f6b7a                 | v1         |    404       |Provided or specification version not found|
      | 0184f828214f6b7a                 | v1         |    404       |Provided or specification version not found|
      |                                  | v1         |    404       |Provided or specification version not found|
      | 0184f828214f6b7a03c711284b2b8e39 |            |    404       |Provided or specification version not found|

  @TestCaseId("https://jira.clickatell.com/browse/CCH-676")
  Scenario: CH :: Admin API : User should be able to verify all returned endpoints for a provider
    Given User is able to verify all available endpoints for provider
      |        o.id                    |     o.name                      |
      |0184f82f654d988ff09d6d08e8499c3a|Create lead                      |
      |0184f82f654de2b793510630141e9990|Search customer                  |
      |0184f82f654e445ba7e2e46ddb638085|Get TAGS                         |
      |0184f82f654eb488be883c9412a2e22c|Get User Custom Fields - LEADS   |
      |0184f82f654ec9cdb6eed670afdd7e36|Retrieve account details         |
      |0184f82f654ef40f88d3929be7ee05c7|Get customer                     |
      |0184f82f654ef7950075689898286775|Update lead                      |

  @TestCaseId("https://jira.clickatell.com/browse/CCH-626")
  Scenario Outline: CH :: Internal API : GET /internal/endpoints should return available endpoints for a provider.
    Given User is able to get all endpoint detail for Provider via Internal API
      | i.providerID   | <i.providerID>   |
      |i.versionID     | <i.versionID>    |
      | o.errorMessage | <o.errorMessage> |
      | o.responseCode | <o.responseCode> |

    Examples:
      | i.providerID                     | i.versionID|o.responseCode|o.errorMessage                             |
      | 0184f828214f6b7a03c711284b2b8e39 | v1.0.0     |    200       |                                           |
      | 0184f828214f6b7a03c711284b2b8e39 | v1         |    404       |Provided or specification version not found|
      | 0184f828214f6b7a                 | v1         |    404       |Provided or specification version not found|
      | 0184f828214f6b7a                 | v1         |    404       |Provided or specification version not found|
      |                                  | v1         |    404       |Provided or specification version not found|
      | 0184f828214f6b7a03c711284b2b8e39 |            |    404       |Provided or specification version not found|

  @TestCaseId("https://jira.clickatell.com/browse/CCH-683")
  Scenario: CH :: Internal API : User should be able to verify all returned endpoints for a provider
    Given User is able to verify all available endpoints for provider via Internal API
      |        o.id                    |     o.name                    |
      |0184f82f654d988ff09d6d08e8499c3a| Create lead                   |
      |0184f82f654de2b793510630141e9990| Search customer               |
      |0184f82f654e445ba7e2e46ddb638085|Get TAGS                       |
      |0184f82f654eb488be883c9412a2e22c|Get User Custom Fields - LEADS |
      |0184f82f654ec9cdb6eed670afdd7e36|Retrieve account details        |
      |0184f82f654ef40f88d3929be7ee05c7|Get customer                    |
      |0184f82f654ef7950075689898286775|Update lead                     |

  @TestCaseId("https://jira.clickatell.com/browse/CCH-630")
  Scenario Outline: CH :: Admin API : CH :: Admin API : GET /admin/specifications should return all specifications for a provider
    Given User is able to get all specifications for a provider
      | i.providerID                                  | <i.providerID>   |
      | o.id                                          | <o.id>    |
      | o.errorMessage                                | <o.errorMessage> |
      | o.responseCode                                | <o.responseCode> |
      | o.authDetails.grantType                       | <o.authDetails.grantType> |
      | o.authDetails.authPath                        | <o.authDetails.authPath> |
      | o.authDetails.refreshPath                     | <o.authDetails.refreshPath>         |
      | o.authDetails.tokenPath                       | <o.authDetails.tokenPath>       |
      | o.authDetails.tokenExpirationDurationSeconds  | <o.authDetails.tokenExpirationDurationSeconds>         |
      |  o.authDetails.scopes                         | <o.authDetails.scopes>       |
      |  o.authDetails.authorizationHeaderValuePrefix | <o.authDetails.authorizationHeaderValuePrefix>                            |
      |  o.authDetails.authType                       | <o.authDetails.authType>         |
      |  o.version                                    | <o.version>       |
      |  o.openApiSpecS3Key                           | <o.openApiSpecS3Key>         |


    Examples:
      | i.providerID                     | o.responseCode|o.errorMessage                 |o.id                            |o.authDetails.grantType  |o.authDetails.authPath           | o.authDetails.refreshPath   |o.authDetails.tokenPath              |o.authDetails.tokenExpirationDurationSeconds   | o.authDetails.scopes              | o.authDetails.authorizationHeaderValuePrefix|o.authDetails.authType   | o.version               |o.openApiSpecS3Key                        |
      |0184f828214f6b7a03c711284b2b8e39  |200            |                               |0184f82f61a2613547072daf82115294| AUTHORIZATION_CODE     |/oauth2/authorize                |/oauth2/token                |/oauth2/token                        |  300                                          |  read+write+profile               |                                             | OAUTH                   |    v1.0.0            |  /0184f828214f6b7a03c711284b2b8e39/v1.0.0   |
      |0184f828214f6b7a03c711284b2false  |404            | Specifications not found      |                                |                        |                                 |                             |                                     |                                               |                                   |                                             |                         |                      |                                             |
      |                                  |404            | Specifications not found      |                                |                        |                                 |                             |                                     |                                               |                                   |                                             |                         |                      |                                             |

  @TestCaseId("https://jira.clickatell.com/browse/CCH-623")
  Scenario Outline: CH :: Admin API : GET /admin/endpoints/{endpoint} should return available endpoint details
    Given User is able to get specific endpoint detail for Provider via Admin api
      | i.endpointID                                  | <i.endpointID>                     |
      | o.errorMessage                                | <o.errorMessage>                   |
      | o.responseCode                                | <o.responseCode>                   |
      | o.operationName                               | <o.operationName>                   |
      | o.requestParameters.id                        | <o.requestParameters.id>              |
      | o.requestParameters.label                     | <o.requestParameters.label>               |
      | o.requestParameters.placeholder               | <o.requestParameters.placeholder>         |
      | o.requestParameters.default                   | <o.requestParameters.default>             |
      |  o.requestParameters.required                 | <o.requestParameters.required>            |
      |  o.requestParameters.constraints              | <o.requestParameters.constraints>         |
      |  o.requestParameters.parameterType            | <o.requestParameters.parameterType>       |
      |  o.requestParameters.availableOptions         | <o.requestParameters.availableOptions>    |
      |  o.requestParameters.isArray                  | <o.requestParameters.isArray>             |
      |  o.requestParameters.presentationType         | <o.requestParameters.presentationType>    |
      |  o.requestParameters.repeatableGroupId        | <o.requestParameters.repeatableGroupId>   |
      |  o.requestParameters.repeatableGroupName      | <o.requestParameters.repeatableGroupName> |
      |  o.requestParameters.placementType            | <o.requestParameters.placementType>       |
      |  o.requestParameters.destinationPath          | <o.requestParameters.destinationPath>     |
      | o.responseSample.statusCode                   |<o.responseSample.statusCode>              |
      |o.responseSample.properties                    | <o.responseSample.properties>             |


    Examples:
      | i.endpointID                     |o.errorMessage         | o.responseCode      |   o.operationName   | o.requestParameters.id         | o.requestParameters.label   |  o.requestParameters.placeholder | o.requestParameters.default |o.requestParameters.required |o.requestParameters.constraints|o.requestParameters.parameterType|o.requestParameters.availableOptions|  o.requestParameters.isArray  | o.requestParameters.presentationType  | o.requestParameters.repeatableGroupId |o.requestParameters.repeatableGroupName|o.requestParameters.placementType|o.requestParameters.destinationPath | o.responseSample.statusCode |o.responseSample.properties |
      |  0184f82664fb33c510da20cc404772ce|                       |200                  |Get customer         |0184f82664fb87852199fc962680a301|User ID                      |User ID                           |                              |  true                      |                               |NUMBER                           |                                    |false                          |NUMBER                                 |                                       |                                       |PATH                             |user_id                             |200                          |                            |
      |  0184f82664fb33c510da20cIncorrect|Endpoint not found     |404                  |Get customer         |0184f82664fb87852199fc962680a301|User ID                      |User ID                           |                              |  true                      |                               |NUMBER                           |                                    |false                          |NUMBER                                 |                                       |                                       |PATH                             |user_id                             |200                          |                           |
      |                                  |Endpoint not found     |404                  |Get customer         |0184f82664fb87852199fc962680a301|User ID                      |User ID                           |                              |  true                      |                               |NUMBER                           |                                    |false                          |NUMBER                                 |                                       |                                       |PATH                             |user_id                             |200                          |                           |

  @TestCaseId("https://jira.clickatell.com/browse/CCH-627")
  Scenario Outline: CH :: Internal API : GET /internal/endpoints/{endpoint} should return available endpoint details
    Given User is able to get specific endpoint detail for Provider via Internal Api
      | i.endpointID                                  | <i.endpointID>                     |
      | o.errorMessage                                | <o.errorMessage>                   |
      | o.responseCode                                | <o.responseCode>                   |
      | o.operationName                               | <o.operationName>                   |
      | o.requestParameters.id                        | <o.requestParameters.id>              |
      | o.requestParameters.label                     | <o.requestParameters.label>               |
      | o.requestParameters.placeholder               | <o.requestParameters.placeholder>         |
      | o.requestParameters.default                   | <o.requestParameters.default>             |
      |  o.requestParameters.required                 | <o.requestParameters.required>            |
      |  o.requestParameters.constraints              | <o.requestParameters.constraints>         |
      |  o.requestParameters.parameterType            | <o.requestParameters.parameterType>       |
      |  o.requestParameters.availableOptions         | <o.requestParameters.availableOptions>    |
      |  o.requestParameters.isArray                  | <o.requestParameters.isArray>             |
      |  o.requestParameters.presentationType         | <o.requestParameters.presentationType>    |
      |  o.requestParameters.repeatableGroupId        | <o.requestParameters.repeatableGroupId>   |
      |  o.requestParameters.repeatableGroupName      | <o.requestParameters.repeatableGroupName> |
      |  o.requestParameters.placementType            | <o.requestParameters.placementType>       |
      |  o.requestParameters.destinationPath          | <o.requestParameters.destinationPath>     |
      | o.responseSample.statusCode                   |<o.responseSample.statusCode>              |
      |o.responseSample.properties                    | <o.responseSample.properties>             |


    Examples:
      | i.endpointID                     |o.errorMessage         | o.responseCode      |   o.operationName   | o.requestParameters.id         | o.requestParameters.label   |  o.requestParameters.placeholder | o.requestParameters.default |o.requestParameters.required |o.requestParameters.constraints|o.requestParameters.parameterType|o.requestParameters.availableOptions|  o.requestParameters.isArray  | o.requestParameters.presentationType  | o.requestParameters.repeatableGroupId |o.requestParameters.repeatableGroupName|o.requestParameters.placementType|o.requestParameters.destinationPath | o.responseSample.statusCode |o.responseSample.properties |
      |  0184f82664fb33c510da20cc404772ce|                       |200                  |Get customer         |0184f82664fb87852199fc962680a301|User ID                      |User ID                           |                              |  true                      |                               |NUMBER                           |                                    |false                          |NUMBER                                 |                                       |                                       |PATH                             |user_id                             |200                          |                            |
      |  0184f82664fb33c510da20cIncorrect|Endpoint not found     |404                  |Get customer         |0184f82664fb87852199fc962680a301|User ID                      |User ID                           |                              |  true                      |                               |NUMBER                           |                                    |false                          |NUMBER                                 |                                       |                                       |PATH                             |user_id                             |200                          |                           |
      |                                  |Endpoint not found     |404                  |Get customer         |0184f82664fb87852199fc962680a301|User ID                      |User ID                           |                              |  true                      |                               |NUMBER                           |                                    |false                          |NUMBER                                 |                                       |                                       |PATH                             |user_id                             |200                          |                           |





















