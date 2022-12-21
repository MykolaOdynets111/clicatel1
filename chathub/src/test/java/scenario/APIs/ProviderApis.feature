Feature: Provider API


  @TestCaseId("https://jira.clickatell.com/browse/CCH-509")
  Scenario Outline: CH :: Public provider APIs: Execute end point Get /api/providers with Authentication token should respond all the activated providers.
    Given User is able to GET providers API response
    Examples:
      | i.ProviderID                     | o.resposecode | o.errordescription | o.providerid                     | o.providername  |
      | 0184f820c06ec8b62dfa0610e29ab575 | 200           |                    | 0184f820c06ec8b62dfa0610e29ab575 | Zendesk Support |

  @TestCaseId("https://jira.clickatell.com/browse/CCH-333")
  Scenario Outline: ABC
    Given User is able to GET providers state in API response
      | providerID   | <providerID>   |
      | responseCode | <responseCode> |
      | errorMessage | <errorMessage> |
      | providerName | <providerName> |
      | status       | <status>       |

    Examples:
      | providerID                       | responseCode | errorMessage       | providerName    | status |
      | 0184f820c06ec8b62dfa0610e29ab575 | 200          | none               | Zendesk Support | Active |
     # | 99                               | 200         |                    | Salesforce      | Inactive|
      |                                  | 404          | Provider not found |                 |        |
      | 17hfo72rhwf                      | 404          | Provider not found |                 |        |