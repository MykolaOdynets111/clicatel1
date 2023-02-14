Feature: Get settings for an account

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4712")
  Scenario Outline: C2P Unity API :: GET /settings :: truth table

    Given User is logged in to unity
    And User gets account settings
      | i.activationKey | <i.activationKey> |
      | o.responseCode  | <o.responseCode>  |
      | o.accountId     | <o.accountId>     |
      | o.showTutorial  | <o.showTutorial>  |
      | o.errors        | <o.errors>        |
      | o.path          | <o.path>          |

    Examples:
      | i.activationKey | o.responseCode | o.accountId                      | o.showTutorial | o.errors     | o.path               |
      | token           | 200            | 2c9acc3078b5cfe80178db93bd871a58 | false          |              |                      |
      | test            | 401            |                                  |                | Unauthorized | /v2/account/settings |
      | " "             | 401            |                                  |                | Unauthorized | /v2/account/settings |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4713")
  Scenario Outline: C2P Unity API :: PUT /show-tutorial :: truth table

    Given User is logged in to unity
    And User updates account settings
      | i.activationKey       | <i.activationKey> |
      | i.showTutorial        | <i.showTutorial>  |
      | o.responseCode        | <o.responseCode>  |
      | o.updatedShowTutorial | <o.showTutorial>  |
      | o.errors              | <o.errors>        |
      | o.path                | <o.path>          |

    Examples:
      | i.activationKey | i.showTutorial | o.responseCode | o.showTutorial | o.errors     | o.path                             |
      | token           | true           | 200            | true           |              |                                    |
      | token           | false          | 200            | false          |              |                                    |
      | test            |                | 401            |                | Unauthorized | /v2/account/settings/show-tutorial |
      | " "             |                | 401            |                | Unauthorized | /v2/account/settings/show-tutorial |