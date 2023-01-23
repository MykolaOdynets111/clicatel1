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
      | 0185bbb0bd4606f5bd390e857d2c8aca | 200            |                     | 0185bbb0bd4606f5bd390e857d2c8aca | 0184f820c06ec8b62dfa0610e29ab575 | DISABLED                        | SANDBOX                          | CH_Test_AutoTester2023-115 | testoauth  | 6b38***************6bf5 | https://d3v-clickatell2162.zendesk.com |
      | 0185bbb287a0e71d4c06bfb4de2a910c | 200            |                     | 0185bbb287a0e71d4c06bfb4de2a910c | 0184f820c06ec8b62dfa0610e29ab575 | AUTH_PENDING                    | SANDBOX                          | CH_Test_AutoTester2023-116 | testoauth  | 6b38***************6bf5 | https://d3v-clickatell2162.zendesk.com |
      | 0185bbb47862d93e7c9f2bf20512a19c | 200            |                     | 0185bbb47862d93e7c9f2bf20512a19c | 0184f820c06ec8b62dfa0610e29ab575 | DISABLED                        | PRODUCTION                       | CH_Test_AutoTester2023-117 | testoauth  | 6b38***************6bf5 | https://d3v-clickatell2162.zendesk.com |
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
      | Wrong Id                         | 404            | Configuration ID not found |                                  |                                  |            |                            |          |                                        |
#AUTHPENDING case, BUGID: To be reported      | TRUE                             |                | Precondition failed     |                                      |                                  |            |                            |              |                                        |
#With ChatFlow or ChatDesk activated, Date to be created      | TRUE                             | 500            | Precondition failed     |                                      |                                  |            |                            |              |                                        |

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
# Note: If the code is not runnng then data from the backend has been changed, need to update the ids or outputs. Code is working perfectly fine.
    Examples:
      | i.id                              | i.providerId                     | o.responseCode | o.errorMessage             | o.id                             | o.type     | o.setupName                   | o.timeToExpire |
      | 0185ceb0c0f5dc0bad548c3baf3ea2fa  | 0184f820c06ec8b62dfa0610e29ab575 | 200            |                            | 0185ceb0c0f5dc0bad548c3baf3ea2fa | SANDBOX    | chathub_test_UHTester2023-153 | 300            |
      | 0185ceaddc7af760ebfb1e3cbfbe0e0d  | 0184f820c06ec8b62dfa0610e29ab575 | 200            |                            | 0185ceaddc7af760ebfb1e3cbfbe0e0d | PRODUCTION | chathub_test_UHTester2023-152 | 300            |
      | WrongId                           | 0184f820c06ec8b62dfa0610e29ab575 | 404            | Configuration ID not found |                                  |            |                               |                |
#WrongError message #BudID to be added      | 0185ceaddc7af760ebfb1e3cbfbe0e0d  | WrongId                          | 404            | ProviderId not found       |                                  |            |                               |                |
      | 0185ceb113150350596852b3d3dd42f1  | 0184f820c06ec8b62dfa0610e29ab575 | 200            |                            | 0185ceb113150350596852b3d3dd42f1 | SANDBOX    | chathub_test_UHTester2023-154 | 300            |
      | 0185ceb1577cb56bd671a5d651f2dd19  | 0184f820c06ec8b62dfa0610e29ab575 | 200            |                            | 0185ceb1577cb56bd671a5d651f2dd19 | SANDBOX    | chathub_test_UHTester2023-155 | 300            |
      | 0185ceb1a9d7b89b3d45f0fd6d0082b5  | 0184f820c06ec8b62dfa0610e29ab575 | 200            |                            | 0185ceb1a9d7b89b3d45f0fd6d0082b5 | SANDBOX    | chathub_test_UHTester2023-156 | 300            |
      | 0185ceb1a9d7b89b3d45f0fd6d0081123 | 0184f820c06ec8b62dfa0610e29ab575 | 404            | Configuration ID not found |                                  |            |                               |                |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-549")
  Scenario: CCH :: Public :: Configurations API: Get all configurations should return configurations with all the status except AUTH_PENDING
    Given User is able to get all configurations for a provider - Check 200 responses
      | i.providerId                     | i.version | o.responseCode | o.id                             | o.providerId                     | o.type     | o.name                        | o.status | o.host                                 | o.createdDate            | o.modifiedDate           |
      | 0184f820c06ec8b62dfa0610e29ab575 |           | 200            | 0185a771e2d64aadd296aedbc0ef2492 | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | CH_Test_AutoTester2023-97     | ACTIVE   | https://d3v-clickatell2162.zendesk.com | 2023-01-12T19:25:37.065Z | 2023-01-16T11:10:06.477Z |
      |                                  |           |                | 0185bb6c699de39640ff6012e6e07548 | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | CH_Test_AutoTester2023-112    | ACTIVE   | https://d3v-clickatell2162.zendesk.com | 2023-01-16T16:32:02.463Z | 2023-01-16T16:32:50.350Z |
      |                                  |           |                | 0185bbb0bd4606f5bd390e857d2c8aca | 0184f820c06ec8b62dfa0610e29ab575 | SANDBOX    | CH_Test_AutoTester2023-115    | DISABLED | https://d3v-clickatell2162.zendesk.com | 2023-01-16T17:46:40.329Z | 2023-01-20T09:12:34.093Z |
      |                                  |           |                | 0185bbb47862d93e7c9f2bf20512a19c | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | CH_Test_AutoTester2023-117    | DISABLED | https://d3v-clickatell2162.zendesk.com | 2023-01-16T17:50:44.837Z | 2023-01-20T08:29:19.934Z |
      |                                  |           |                | 0185c5cc199e5bce1883edfce9b7f38e | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | chathub_test_UHTester2023-111 | ACTIVE   | https://d3v-clickatell2162.zendesk.com | 2023-01-18T16:52:45.600Z | 2023-01-18T16:53:16.983Z |
      |                                  |           |                | 0185c5cd63abb75652e8096bf6ba0129 | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | chathub_test_UHTester2023-112 | ACTIVE   | https://d3v-clickatell2162.zendesk.com | 2023-01-18T16:54:10.093Z | 2023-01-18T16:54:20.437Z |
      |                                  |           |                | 0185c5e1085e3684bac8193a6511957d | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | chathub_test_ADTester2023-114 | ACTIVE   | https://d3v-clickatell2162.zendesk.com | 2023-01-18T17:15:37.440Z | 2023-01-18T17:15:47.884Z |
      |                                  |           |                | 0185c616d6faca2c2e727cc660a146dd | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | chathub_test_ADTester2023-110 | ACTIVE   | https://d3v-clickatell2162.zendesk.com | 2023-01-18T18:14:23.740Z | 2023-01-18T18:15:51.984Z |
      |                                  |           |                | 0185c61b8d3e3f432e082d17303d7eef | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | chathub_test_UHTester2023-120 | DISABLED | https://d3v-clickatell2162.zendesk.com | 2023-01-18T18:19:32.544Z | 2023-01-18T20:11:38.610Z |
      |                                  |           |                | 0185c6889dd3bcf02085659411a68260 | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | chathub_test_UHTester2023-121 | DISABLED | https://d3v-clickatell2162.zendesk.com | 2023-01-18T20:18:40.213Z | 2023-01-18T20:23:47.901Z |
      |                                  |           |                | 0185ca9a61a1ae246ac93a9e382c8de8 | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | chathub_test_UHTester2023-121 | ACTIVE   | https://d3v-clickatell2162.zendesk.com | 2023-01-19T15:16:33.495Z | 2023-01-19T15:17:18.485Z |
      |                                  |           |                | 0185ce80cc6ac8aa1243eee8282ce872 | 0184f820c06ec8b62dfa0610e29ab575 | PRODUCTION | chathub_test_UHTester2023-150 | DISABLED | https://d3v-clickatell2162.zendesk.com | 2023-01-20T09:27:05.588Z | 2023-01-20T09:32:04.030Z |

  @TestCaseId("https://jira.clickatell.com/browse/CCH-664")
  Scenario Outline: CCH :: Public :: Configurations API: Get all configurations should return configurations with all the status except AUTH_PENDING (other than 200 response coverage in the test case)
    Given User is able to get all configurations for a provider - Check non 200 responses
      | i.providerId   | <i.providerId>   |
      | i.version      | <i.version>      |
      | o.responseCode | <o.responseCode> |
      | o.errorMessage | <o.errorMessage> |

    Examples:
      | i.providerId | i.version | o.responseCode | o.errorMessage                                   |
      | WrongId      |           | 404            | Provider ID or Version ID not found for Provider |
# Will be implemented after LR     | 0184f820c06ec8b62dfa0610e29ab575 | Wrong     | 404  | Provider ID or Version ID not found for Provider |
# bug to be reported      | NULL         |           | 404            | bad reuqest                                      |
 # Will be implemented after LR    | 0184f820c06ec8b62dfa0610e29ab575 | NULL      | 404  | bad reuqest                                      |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-571")
  Scenario: CCH :: Internal :: Configurations API: Get all configurations should return configurations with all the status (200 response coverage in the test case)
    Given User is able to get all configurations for a provider - Check 200 responses - Internal
      | i.providerId                     | i.version | o.responseCode | o.errorMessage | o.id                             | o.displayName                 | o.configurationEnvironmentTypeId | o.accountProviderConfigStatusId |
      | 0184f820c06ec8b62dfa0610e29ab575 |           | 200            |                | 0185a771e2d64aadd296aedbc0ef2492 | CH_Test_AutoTester2023-97     | PRODUCTION                       | ACTIVE                          |
      |                                  |           |                |                | 0185bb6c699de39640ff6012e6e07548 | CH_Test_AutoTester2023-112    | PRODUCTION                       | ACTIVE                          |
      |                                  |           |                |                | 0185c5cc199e5bce1883edfce9b7f38e | chathub_test_UHTester2023-111 | PRODUCTION                       | ACTIVE                          |
      |                                  |           |                |                | 0185c5cd63abb75652e8096bf6ba0129 | chathub_test_UHTester2023-112 | PRODUCTION                       | ACTIVE                          |
      |                                  |           |                |                | 0185c5e1085e3684bac8193a6511957d | chathub_test_ADTester2023-114  | PRODUCTION                       | ACTIVE                          |
      |                                  |           |                |                | 0185c616d6faca2c2e727cc660a146dd | chathub_test_ADTester2023-110 | PRODUCTION                       | ACTIVE                          |
      |                                  |           |                |                | 0185ca9a61a1ae246ac93a9e382c8de8 | chathub_test_UHTester2023-121 | PRODUCTION                       | ACTIVE                          |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-685")
  Scenario Outline: CCH :: Internal :: Configurations API: Get all configurations should return configurations with all the status (other than 200 response coverage in the test case)
    Given User is able to get all configurations for a provider - Check non 200 responses - Internal
      | i.providerId   | <i.providerId>   |
      | i.version      | <i.version>      |
      | o.responseCode | <o.responseCode> |
      | o.errorMessage | <o.errorMessage> |

    Examples:
      | i.providerId | i.version | o.responseCode | o.errorMessage                                   |
      | WrongId      |           | 404            | Provider ID or Version ID not found for Provider |
# Will be implemented after LR     | 0184f820c06ec8b62dfa0610e29ab575 | Wrong     | 404  | Provider ID or Version ID not found for Provider |
# bug to be reported      | NULL         |           | 404            | bad reuqest                                      |
 # Will be implemented after LR    | 0184f820c06ec8b62dfa0610e29ab575 | NULL      | 404  | bad reuqest                                      |
