Feature: Configuration API

  @TestCaseId("https://jira.clickatell.com/browse/CCH-567")
  Scenario Outline: CH :: Public :: Configurations API : Activate configuration
    Given User is able to activate configuration for a provider
      | i.name               | <i.name>               |
      | i.clientSecret       | <i.clientSecret>       |
      | i.clientId           | <i.clientId>           |
      | i.host               | <i.host>               |
      | i.providerId         | <i.providerId>         |
      | i.type               | <i.type>               |
      | o.responseCode       | <o.responseCode>       |
      | o.errorMessage       | <o.errorMessage>       |
      | o.type               | <o.type>               |
      | o.setupName          | <o.setupName>          |
      | o.authenticationLink | <o.authenticationLink> |
      | o.timeToExpire       | <o.timeToExpire>       |

    Examples:
      | i.name                     | i.clientSecret                                                   | i.clientId | i.host                                 | i.providerId                     | i.type     | o.responseCode | o.errorMessage | o.type     | o.setupName                | o.authenticationLink | o.timeToExpire |
      | CH_Test_AutoTester2023-150 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 200            |                | PRODUCTION | CH_Test_AutoTester2023-151 | 200                  | 300            |
      | CH_Test_AutoTester2023-151 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                | SANDBOX    | CH_Test_AutoTester2023-152 | 200                  | 300            |
#  #Defect CCH-649      | CH_Test_AutoTester2023-42 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                      |  SANDBOX |  CH_Test_AutoTester2023-42       | 200               | 300            |
      | CH_Test_AutoTester2023-152 | WrongInput                                                       | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                | SANDBOX    | CH_Test_AutoTester2023-153 | 200                  | 300            |
      | CH_Test_AutoTester2023-153 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | WrongInput | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                | SANDBOX    | CH_Test_AutoTester2023-154 | 200                  | 300            |
      | CH_Test_AutoTester2023-154 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https:/wrongInput.zendesk.com          | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                | SANDBOX    | CH_Test_AutoTester2023-155 | 200                  | 300            |
#   #DefectID: CCH-650    | CH_Test_AutoTester2023-51 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | WrongInput   | SANDBOX | 404            | Provider not found |        |             |                      |                |
      | CH_Test_AutoTester2023-155 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | WrongInput | 404            | Bad request    |            |                            |                      |                |
      |                            | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | Bad request    |            |                            |                      |                |
#  #DefectID: CCH-651    | CH_Test_AutoTester2023-52 |                                                              | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | ClientId and ClientSecret in request or in OAuthDetails should not be null |                   |          |                                   |               |
#  #DefectID: CCH-651    | CH_Test_AutoTester2023-52 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 |        | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | ClientId and ClientSecret in request or in OAuthDetails should not be null |                   |          |                                   |               |
#  #DefectID: CCH-651    | CH_Test_AutoTester2023-52 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  |                                    | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | ClientId and ClientSecret in request or in OAuthDetails should not be null |                   |          |                                   |               |
      | CH_Test_AutoTester2023-156 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com |                                  | PRODUCTION | 400            | Bad request    |            |                            |                      |                |
      | CH_Test_AutoTester2023-157 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 |            | 400            | Bad request    |            |                            |                      |                |

  @TestCaseId("https://jira.clickatell.com/browse/CCH-561")
  Scenario Outline: CCH :: Public :: Configurations API: Get configuration secrets API should return the clientSecret in astericks.
    Given User is able to get configuration secrects in astericks
      | i.configurationId                | <i.configurationId>                |
      | o.responseCode                   | <o.responseCode>                   |
      | o.Error Description              | <o.Error Description>              |
      | o.id                             | <o.id>                             |
      | o.providerId                     | <o.providerId>                     |
      | o.accountProviderConfigStatusId  | <o.accountProviderConfigStatusId>  |
      | o.configurationEnvironmentTypeId | <o.configurationEnvironmentTypeId> |
      | o.displayName                    | <o.displayName>                    |
      | o.clientId                       | <o.clientId>                       |
      | o.clientSecret                   | <o.clientSecret>                   |
      | o.hostUrl                        | <o.hostUrl>                        |

    Examples:
      | i.configurationId                | o.responseCode | o.Error Description | o.id                             | o.providerId                     | o.accountProviderConfigStatusId | o.configurationEnvironmentTypeId | o.displayName              | o.clientId | o.clientSecret          | o.hostUrl                              |
      | 0185a771e2d64aadd296aedbc0ef2492 | 200            |                     | 0185a771e2d64aadd296aedbc0ef2492 | 0184f820c06ec8b62dfa0610e29ab575 | ACTIVE                          | PRODUCTION                       | CH_Test_AutoTester2023-97  | testoauth  | 6b38***************6bf5 | https://d3v-clickatell2162.zendesk.com |
      | 0185bbb0bd4606f5bd390e857d2c8aca | 200            |                     | 0185bbb0bd4606f5bd390e857d2c8aca | 0184f820c06ec8b62dfa0610e29ab575 | ACTIVE                          | SANDBOX                          | CH_Test_AutoTester2023-115 | testoauth  | 6b38***************6bf5 | https://d3v-clickatell2162.zendesk.com |
      | 0185bbb287a0e71d4c06bfb4de2a910c | 200            |                     | 0185bbb287a0e71d4c06bfb4de2a910c | 0184f820c06ec8b62dfa0610e29ab575 | AUTH_PENDING                    | SANDBOX                          | CH_Test_AutoTester2023-116 | testoauth  | 6b38***************6bf5 | https://d3v-clickatell2162.zendesk.com |
      | 0185bbb47862d93e7c9f2bf20512a19c | 200            |                     | 0185bbb47862d93e7c9f2bf20512a19c | 0184f820c06ec8b62dfa0610e29ab575 | ACTIVE                          | PRODUCTION                       | CH_Test_AutoTester2023-117 | testoauth  | 6b38***************6bf5 | https://d3v-clickatell2162.zendesk.com |
  # Bug to be reported    | Wrong Input                      | 404                                | Configuration ID not found |                                  |                                  |                                 |                                  |                            |            |                         | https://d3v-clickatell2162.zendesk.com |



  @TestCaseId("https://jira.clickatell.com/browse/CCH-662")
  Scenario Outline: CCH :: Public :: Configurations API: Get configuration state API should return the state of the configuration.
    Given User is able to get configuration state
      | i.configurationId               | <i.configurationId>               |
      | o.responseCode                  | <o.responseCode>                  |
      | o.errorMessage                  | <o.errorMessage>                  |
      | o.accountProviderConfigStatusId | <o.accountProviderConfigStatusId> |

    Examples:
      | i.configurationId                | o.responseCode | o.errorMessage             | o.accountProviderConfigStatusId |
      | 0185bb6c699de39640ff6012e6e07548 | 200            |                            | ACTIVE                          |
      | 0185bb938e4a2c59f3aa9d6e5d588346 | 200            |                            | AUTH_PENDING                    |
      | Wrong ID                         | 404            | Configuration ID not found |                                 |
  #BUG# TO BE ADDED    |                              | 404            | bad reuqest                                             |                               |

  @TestCaseId("https://jira.clickatell.com/browse/CCH-668")
  Scenario Outline: CH :: Public :: Configurations API : Delete configuration
    Given User is able to delete configurations
      | i.configurationId | <i.configurationId> |
      | o.responseCode    | <o.responseCode>    |
      | o.errorMessage    | <o.errorMessage>    |

    Examples:
    # For the first case, consider the following;
        # Always give the configuration ID which is not DISABLED
      | i.configurationId                | o.responseCode | o.errorMessage                                      |
# BugID: ID to be added      |TRUE|200|Configuration Deleted|
#Data to be created      |TRUE|412|Precondition failed - Configuration is not disabled|
#Data to be created      |TRUE|412|Precondition failed - Configuration is not disabled|
      | 0185ce88f2202c018711a8ac5278012c | 412            | Precondition failed - Configuration is not disabled |
      | 0185a771e2d64aadd296aedbc0ef2492 | 412            | Precondition failed - Configuration is not disabled |
#BugID: To be added from first example      |TRUE|404|Configuration ID not found|
      | Wrong ConfigID                   | 404            | Configuration ID not found                          |