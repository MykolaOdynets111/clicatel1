Feature: Provider API


  @TestCaseId("https://jira.clickatell.com/browse/CCH-509")
  Scenario Outline: CH :: Public provider APIs: Execute end point Get /api/providers with Authentication token should respond all the activated providers.
    Given User is able to GET providers API response
    Examples:
      | i.ProviderID                     | o.resposecode | o.errordescription | o.providerid                     | o.providername  |
      | 0184f820c06ec8b62dfa0610e29ab575 | 200           |                    | 0184f820c06ec8b62dfa0610e29ab575 | Zendesk Support |

  @TestCaseId("https://jira.clickatell.com/browse/CCH-639")
  Scenario Outline: CCH :: Public :: Provider API :  Validate provider state.
    Given User is able to GET providers state in API response
      | i.o.providerID   | <i.o.providerID>   |
      | o.responseCode | <o.responseCode> |
      | o.errorMessage | <o.errorMessage> |
      | o.providerName | <o.providerName> |
      | o.status       | <o.status>       |

    Examples:
      | i.o.providerID                   | o.responseCode   | o.errorMessage     | o.providerName  | o.status |
      | 0184f820c06ec8b62dfa0610e29ab575 | 200              | none               | Zendesk Support | true     |
    #Data to be created | 99                               | 200              |                    | Salesforce      | false    |
      |                                  | 404              | Provider not found |                 |          |
      | 17hfo72rhwf                      | 404              | Provider not found |                 |          |