Feature: API Keys Management

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4748")
  Scenario Outline: C2P Unity API :: API Keys Management :: GET /api-keys :: truth table

    Given User is logged in to unity
    And User gets 'API Keys Management'
      | i.widgetId     | <i.widgetId>     |
      | o.apiKey       | <o.apiKey>       |
      | o.responseCode | <o.responseCode> |
      | o.errors       | <o.errors>       |
      | o.path         | <o.path>         |

    Examples:
      | i.widgetId                       | o.apiKey                         | o.responseCode | o.errors  | o.path                       |
      | 2c9acd56862b180001862b679a390001 | 24afad176fe4446aa13ecc539758adb1 | 200            |           |                              |
      |                                  |                                  | 404            | NOT_FOUND | URL /v2/widget/null/api-keys |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4749")
  Scenario Outline: C2P Unity API :: API Keys Management :: POST /api-keys :: truth table

    Given User is logged in to unity
    And User gets 'API Keys Management'
      | i.widgetId     | <i.widgetId>     |
      | o.apiKey       | <o.apiKey>       |
      | o.responseCode | <o.responseCode> |
      | o.errors       | <o.errors>       |
      | o.path         | <o.path>         |

    Examples:
      | i.widgetId                       | o.apiKey                         | o.responseCode | o.errors  | o.path                       |
      | 2c9acd56862b180001862b679a390001 | 24afad176fe4446aa13ecc539758adb1 | 200            |           |                              |
      |                                  |                                  | 404            | NOT_FOUND | URL /v2/widget/null/api-keys |