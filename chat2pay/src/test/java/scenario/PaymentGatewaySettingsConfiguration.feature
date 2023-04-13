Feature: Payment Gateway Settings Configuration

  Background:
    Given User is logged in to unity
    When User creates new widget

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4741")
  Scenario Outline: C2P Unity API :: Payment Gateway Settings Configuration :: GET /payment-gateway-settings :: truth table

    And User sets up 'Unified Payment Setting' for widget
    Then User gets 'Unified Payment Gateway Settings'
      | i.widgetId         | <i.widgetId>         |
      | o.paymentGatewayId | <o.paymentGatewayId> |
      | o.locale           | <o.locale>           |
      | o.country          | <o.country>          |
      | o.defaultCurrency  | <o.defaultCurrency>  |
      | o.responseCode     | <o.responseCode>     |
      | o.errors           | <o.errors>           |
      | o.path             | <o.path>             |

    Examples:
      | i.widgetId | o.paymentGatewayId | o.locale         | o.country | o.defaultCurrency | o.responseCode | o.errors  | o.path                                       |
      | valid      | 1                  | English (Canada) | Canada    | South Africa      | 200            |           |                                              |
      | invalid    |                    |                  |           |                   | 404            | NOT_FOUND | URL /v2/widget/null/payment-gateway-settings |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4745")
  Scenario Outline: C2P Unity API :: Payment Gateway Settings Configuration :: POST /payment-gateway-settings/1 :: truth table

    And User sets up 'Secure Acceptance Setting' for widget
    Then User gets 'Secure Acceptance Settings'
      | i.widgetId         | <i.widgetId>         |
      | o.paymentGatewayId | <o.paymentGatewayId> |
      | o.defaultCurrency  | <o.defaultCurrency>  |
      | o.responseCode     | <o.responseCode>     |
      | o.errors           | <o.errors>           |
      | o.path             | <o.path>             |

    Examples:
      | i.widgetId | o.paymentGatewayId | o.defaultCurrency | o.responseCode | o.errors  | o.path                                       |
      | valid      | 1                  | South Africa      | 200            |           |                                              |
      | invalid    |                    |                   | 404            | NOT_FOUND | URL /v2/widget/null/payment-gateway-settings |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4746")
  Scenario Outline: C2P Unity API :: Payment Gateway Settings Configuration :: POST /payment-gateway-settings/2 :: truth table

    And User sets up 'Unified Payment Setting' for widget
    Then User gets 'Unified Payment Gateway Settings'
      | i.widgetId         | <i.widgetId>         |
      | o.paymentGatewayId | <o.paymentGatewayId> |
      | o.locale           | <o.locale>           |
      | o.country          | <o.country>          |
      | o.defaultCurrency  | <o.defaultCurrency>  |
      | o.responseCode     | <o.responseCode>     |
      | o.errors           | <o.errors>           |
      | o.path             | <o.path>             |

    Examples:
      | i.widgetId | o.paymentGatewayId | o.locale         | o.country | o.defaultCurrency | o.responseCode | o.errors  | o.path                                       |
      | valid      | 1                  | English (Canada) | Canada    | South Africa      | 200            |           |                                              |
      | invalid    |                    |                  |           |                   | 404            | NOT_FOUND | URL /v2/widget/null/payment-gateway-settings |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4742")
  Scenario Outline: C2P Unity API :: Payment Gateway Settings Configuration :: DELETE /payment-gateway-settings/{paymentGatewaySettingsId} :: truth table

    And User sets up 'Unified Payment Setting' for widget
    Then User deletes 'Payment Gateway Settings'
      | i.widgetId     | <i.widgetId>     |
      | o.responseCode | <o.responseCode> |
      | o.errors       | <o.errors>       |
      | o.path         | <o.path>         |

    Examples:
      | i.widgetId | o.responseCode | o.errors  | o.path                                       |
      | valid      | 200            |           |                                              |
      | invalid    | 404            | NOT_FOUND | URL /v2/widget/null/payment-gateway-settings |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4747")
  Scenario Outline: C2P Unity API :: Payment Gateway Settings Configuration :: POST /payment-gateway-settings/logo :: truth table

    And User sets up 'Unified Payment Setting' for widget
    Then User posts 'Payment Gateway Logo'
      | i.logo         | <i.logo>         |
      | o.responseCode | <o.responseCode> |
      | o.errors       | <o.errors>       |
      | o.path         | <o.path>         |

    Examples:
      | i.logo  | o.responseCode | o.errors  | o.path                                            |
      | valid   | 200            |           |                                                   |
      | invalid | 404            | NOT_FOUND | URL /v2/widget/null/payment-gateway-settings/logo |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4743")
  Scenario Outline: C2P Unity API :: Payment Gateway Settings Configuration :: GET /payment-gateway-settings/{paymentGatewaySettingsId}/logo :: truth table

    When User posts 'Payment Gateway Logo'
      | i.logo         | <i.logo>         |
      | o.responseCode | <o.responseCode> |
      | o.errors       | <o.errors>       |
      | o.path         | <o.path>         |

    And User sets up 'Unified Payment Setting' for widget
    Then User gets 'Payment Gateway Logo'
      | i.logo         | <i.logo>         |
      | o.responseCode | <o.responseCode> |
      | o.errors       | <o.errors>       |
      | o.path         | <o.path>         |

    Examples:
      | i.logo  | o.responseCode | o.errors  | o.path                                       |
      | valid   | 200            |           |                                              |
      | invalid | 404            | NOT_FOUND | URL /v2/widget/null/payment-gateway-settings |