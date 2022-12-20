Feature: Provider API


  @TestCaseId("https://jira.clickatell.com/browse/CCH-509")
  Scenario Outline: CH :: Public provider APIs: Execute end point Get /api/providers with Authentication token should respond all the activated providers.
    Given User is able to GET providers API response
    Examples:
      | i.ProviderID                     | o.resposecode | o.errordescription | o.providerid                     | o.providername  |
      | 0184f820c06ec8b62dfa0610e29ab575 | 200           |                    | 0184f820c06ec8b62dfa0610e29ab575 | Zendesk Support |

  @TestCaseId("https://jira.clickatell.com/browse/CCH-333")
  Scenario: ABC
    Given User is able to GET providers state in API response
      | i.ProviderID                     | o.resposecode | o.errordescription | o.providerid                     | o.providername  | o.status |
      | 0184f820c06ec8b62dfa0610e29ab575 | 200           |                    | 0184f820c06ec8b62dfa0610e29ab575 | Zendesk Support | Active   |
      #| 99                               | 200           |                    | 99                               | Salesforce      | Inactive |
      |                                  | 404           | Provider not found |                                  |                 |          |
      | 17hfo72rhwf                      | 404           | Provider not found |                                  |                 |          |