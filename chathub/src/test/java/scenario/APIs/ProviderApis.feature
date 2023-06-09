Feature: Provider API

  @TestCaseId("https://jira.clickatell.com/browse/CCH-509")
  Scenario: CCH :: Public :: Provider API : Get providers
    Given User is able to GET providers API response
      | o.id                             | o.name          | o.logoUrl | o.description | o.moreInfoUrl | o.vid  | o.version       | o.latest | o.isAdded |
      | 0184f820c06ec8b62dfa0610e29ab575 | Zendesk Support | NA        | NA            | NA            | v1.0.0 | Zendesk Support | true     | true      |
      | 0184f828214f6b7a03c711284b2b8e39 | Zendesk Sell    | NA        | NA            | NA            | v1.0.0 | Zendesk Sell    | true     | true      |
      | 0184f8322847eaddbda79d5a29eaa5d4 | Salesforce      | NA        | NA            | NA            | v1.0.0 | Salesforce      | true     | true      |
      | 0185172bf6b57e9831c6d6616bc68317 | Shopify         | string    | string        | string        | v1.0.0 | Shopify         | true     | true      |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-518")
  Scenario: CCH :: Internal :: Provider API : Get providers
    Given User is able to GET providers API response - Internal
      | o.id                             | o.name          | o.logoUrl | o.description | o.moreInfoUrl | o.vid  | o.version       | o.latest |
      | 0184f820c06ec8b62dfa0610e29ab575 | Zendesk Support | NA        | NA            | NA            | v1.0.0 | Zendesk Support | true     |
      | 0184f828214f6b7a03c711284b2b8e39 | Zendesk Sell    | NA        | NA            | NA            | v1.0.0 | Zendesk Sell    | true     |
      | 0184f8322847eaddbda79d5a29eaa5d4 | Salesforce      | NA        | NA            | NA            | v1.0.0 | Salesforce      | true     |
      | 0185172bf6b57e9831c6d6616bc68317 | Shopify         | string    | string        | string        | v1.0.0 | Shopify         | true     |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-639")
  Scenario Outline: CCH :: Public :: Provider API : Validate provider state
    Given User is able to GET providers state in API response
      | i.providerId   | <i.providerId>   |
      | o.responseCode | <o.responseCode> |
      | o.errorMessage | <o.errorMessage> |
      | o.providerId   | <o.providerId>   |
      | o.providerName | <o.providerName> |
      | o.status       | <o.status>       |

    Examples:
      | i.providerId                     | o.responseCode | o.errorMessage     | o.providerId                     | o.providerName  | o.status |
      | 0184f820c06ec8b62dfa0610e29ab575 | 200            | none               | 0184f820c06ec8b62dfa0610e29ab575 | Zendesk Support | true     |
    #Data to be created after integration build deployment | 99                               | 200               |                                  |     Salesforce  | false    |
      |                                  | 404            | Provider not found |                                  |                 |          |
      | 17hfo72rhwf                      | 404            | Provider not found |                                  |                 |          |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-513")
  Scenario Outline: CCH :: Internal :: Provider API : Validate provider state
    Given User is able to GET providers state in API response - Internal
      | i.providerId   | <i.providerId>   |
      | o.responseCode | <o.responseCode> |
      | o.errorMessage | <o.errorMessage> |
      | o.providerId   | <o.providerId>   |
      | o.providerName | <o.providerName> |
      | o.status       | <o.status>       |

    Examples:
      | i.providerId                     | o.responseCode | o.errorMessage     | o.providerId                     | o.providerName  | o.status |
      | 0184f820c06ec8b62dfa0610e29ab575 | 200            |                    | 0184f820c06ec8b62dfa0610e29ab575 | Zendesk Support | true     |
#Data to be created after integration build is deployed      | 99                               | 200            |                    | 99                               | Salesforce     | Inactive |
#BugID: CCH-696      |                                  | 404            | Provider not found |                                  |                 |          |
      | Wrong id                         | 404            | Provider not found |                                  |                 |          |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-669")
  Scenario: CCH :: Admin :: Provider API : Get providers
    Given Admin is able to GET providers API response
      | o.id                             | o.name          | o.logoUrl | o.description | o.moreInfoUrl | o.vid  | o.version       | o.latest |
      | 0184f820c06ec8b62dfa0610e29ab575 | Zendesk Support | NA        | NA            | NA            | v1.0.0 | Zendesk Support | true     |
      | 0184f828214f6b7a03c711284b2b8e39 | Zendesk Sell    | NA        | NA            | NA            | v1.0.0 | Zendesk Sell    | true     |
      | 0184f8322847eaddbda79d5a29eaa5d4 | Salesforce      | NA        | NA            | NA            | v1.0.0 | Salesforce      | true     |
      | 0185172bf6b57e9831c6d6616bc68317 | Shopify         | string    | string        | string        | v1.0.0 | Shopify         | true     |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-674")
  Scenario Outline: CCH :: Admin :: Provider API : Get existing provider details
    Given Admin is able to GET existing provider details
      | i.providerId   | <i.providerId>   |
      | o.responseCode | <o.responseCode> |
      | o.errorMessage | <o.errorMessage> |
      | o.id           | <o.id>           |
      | o.name         | <o.name>         |
      | o.logoUrl      | <o.logoUrl>      |
      | o.description  | <o.description>  |
      | o.moreInfoUrl  | <o.moreInfoUrl>  |

    Examples:
      | i.providerId                     | o.responseCode | o.errorMessage     | o.id                             | o.name          | o.logoUrl | o.description | o.moreInfoUrl |
      | 0184f820c06ec8b62dfa0610e29ab575 | 200            |                    | 0184f820c06ec8b62dfa0610e29ab575 | Zendesk Support | NA        | NA            | NA            |
      | 0184f828214f6b7a03c711284b2b8e39 | 200            |                    | 0184f828214f6b7a03c711284b2b8e39 | Zendesk Sell    | NA        | NA            | NA            |
      | 0184f8322847eaddbda79d5a29eaa5d4 | 200            |                    | 0184f8322847eaddbda79d5a29eaa5d4 | Salesforce      | NA        | NA            | NA            |
      | 0185172bf6b57e9831c6d6616bc68317 | 200            |                    | 0185172bf6b57e9831c6d6616bc68317 | Shopify         | string    | string        | string        |
      | Wrong Id                         | 404            | Provider not found |                                  |                 |           |               |               |
     #BugId: CCH-701 ||400|Bad request||||||


  @TestCaseId("https://jira.clickatell.com/browse/CCH-672")
  Scenario: CCH :: Admin :: Get configured provider for customer (200 response coverage in the test case)
    Given Admin is able to GET configured provider for customer
      | i.mc2AccountId                   | o.responseCode | o.errorMessage | o.id                             | o.name          | o.logoUrl | o.description | o.moreInfoUrl | o.vid  | o.version       | o.latest |
      | bb0496c20c434a76a927e7419075fcc3 | 200            |                | 0184f820c06ec8b62dfa0610e29ab575 | Zendesk Support | NA        | NA            | NA            | v1.0.0 | Zendesk Support | true     |
      |                                  |                |                | 0184f8322847eaddbda79d5a29eaa5d4 | Salesforce      | NA        | NA            | NA            | v1.0.0 | Salesforce      | true     |

  @TestCaseId("https://jira.clickatell.com/browse/CCH-823")
  Scenario Outline: CCH :: Admin :: Get configured provider for customer (other than 200 response coverage in the test case)
    Given Admin is able to GET configured provider for customer
      | i.mc2AccountId   | o.responseCode   | o.errorMessage   | o.id   | o.name   | o.logoUrl   | o.description   | o.moreInfoUrl   | o.vid   | o.version   | o.latest   |
      | <i.mc2AccountId> | <o.responseCode> | <o.errorMessage> | <o.id> | <o.name> | <o.logoUrl> | <o.description> | <o.moreInfoUrl> | <o.vid> | <o.version> | <o.latest> |
    Examples:
      | i.mc2AccountId | o.responseCode | o.errorMessage | o.id | o.name | o.logoUrl | o.description | o.moreInfoUrl | o.vid | o.version | o.latest |
    #BugId CCH-710  | Wrong input                      | 404            | mc2AccountId not found |                                  |                 |           |               |               |        |                 |          |
    #BUgId CCH-701 |                                  | 400            | Bad request            |                                  |                 |           |               |               |        |                 |          |


  @TestCaseId("https://jira.clickatell.com/browse/CCH-673")
  Scenario Outline: CCH :: Admin :: Create Provider API
    Given Admin is able to create provider
      | i.name         | <i.name>         |
      | i.logoUrl      | <i.logoUrl>      |
      | i.description  | <i.description>  |
      | i.moreInfoUrl  | <i.moreInfoUrl>  |
      | o.responseCode | <o.responseCode> |
      | o.errorMessage | <o.errorMessage> |
      | o.name         | <o.name>         |
      | o.logoUrl      | <o.logoUrl>      |
      | o.description  | <o.description>  |
      | o.moreInfoUrl  | <o.moreInfoUrl>  |
    Examples:
      | i.name              | i.logoUrl | i.description | i.moreInfoUrl | o.responseCode | o.errorMessage | o.name              | o.logoUrl | o.description | o.moreInfoUrl |
      | Auto_Test_Provider1 | NA        | NA            | NA            | 201            |                | Auto_Test_Provider1 | NA        | NA            | NA            |
  #BugID: CCH-709    |Auto_Test_Provider1 |NA|NA|NA|412|Provider name already present|||||
      |                     | NA        | NA            | NA            | 404            | Bad request    |                     |           |               |               |
      | Auto_Test_Provider2 |           | NA            | NA            | 404            | Bad request    |                     |           |               |               |
      | Auto_Test_Provider3 | NA        |               | NA            | 404            | Bad request    |                     |           |               |               |
      | Auto_Test_Provider4 | NA        | NA            |               | 404            | Bad request    |                     |           |               |               |


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
      | i.id                             | i.name                      | i.logoUrl  | i.description      | i.moreInfoUrl | o.responseCode | o.errorMessage     | o.id                             | o.name                      | o.logoUrl  | o.description      | o.moreInfoUrl |
      | 0185cbc593c73974633631885e71053f | Auto_Tester_Provider_Update | UpdatedURL | UpdatedDescription | UpdatedInfo   | 200            |                    | 0185cbc593c73974633631885e71053f | Auto_Tester_Provider_Update | UpdatedURL | UpdatedDescription | UpdatedInfo   |
      | Wrong ID                         | Auto_Tester_Provider_Update | UpdatedURL | UpdatedDescription | UpdatedInfo   | 404            | Provider not found |                                  |                             |            |                    |               |
  #BugId CCH-701 || Auto_Tester_Provider_Update | UpdatedURL | UpdatedDescription | UpdatedInfo | 400 | Bad request ||||||