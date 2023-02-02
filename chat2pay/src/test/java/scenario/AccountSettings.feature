Feature: Get settings for an account

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4712")
  Scenario Outline: C2P Unity API :: GET /settings :: truth table

    Given User is logged in to unity
    And User gets account settings
      | activationKey | <i.activationKey> |
      | responseCode  | <o.responseCode>  |
      | accountId     | <o.accountId>     |
      | showTutorial  | <o.showTutorial>  |
      | error         | <o.error>         |
      | path          | <o.path>          |

    Examples:
      | i.activationKey | o.responseCode | o.accountId                      | o.showTutorial | o.error      | o.path               |
      | token           | 200            | f06fc54419f04909be23f315027cba1b | false          |              |                      |
      | test            | 401            |                                  |                | Unauthorized | /v2/account/settings |
      | " "             | 401            |                                  |                | Unauthorized | /v2/account/settings |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4713")
  Scenario Outline: C2P Unity API :: PUT /show-tutorial :: truth table

    Given User is logged in to unity
    And User updates account settings
      | activationKey       | <i.activationKey> |
      | showTutorial        | <i.showTutorial>  |
      | responseCode        | <o.responseCode>  |
      | updatedShowTutorial | <o.showTutorial>  |
      | error               | <o.error>         |
      | path                | <o.path>          |

    Examples:
      | i.activationKey | i.showTutorial | o.responseCode | o.showTutorial | o.error      | o.path                             |
      | token           | false          | 200            | false          |              |                                    |
      | token           | true           | 200            | true           |              |                                    |
      | test            |                | 401            |                | Unauthorized | /v2/account/settings/show-tutorial |
      | " "             |                | 401            |                | Unauthorized | /v2/account/settings/show-tutorial |