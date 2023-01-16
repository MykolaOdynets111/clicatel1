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
      | i.name                    | i.clientSecret                                                   | i.clientId | i.host                                 | i.providerId                     | i.type     | o.responseCode | o.errorMessage | o.type     | o.setupName               | o.authenticationLink | o.timeToExpire |
      | CH_Test_AutoTester2023-83 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 200            |                | PRODUCTION | CH_Test_AutoTester2023-83 | 200                  | 300            |
      | CH_Test_AutoTester2023-79 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                | SANDBOX    | CH_Test_AutoTester2023-79 | 200                  | 300            |
#  #Defect CCH-649      | CH_Test_AutoTester2023-42 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                      |  SANDBOX |  CH_Test_AutoTester2023-42       | 200               | 300            |
      | CH_Test_AutoTester2023-80 | WrongInput                                                       | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                | SANDBOX    | CH_Test_AutoTester2023-80 | 200                  | 300            |
      | CH_Test_AutoTester2023-81 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | WrongInput | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                | SANDBOX    | CH_Test_AutoTester2023-81 | 200                  | 300            |
      | CH_Test_AutoTester2023-82 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https:/wrongInput.zendesk.com          | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                | SANDBOX    | CH_Test_AutoTester2023-82 | 200                  | 300            |
#   #DefectID: CCH-650    | CH_Test_AutoTester2023-51 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | WrongInput   | SANDBOX | 404            | Provider not found |        |             |                      |                |
      | CH_Test_AutoTester2023-52 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | WrongInput | 404            | Bad request    |            |                           |                      |                |
      |                           | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | Bad request    |            |                           |                      |                |
#  #DefectID: CCH-651    | CH_Test_AutoTester2023-52 |                                                              | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | ClientId and ClientSecret in request or in OAuthDetails should not be null |                   |          |                                   |               |
#  #DefectID: CCH-651    | CH_Test_AutoTester2023-52 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 |        | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | ClientId and ClientSecret in request or in OAuthDetails should not be null |                   |          |                                   |               |
#  #DefectID: CCH-651    | CH_Test_AutoTester2023-52 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  |                                    | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | ClientId and ClientSecret in request or in OAuthDetails should not be null |                   |          |                                   |               |
      | CH_Test_AutoTester2023-52 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com |                                  | PRODUCTION | 400            | Bad request    |            |                           |                      |                |
      | CH_Test_AutoTester2023-53 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 |            | 400            | Bad request    |            |                           |                      |                |



  @TestCaseId("https://jira.clickatell.com/browse/CCH-662")
  Scenario Outline: CCH :: Public :: Configurations API: Get configuration state API should return the state of the configuration.
    Given User is able to get configuration state
      | i.configurationId        | <i.configurationId>        |
      | o.responseCode           | <o.responseCode>           |
      | o.errorMessage           | <o.errorMessage>           |
      | o.accountProviderConfigStatusId | <o.accountProviderConfigStatusId> |

    Examples:
      | i.configurationId                | o.responseCode | o.errorMessage                                          | o.accountProviderConfigStatusId |
      | 0185bb6c699de39640ff6012e6e07548 | 200            |                                                         | ACTIVE                        |
      | 0185bb938e4a2c59f3aa9d6e5d588346 | 200            |                                                         | AUTH_PENDING                  |
      | Wrong ID                         | 404            | Configuration ID not found                              |                               |
  #BUG# TO BE ADDED    |                              | 404            | bad reuqest                                             |                               |