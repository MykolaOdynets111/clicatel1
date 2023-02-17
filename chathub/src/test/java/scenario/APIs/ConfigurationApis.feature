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

    # This API requires unique input names to execute. The solution to it is the data we will create has to be deleted at the end but currently disable API is not working because of CCH-715. Once it will start working and will be tested will add steps to disable and delete activated configurations , in that way the same data will be created and deleted.
    # Another approach was a temporary fix where we could've generated random names in the data maps but after discussions came to the conclusion that the effort is not worth the time for a temporary fix.
    # So, for now to enter unique and same values in 'i.name' and 'o.setupName' to execute this test case.
    Examples:
      | i.name                     | i.clientSecret                                                   | i.clientId | i.host                                 | i.providerId                     | i.type     | o.responseCode | o.errorMessage | o.type     | o.setupName                | o.authenticationLink | o.timeToExpire |
      | CH_Test_AutoTester2023-002 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 200            |                | PRODUCTION | CH_Test_AutoTester2023-002 | 200                  | 300            |
      | CH_Test_AutoTester2023-205 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                | SANDBOX    | CH_Test_AutoTester2023-205 | 200                  | 300            |
  #Defect CCH-649      | CH_Test_AutoTester2023-42 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                      |  SANDBOX |  CH_Test_AutoTester2023-42       | 200               | 300            |
      | CH_Test_AutoTester2023-206 | WrongInput                                                       | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                | SANDBOX    | CH_Test_AutoTester2023-206 | 200                  | 300            |
      | CH_Test_AutoTester2023-207 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | WrongInput | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                | SANDBOX    | CH_Test_AutoTester2023-207 | 200                  | 300            |
      | CH_Test_AutoTester2023-208 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https:/wrongInput.zendesk.com          | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | 200            |                | SANDBOX    | CH_Test_AutoTester2023-208 | 200                  | 300            |
   #DefectID: CCH-650    | CH_Test_AutoTester2023-51 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | WrongInput   | SANDBOX | 404            | Provider not found |        |             |                      |                |
      | CH_Test_AutoTester2023-11  | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | WrongInput | 404            | Bad request    |            |                            |                      |                |
      |                            | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | Bad request    |            |                            |                      |                |
  #DefectID: CCH-651    | CH_Test_AutoTester2023-52 |                                                              | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | ClientId and ClientSecret in request or in OAuthDetails should not be null |                   |          |                                   |               |
  #DefectID: CCH-651    | CH_Test_AutoTester2023-52 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 |        | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | ClientId and ClientSecret in request or in OAuthDetails should not be null |                   |          |                                   |               |
  #DefectID: CCH-651    | CH_Test_AutoTester2023-52 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  |                                    | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | 404            | ClientId and ClientSecret in request or in OAuthDetails should not be null |                   |          |                                   |               |
      | CH_Test_AutoTester2023-156 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com |                                  | PRODUCTION | 400            | Bad request    |            |                            |                      |                |
      | CH_Test_AutoTester2023-157 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth  | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 |            | 400            | Bad request    |            |                            |                      |                |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-561")
  Scenario Outline: CCH :: Public :: Configurations API: Get configuration secrets API should return the clientSecret in astericks.
    Given User is able to get configuration secrects in astericks
      | i.configurationId                | <i.configurationId>                |
      | o.responseCode                   | <o.responseCode>                   |
      | o.errorMessage                   | <o.errorMessage>                   |
      | o.id                             | <o.id>                             |
      | o.providerId                     | <o.providerId>                     |
      | o.accountProviderConfigStatusId  | <o.accountProviderConfigStatusId>  |
      | o.configurationEnvironmentTypeId | <o.configurationEnvironmentTypeId> |
      | o.displayName                    | <o.displayName>                    |
      | o.clientId                       | <o.clientId>                       |
      | o.clientSecret                   | <o.clientSecret>                   |
      | o.hostUrl                        | <o.hostUrl>                        |

    Examples:
      | i.configurationId                | o.responseCode | o.errorMessage             | o.id                             | o.providerId                     | o.accountProviderConfigStatusId | o.configurationEnvironmentTypeId | o.displayName                    | o.clientId   | o.clientSecret          | o.hostUrl                              |
      | 01864e7ea911860501de7719acb3f263 | 200            |                            | 01864e7ea911860501de7719acb3f263 | 0184f820c06ec8b62dfa0610e29ab575 | ACTIVE                          | PRODUCTION                       | chathub_test_AutoTester2023-1000 | testoauth    | 6b38***************6bf5 | https://d3v-clickatell2162.zendesk.com |
      | 01864e80ccadb659f4728d79e8d43f4c | 200            |                            | 01864e80ccadb659f4728d79e8d43f4c | 0184f820c06ec8b62dfa0610e29ab575 | ACTIVE                          | SANDBOX                          | chathub_test_AutoTester2023-1001 | testoauth    | 6b38***************6bf5 | https://d3v-clickatell2162.zendesk.com |
      | 01863b30120c580096c2e8948c8704f7 | 200            |                            | 01863b30120c580096c2e8948c8704f7 | 0184f820c06ec8b62dfa0610e29ab575 | AUTH_PENDING                    | PRODUCTION                       | Test Configuration               | testoauth    | 6b38***************6bf5 | https://d3v-clickatell2162.zendesk.com |
      | 0186196d45a5e20ff08a44565de66853 | 200            |                            | 0186196d45a5e20ff08a44565de66853 | 0184f820c06ec8b62dfa0610e29ab575 | AUTH_PENDING                    | SANDBOX                          | Chathub Zendesk Sell Testing     | demo_chathub | 1dcc***************c91d | https://d3v-clickatell2162.zendesk.com |
      | 018650777986567652f3d153c132974b | 200            |                            | 018650777986567652f3d153c132974b | 0184f820c06ec8b62dfa0610e29ab575 | DISABLED                        | PRODUCTION                       | chathub_test_UHTester2023-006    | testoauth    | 6b38***************6bf5 | https://d3v-clickatell2162.zendesk.com |
      | 01864e882139d697a68b5ce716dc3c70 | 200            |                            | 01864e882139d697a68b5ce716dc3c70 | 0184f820c06ec8b62dfa0610e29ab575 | DISABLED                        | SANDBOX                          | chathub_test_AutoTester2023-1004 | testoauth    | 6b38***************6bf5 | https://d3v-clickatell2162.zendesk.com |
      | Wrong Input                      | 404            | Configuration ID not found |                                  |                                  |                                 |                                  |                                  |              |                         |                                        |
#DefectId CCH-699      |                   | 400            | Bad request         |      |              |                                 |                                  |               |            |                |           |


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
  #BUG# CCH-699    |                              | 404            | bad reuqest                                             |                               |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-666")
  Scenario Outline: CCH :: Public :: Configuration API: Disable configuration API
    Given User is able to disable configurations
      | i.configurationId | <i.configurationId> |
      | o.responseCode    | <o.responseCode>    |
      | o.errorMessage    | <o.errorMessage>    |
      | o.id              | <o.id>              |
      | o.providerId      | <o.providerId>      |
      | o.type            | <o.type>            |
      | o.name            | <o.name>            |
      | o.status          | <o.status>          |
      | o.host            | <o.host>            |

    Examples:
    # For the first two cases, consider the following;
        # Always give the configuration ID which is not DISABLED
        # If the configuration ID is not in DISABLED state then make sure the configuration ID should not be in AUTHPENDING state
      | i.configurationId                | o.responseCode | o.errorMessage             | o.id                             | o.providerId                     | o.type     | o.name                     | o.status | o.host                                 |
      | 0185bbb47862d93e7c9f2bf20512a19c | 200            |                            | 0185bbb47862d93e7c9f2bf20512a19c | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | CH_Test_AutoTester2023-117 | DISABLED | https://d3v-clickatell2162.zendesk.com |
      | 0185bbb0bd4606f5bd390e857d2c8aca | 200            |                            | 0185bbb0bd4606f5bd390e857d2c8aca | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | CH_Test_AutoTester2023-115 | DISABLED | https://d3v-clickatell2162.zendesk.com |
      | 0185bbb0bd4606f5bd390e857d2c8aca | 200            |                            | 0185bbb0bd4606f5bd390e857d2c8aca | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | CH_Test_AutoTester2023-115 | DISABLED | https://d3v-clickatell2162.zendesk.com |
#ExpiringSoon - Data to be create once integrated build will be deployed |TRUE|200||[configurationId]|[providerId]|SANDBOX|[name]|DISABLED|[host]|
#Expired - Data to be create once integrated build will be deployed |TRUE|200||[configurationId]|[providerId]|SANDBOX|[name]|DISABLED|[host]|
#Disabled - BugId: CCH-711       |TRUE|500|Precondition failed|||||||
      | Wrong Input                      | 404            | Configuration ID not found |                                  |                                  |            |                            |          |                                        |
#Null - BugId: CCH-712      ||400|Bad request|||||||
#AuthPending - BugId: CCH-224      |TRUE|500|Precondition failed|||||||
#With ChatFlow and ChatDesk activated - Data to be created once integrated build will be deployed      |TRUE|500|Precondition failed|||||||


  @TestCaseId("https://jira.clickatell.com/browse/CCH-668")
  Scenario Outline: CH :: Public :: Configurations API : Delete configuration
    Given User is able to delete configurations
      | i.configurationId | <i.configurationId> |
      | o.responseCode    | <o.responseCode>    |
      | o.errorMessage    | <o.errorMessage>    |

    Examples:
      | i.configurationId                | o.responseCode | o.errorMessage                                      |
#Disabled Scenario: Configuration ID in disabled state should be given
      # BugID: CCH-715      |TRUE|200|Configuration Deleted|
#Expiring soon scenario: Data to be created when integration build will be deployed
      # |TRUE|412|Precondition failed - Configuration is not disabled|
#Expired scenario: Data to be created when integration build will be deployed
      # |TRUE|412|Precondition failed - Configuration is not disabled|
#AuthPending scenario: Input ID which is in AUTH_PENDING state
      | 0185ce88f2202c018711a8ac5278012c | 412            | Precondition failed - Configuration is not disabled |
#Activated scenario: Input ID which is in ACTIVE state
      | 0185a771e2d64aadd296aedbc0ef2492 | 412            | Precondition failed - Configuration is not disabled |
#Wrong input scenario:
      | Wrong Id                         | 404            | Configuration ID not found                          |
#Null scenario:
# BugID: CCH-699      |                                  | 400            | Bad request                                         |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-667")
  Scenario Outline: CCH :: Public :: Configuration API: Re-activate configuration API
    Given User is able to re-activate configuration for a provider
      | i.id           | <i.id>           |
      | i.providerId   | <i.providerId>   |
      | o.responseCode | <o.responseCode> |
      | o.errorMessage | <o.errorMessage> |
      | o.id           | <o.id>           |
      | o.type         | <o.type>         |
      | o.setupName    | <o.setupName>    |
      | o.timeToExpire | <o.timeToExpire> |
# Note: If test fails make sure that the data is not updated i.e. id or outputs.
    Examples:
      | i.id                             | i.providerId                     | o.responseCode | o.errorMessage             | o.id                             | o.type     | o.setupName                      | o.timeToExpire |
      | 01864e80ccadb659f4728d79e8d43f4c | 0184f820c06ec8b62dfa0610e29ab575 | 200            |                            | 01864e80ccadb659f4728d79e8d43f4c | SANDBOX    | chathub_test_AutoTester2023-1001 | 300            |
      | 01864e7ea911860501de7719acb3f263 | 0184f820c06ec8b62dfa0610e29ab575 | 200            |                            | 01864e7ea911860501de7719acb3f263 | PRODUCTION | chathub_test_AutoTester2023-1000 | 300            |
      | WrongId                          | 0184f820c06ec8b62dfa0610e29ab575 | 404            | Configuration ID not found |                                  |            |                                  |                |
#DefectId: CCH-716      |0185ceaddc7af760ebfb1e3cbfbe0e0d|WrongId|404|ProviderId not found|||||
      | 01864e862367dd01e9f47c3aff28a835 | 0184f820c06ec8b62dfa0610e29ab575 | 200            |                            | 01864e862367dd01e9f47c3aff28a835 | SANDBOX    | chathub_test_AutoTester2023-1003 | 300            |
      | 01864e80ccadb659f4728d79e8d43f4c | 0184f820c06ec8b62dfa0610e29ab575 | 200            |                            | 01864e80ccadb659f4728d79e8d43f4c | SANDBOX    | chathub_test_AutoTester2023-1001 | 300            |
      | 01864e8313f67d4fe7134bcfd6642c83 | 0184f820c06ec8b62dfa0610e29ab575 | 200            |                            | 01864e8313f67d4fe7134bcfd6642c83 | SANDBOX    | chathub_test_AutoTester2023-1002 | 300            |
#Unable to create data due to defectId: CCH-715 |0185ceb1a9d7b89b3d45f0fd6d0081123|0184f820c06ec8b62dfa0610e29ab575|404|Configuration ID not found|||||
      |                                  | 0184f820c06ec8b62dfa0610e29ab575 | 400            | Bad request                |                                  |            |                                  |                |
      | TRUE                             |                                  | 400            | Bad request                |                                  |            |                                  |                |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-549")
  Scenario: CCH :: Public :: Configurations API: Get all configurations should return configurations with all the status except AUTH_PENDING (200 response coverage in the test case)
    Given User is able to get all configurations for a provider - Public
      | i.providerId                     | i.version | o.responseCode | o.id                             | o.providerId                     | o.type     | o.name                | o.status | o.host                            | o.createdDate            | o.modifiedDate           |
      | 0185172bf6b57e9831c6d6616bc68317 |           | 200            | 01861351de1a5234dd705346088f724c | 0185172bf6b57e9831c6d6616bc68317 | PRODUCTION | Shopify_Demo_Testing3 | ACTIVE   | https://cyan-bottle.myshopify.com | 2023-02-02T18:09:38.218Z | 2023-02-06T17:36:35.905Z |
      |                                  |           |                | 0186139cd724b9f391e014bff9486c2d | 0185172bf6b57e9831c6d6616bc68317 | PRODUCTION | Shopify_Demo_Testing9 | ACTIVE   | https://cyan-bottle.myshopify.com | 2023-02-02T19:31:31.239Z | 2023-02-02T19:32:11.145Z |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-664")
  Scenario Outline: CCH :: Public :: Configurations API: Get all configurations should return configurations with all the status except AUTH_PENDING (other than 200 response coverage in the test case)
    Given User is able to get all configurations for a provider - Public
      | i.providerId   | i.version   | o.responseCode   | o.errorMessage   |
      | <i.providerId> | <i.version> | <o.responseCode> | <o.errorMessage> |
    Examples:
      | i.providerId | i.version | o.responseCode | o.errorMessage                                   |
      | WrongId      |           | 404            | Provider ID or Version ID not found for Provider |
# Will be implemented after LR     | 0184f820c06ec8b62dfa0610e29ab575 | Wrong     | 404  | Provider ID or Version ID not found for Provider |
# BugId: CCH-698  | NULL         |           | 404            | bad reuqest                                      |
 # Will be implemented after LR    | 0184f820c06ec8b62dfa0610e29ab575 | NULL      | 404  | bad reuqest                                      |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-571")
  Scenario: CCH :: Internal :: Configurations API: Get all configurations should return configurations with all the status (200 response coverage in the test case)
    Given User is able to get all configurations for a provider - Internal
      | i.providerId                     | i.version | o.responseCode | o.errorMessage | o.id                             | o.displayName                    | o.configurationEnvironmentTypeId | o.accountProviderConfigStatusId |
      | 0184f820c06ec8b62dfa0610e29ab575 |           | 200            |                | 01860e79275cec65d8eb4e734c1dd170 | mehdi and amrit test 2           | PRODUCTION                       | ACTIVE                          |
      |                                  |           |                |                | 0186196f64a30e428c8fcada2788031a | Chathub Zendesk Support Testing1 | SANDBOX                          | ACTIVE                          |
      |                                  |           |                |                | 01864c5b65e25804507824869221d86c | test_ga_3                        | SANDBOX                          | ACTIVE                          |
      |                                  |           |                |                | 01864ca331f6d6b61dbe38739fc2bb59 | accountTest                      | PRODUCTION                       | ACTIVE                          |
      |                                  |           |                |                | 01864ca6b38368317bc58735eeac8213 | Chathub 045 671                  | SANDBOX                          | ACTIVE                          |
      |                                  |           |                |                | 01864e7ea911860501de7719acb3f263 | chathub_test_AutoTester2023-1000 | PRODUCTION                       | ACTIVE                          |
      |                                  |           |                |                | 01864e80ccadb659f4728d79e8d43f4c | chathub_test_AutoTester2023-1001 | SANDBOX                          | ACTIVE                          |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-685")
  Scenario Outline: CCH :: Internal :: Configurations API: Get all configurations should return configurations with all the status (other than 200 response coverage in the test case)
    Given User is able to get all configurations for a provider - Internal
      | i.providerId   | i.version   | o.responseCode   | o.errorMessage   |
      | <i.providerId> | <i.version> | <o.responseCode> | <o.errorMessage> |

    Examples:
      | i.providerId | i.version | o.responseCode | o.errorMessage                                   |
      | WrongId      |           | 404            | Provider ID or Version ID not found for Provider |
# Will be implemented after LR     | 0184f820c06ec8b62dfa0610e29ab575 | Wrong     | 404  | Provider ID or Version ID not found for Provider |
# BugID: CCH-700     |         |           | 404            | Bad reuqest                                      |
 # Will be implemented after LR    | 0184f820c06ec8b62dfa0610e29ab575 | NULL      | 404  | bad reuqest                                      |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-693")
  Scenario Outline: CH :: Admin API : GET - /admin/configurations should return all available configurations for a provider
    Given User is able to get all configurations for a provider via Admin API
      | i.providerId   | i.version   | i.mc2AccountId   | o.responseCode   | o.errorMessage   |
      | <i.providerId> | <i.version> | <i.mc2AccountId> | <o.responseCode> | <o.errorMessage> |

    Examples:
      | i.providerId                     | i.version | i.mc2AccountId                   | o.responseCode | o.errorMessage                                                               |
      | 0184f828214f6b7a03c711WRONG      | v1.0.0    | bb0496c20c434a76a927e7419075fcc3 | 404            | Provider ID or Version ID not found for Provider 0184f828214f6b7a03c711WRONG |
      | 0184f828214f6b7a03c711284b2b8e39 | v1        | bb0496c20c434a76a927e7419075fcc3 | 404            | Provider ID or Version ID not found for Provider                             |
      #Null ProviderID Bug ID : CCH-700|                                 |v1.0.0     |bb0496c20c434a76a927e7419075fcc3  |  400            | Bad request                                    |
      #Null version    Bug ID : CCH-700| 0184f828214f6b7a03c711284b2b8e39|           |bb0496c20c434a76a927e7419075fcc3  |  400           | Bad request                                      |
      #Incorrect MC2ID Bug ID : CCH-697 |0184f828214f6b7a03c711284b2b8e39 |v1.0.0     |bb0496c20c434a76a927e7419wrong    |  404            | Provider ID or Version ID not found for Provider |
      #Null MC2ID      Bug ID : CCH-697 |0184f828214f6b7a03c711284b2b8e39 |v1.0.0     |                                  |  400            | Bad request                                      |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-694")
  Scenario: CH :: Admin API : GET - /admin/configurations: User is able to verify all returned configurations for a provider
    Given User is able to get all configurations for a provider via Admin API
      | i.providerId                     | i.version | i.mc2AccountId                   | o.responseCode | o.id                             | o.providerId                     | o.type     | o.name                      | o.status     | o.host                  | o.createdDate            | o.modifiedDate           |
      | 0184f828214f6b7a03c711284b2b8e39 | v1.0.0    | bb0496c20c434a76a927e7419075fcc3 | 200            | 0185e57d00ccb0e68298c0a8e5b45a73 | 0184f828214f6b7a03c711284b2b8e39 | PRODUCTION | Chatflow Zendesk Sell PROD1 | DISABLED     | https://api.getbase.com | 2023-01-24T20:34:12.815Z | 2023-01-24T23:19:02.189Z |
      |                                  |           |                                  |                | 0185e5937f2c47f215ffc6dad87142f0 | 0184f828214f6b7a03c711284b2b8e39 | PRODUCTION | Chatflow Zendesk Sell PROD2 | EXPIRED      | https://api.getbase.com | 2023-01-24T20:58:46.959Z | 2023-01-24T21:00:04.680Z |
      |                                  |           |                                  |                | 0185e59ab160a98da89afd18b2775c9b | 0184f828214f6b7a03c711284b2b8e39 | PRODUCTION | Chatflow Zendesk Sell PROD5 | AUTH_PENDING | https://api.getbase.com | 2023-01-24T21:06:38.562Z | 2023-01-27T21:20:22.024Z |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-695")
  Scenario Outline: CH :: Admin API : GET - /admin/configurations : User is able to get configuration detail from endpoint /admin/configurations/{ConfigurationID}
    Given User is able to get specific configuration detail
      | i.configurationID | <i.configurationID> |
      | o.id              | <o.id>              |
      | o.responseCode    | <o.responseCode>    |
      | o.errorMessage    | <o.errorMessage>    |
      | o.providerId      | <o.providerId>      |
      | o.type            | <o.type>            |
      | o.name            | <o.name>            |
      | o.status          | <o.status>          |
      | o.host            | <o.host>            |
      | o.createdDate     | <o.createdDate>     |
      | o.modifiedDate    | <o.modifiedDate>    |

    Examples:
      | o.responseCode | o.errorMessage             | i.configurationID                | o.id                             | o.providerId                     | o.type     | o.name                      | o.status | o.host                  | o.createdDate            | o.modifiedDate           |
      | 200            |                            | 0185e57d00ccb0e68298c0a8e5b45a73 | 0185e57d00ccb0e68298c0a8e5b45a73 | 0184f828214f6b7a03c711284b2b8e39 | PRODUCTION | Chatflow Zendesk Sell PROD1 | DISABLED | https://api.getbase.com | 2023-01-24T20:34:12.815Z | 2023-01-24T23:19:02.189Z |
      | 404            | Configuration ID not found | 0184f828214f6b7a03c711284b2false |                                  |                                  |            |                             |          |                         |                          |                          |
     #Null configurationID Bug logged: CCH-700|400            | Bad request               |                                  |                                |                                |          |                                 |               |                             |                           |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-703")
  Scenario Outline: CH :: Admin API :: Configurations API: /admin/configurations/{configurationId}/{secrets} should return configuration with client id and client secret
    Given User is able to get configuration with client id and client secret
      | i.configurationId                | <i.configurationId>                |
      | o.responseCode                   | <o.responseCode>                   |
      | o.errorMessage                   | <o.errorMessage>                   |
      | o.id                             | <o.id>                             |
      | o.providerId                     | <o.providerId>                     |
      | o.accountProviderConfigStatusId  | <o.accountProviderConfigStatusId>  |
      | o.configurationEnvironmentTypeId | <o.configurationEnvironmentTypeId> |
      | o.displayName                    | <o.displayName>                    |
      | o.clientId                       | <o.clientId>                       |
      | o.clientSecret                   | <o.clientSecret>                   |
      | o.hostUrl                        | <o.hostUrl>                        |

    Examples:
      | o.responseCode | o.errorMessage             | i.configurationId                | o.id | o.providerId | o.accountProviderConfigStatusId | o.configurationEnvironmentTypeId | o.displayName | o.clientId | o.clientSecret | o.hostUrl |
#DefectId: CCH-822      | 200            |                            | 0185a771e2d64aadd296aedbc0ef2492 | 0185a771e2d64aadd296aedbc0ef2492 | 0184f820c06ec8b62dfa0610e29ab575 | ACTIVE                          | PRODUCTION                       | CH_Test_AutoTester2023-97 | testoauth  | 6b38***************6bf5 | https://d3v-clickatell2162.zendesk.com |
      | 404            | Configuration ID not found | 0185a771e2d64aadd296aedbc0Af2492 |      |              |                                 |                                  |               |            |                |           |
    #Null configurationID Bug logged: CCH-700|400           |Bad request                     |                                |                                |                                 |                                   |                                            |                             |                |                          |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-704")
  Scenario Outline: CH :: Admin API :: Configurations API: /admin/configurations/activate: User should be able to create and activate configuration
    Given User should be able to create and activate configuration
      | i.mc2AccountId | <i.mc2AccountId> |
      | i.name         | <i.name>         |
      | o.responseCode | <o.responseCode> |
      | o.errorMessage | <o.errorMessage> |
      | i.clientSecret | <i.clientSecret> |
      | i.clientId     | <i.clientId>     |
      | i.host         | <i.host>         |
      | i.providerId   | <i.providerId>   |
      | i.type         | <i.type>         |
      | o.setupName    | <o.setupName>    |
      | o.type         | <o.type>         |
      | o.timeToExpire | <o.timeToExpire> |

    Examples:
      | o.responseCode | i.name         | o.errorMessage | i.mc2AccountId                   | i.clientSecret                                                   | i.clientId       | i.host                                 | i.providerId                     | i.type     | o.setupName    | o.type     | o.timeToExpire |
      | 200            | Chathub_Test40 |                | bb0496c20c434a76a927e7419075fcc3 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth        | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | Chathub_Test40 | SANDBOX    | 300            |
      | 200            | Chathub_Test41 |                | bb0496c20c434a76a927e7419075fcc3 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth        | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | Chathub_Test41 | PRODUCTION | 300            |
      | 200            | IncorrectName4 |                | bb0496c20c434a76a927e7419075fcc3 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth        | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | IncorrectName4 | SANDBOX    | 300            |
      | 200            | Chathub_Test42 |                | bb0496c20c434a76a927e7419075fcc3 | Incorrect client secret                                          | testoauth        | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | Chathub_Test42 | PRODUCTION | 300            |
      | 200            | Chathub_Test43 |                | bb0496c20c434a76a927e7419075fcc3 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | Incorrect client | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | Chathub_Test43 | PRODUCTION | 300            |
      | 400            |                | Bad request    | bb0496c20c434a76a927e7419075fcc3 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth        | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    |                | SANDBOX    | 300            |
      | 400            | Chathub_Test46 | Bad request    | bb0496c20c434a76a927e7419075fcc3 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth        |                                        | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | Chathub_Test46 | SANDBOX    | 300            |
      | 400            | Chathub_Test47 | Bad request    | bb0496c20c434a76a927e7419075fcc3 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth        | https://d3v-clickatell2162.zendesk.com |                                  | SANDBOX    | Chathub_Test47 | SANDBOX    | 300            |
      | 400            | Chathub_Test48 | Bad request    | bb0496c20c434a76a927e7419075fcc3 | 6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 | testoauth        | https://d3v-clickatell2162.zendesk.com | 0184f820c06ec8b62dfa0610e29ab575 |            | Chathub_Test48 | SANDBOX    | 300            |
      #Incorrect host Bug logged:CCH-705       |404            |Chathub_Test24       |Host not found|bb0496c20c434a76a927e7419075fcc3|6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5|testoauth    |Incorrect host                          |0184f820c06ec8b62dfa0610e29ab575|PRODUCTION  |Chathub_Test24|PRODUCTION      |300                |
      #Incorect Type Bug logged :CCH-705|404            |Chathub_Test06       |Type not found                                                      |bb0496c20c434a76a927e7419075fcc3|6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 |testoauth     |https://d3v-clickatell2162.zendesk.com  |0184f820c06ec8b62dfa0610e29ab575|Incorrect   |Chathub_Test06|PRODUCTION      |300                |
      #Incorrect ProviderIDBug logged :CCH-705 |404   |Chathub_Test2823|Provider ID not found         |bb0496c20c434a76a927e7419075fcc3|6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 |testoauth     |https://d3v-clickatell2162.zendesk.com  |Incorrect                        |PRODUCTION     |PRODUCTION   |   |300                |
      #Incorrect MC2ID Bug logged :CCH-705|404   |Chathub_Test2823        |MC2 ID not found|Incorrect         |6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 |testoauth     |https://d3v-clickatell2162.zendesk.com  |0184f820c06ec8b62dfa0610e29ab575|PRODUCTION     |PRODUCTION      |300|
      #NULL ClientID  Bug logged CCH-699|400    |Chathub_Test2823      |Bad request                    |bb0496c20c434a76a927e7419075fcc3|6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 |testoauth     |https://d3v-clickatell2162.zendesk.com  |0184f820c06ec8b62dfa0610e29ab575|SANDBOX        |SANDBOX    |     |300                |
      #NULL Client secret Bug logged CCH-699|400    |Chathub_Test2823     |Bad request                    |bb0496c20c434a76a927e7419075fcc3|                                                                 |             |https://d3v-clickatell2162.zendesk.com  |0184f820c06ec8b62dfa0610e29ab575|SANDBOX        |SANDBOX      |   |300                |
      #Null MC2ID Bug logged CCH-699       |400            |Chathub_Test01       |Bad request  |                 |6b3806f4286be63fb6ef2ef1d7f73a6940c559e3f096a5a24a329740820f6bf5 |testoauth     |https://d3v-clickatell2162.zendesk.com  |0184f820c06ec8b62dfa0610e29ab575|SANDBOX    |Chathub_Test01|SANDBOX         |300                |

  # This API requires unique input names to execute. The solution to it is the data we will create has to be deleted at the end but currently disable API is not working because of CCH-715. Once it will start working and will be tested will add steps to disable and delete activated configurations , in that way the same data will be created and deleted.# Another approach
  # was a temporary fix where we could've generated random names in the data maps but after discussions came to the conclusion that the effort is not worth the time for a temporary fix.# So, for now to enter unique and same values in 'i.name' and 'o.setupName' to execute this test case.