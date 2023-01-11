Feature: Provider API


  @TestCaseId("https://jira.clickatell.com/browse/CCH-509")
  Scenario Outline: CCH :: Public :: Provider API : Get providers
    Given User is able to GET providers API response
      | o.responseCode       | <o.responseCode>       |
      | o.ErrorDescription | <o.ErrorDescription> |
      | o.id               | <o.id>               |
      | o.name             | <o.name>             |
      | o.logoUrl          | <o.logoUrl>          |
      | o.description      | <o.description>      |
      | o.moreInfoUrl      | <o.moreInfoUrl>      |
      | o.version          | <o.version>          |
      | o.latest           | <o.latest>           |
      | o.isAdded          | <o.isAdded>          |
    Examples:
      | o.responseCode | o.ErrorDescription | o.id                             | o.name          | o.logoUrl | o.description | o.moreInfoUrl | o.id   | o.version       | o.latest | o.isAdded |
      | 200          |                    | 0184f820c06ec8b62dfa0610e29ab575 | Zendesk Support | NA        | NA            | NA            | v1.0.0 | Zendesk Support | TRUE     | TRUE        |
      |              |                    | 0184f828214f6b7a03c711284b2b8e39 | Zendesk Sell    | NA        | NA            | NA            | v1.0.0 | Zendesk Sell    | TRUE     | TRUE        |

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