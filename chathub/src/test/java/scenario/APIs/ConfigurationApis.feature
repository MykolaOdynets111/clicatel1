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


  @TestCaseId("https://jira.clickatell.com/browse/CCH-549")
  Scenario Outline: CCH :: Public :: Configurations API: Get all configurations should return configurations with all the status except AUTH_PENDING
    Given User is able to get all configurations for a provider
      |i.providerId|    <i.providerId		>	|
      |i.version|       <i.version		>	|
      |o.responseCode|  <o.responseCode	>	|
      |o.errorMessage|  <o.errorMessage	>	|
      |o.id|            <o.id				>	|
      |o.providerId|    <o.providerId		>	|
      |o.type|          <o.type			>	|
      |o.name|          <o.name			>	|
      |o.status|        <o.status			>	|
      |o.host|          <o.host			>	|

      Examples:
      |i.providerId|i.version|o.responsecode|o.Error Description|o.id|o.providerId|o.type|o.name|o.status|o.host|
      |0184f820c06ec8b62dfa0610e29ab575||200||0185a771e2d64aadd296aedbc0ef2492|0184f820c06ec8b62dfa0610e29ab575|PRODUCTION|CH_Test_AutoTester2023-97|ACTIVE|https://d3v-clickatell2162.zendesk.com|
      |||||0185bb6c699de39640ff6012e6e07548|0184f820c06ec8b62dfa0610e29ab575|PRODUCTION|CH_Test_AutoTester2023-112|ACTIVE|https://d3v-clickatell2162.zendesk.com|
      |||||0185bbb0bd4606f5bd390e857d2c8aca|0184f820c06ec8b62dfa0610e29ab575|SANDBOX|CH_Test_AutoTester2023-115|ACTIVE|https://d3v-clickatell2162.zendesk.com|
      |||||0185bbb47862d93e7c9f2bf20512a19c|0184f820c06ec8b62dfa0610e29ab575|PRODUCTION|CH_Test_AutoTester2023-117|ACTIVE|https://d3v-clickatell2162.zendesk.com|
      |WrongId||404|Provider ID or Version ID not found for Provider|||||||
      |0184f820c06ec8b62dfa0610e29ab575|Wrong|404|Provider ID or Version ID not found for Provider|||||||
      |NULL||404|bad reuqest|||||||
      |0184f820c06ec8b62dfa0610e29ab575|NULL|404|bad reuqest|||||||