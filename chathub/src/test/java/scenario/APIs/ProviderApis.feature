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
      | i.providerID   | <i.providerID>   |
      | o.responseCode | <o.responseCode> |
      | o.errorMessage | <o.errorMessage> |
      | o.providerID   | <o.providerID>   |
      | o.providerName | <o.providerName> |
      | o.status       | <o.status>       |

    Examples:
      | i.providerID                     | o.responseCode | o.errorMessage     | o.providerID                     | o.providerName  | o.status |
      | 0184f820c06ec8b62dfa0610e29ab575 | 200            | none               | 0184f820c06ec8b62dfa0610e29ab575 | Zendesk Support | true     |
    #Data to be created | 99                               | 200               |                                  |     Salesforce  | false    |
      |                                  | 404            | Provider not found |                                  |                 |          |
      | 17hfo72rhwf                      | 404            | Provider not found |                                  |                 |          |

  @TestCaseId("https://jira.clickatell.com/browse/CCH-675")
  Scenario Outline: CCH :: Admin :: Provider API : Update existing provider details
    Given Admin is able to update existing provider details
      | i.id           | <i.id>           |
      | i.name         | <i.name>         |
      | i.logoUrl      | <i.logoUrl>      |
      | i.description  | <i.description>  |
      | i.moreInfoUrl  | <i.moreInfoUrl>  |
      | o.responseCode | <o.responseCode> |
      | o.errorMessage | <o.errorMessage> |
      | o.id           | <o.id>           |
      | o.name         | <o.name>         |
      | o.logoUrl      | <o.logoUrl>      |
      | o.description  | <o.description>  |
      | o.moreInfoUrl  | <o.moreInfoUrl>  |

    Examples:
      | i.id                             | i.name                      | i.logoUrl  | i.description      | i.moreInfoUrl | o.responseCode | o.errorMessage | o.id                             | o.name                      | o.logoUrl  | o.description      | o.moreInfoUrl |
      | 0185cbc593c73974633631885e71053f | Auto_Tester_Provider_Update | UpdatedURL | UpdatedDescription | UpdatedInfo   | 200            |                | 0185cbc593c73974633631885e71053f | Auto_Tester_Provider_Update | UpdatedURL | UpdatedDescription | UpdatedInfo   |
      | Wrong ID                         | Auto_Tester_Provider_Update | UpdatedURL | UpdatedDescription | UpdatedInfo   | 404         | Provider not found  |                                  |                             |            |||