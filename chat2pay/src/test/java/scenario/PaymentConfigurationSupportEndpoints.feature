Feature: Payment Configuration Support Endpoints

  Background:
    Given User is logged in to unity

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4751")
  Scenario Outline: C2P Unity API :: Payment Configuration Support Endpoints :: GET /billing-type :: truth table

    And User gets 'Billing Type'
      | o.id           | <o.id>           |
      | o.name         | <o.name>         |
      | o.responseCode | <o.responseCode> |

    Examples:
      | o.id | o.name  | o.responseCode |
      | 0    | NONE    | 200            |
      | 1    | FULL    | 200            |
      | 2    | PARTIAL | 200            |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4752")
  Scenario Outline: C2P Unity API :: Payment Configuration Support Endpoints :: GET /card-network :: truth table Edit

    And User gets 'Card Network'
      | o.id           | <o.id>           |
      | o.name         | <o.name>         |
      | o.responseCode | <o.responseCode> |

    Examples:
      | o.id | o.name     | o.responseCode |
      | 1    | AMEX       | 200            |
      | 6    | DINERSCLUB | 200            |
      | 4    | DISCOVER   | 200            |
      | 5    | JCB        | 200            |
      | 3    | MASTERCARD | 200            |
      | 2    | VISA       | 200            |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4753")
  Scenario Outline: C2P Unity API :: Payment Configuration Support Endpoints :: GET /country :: truth table

    And User gets 'Country'
      | o.id           | <o.id>           |
      | o.name         | <o.name>         |
      | o.responseCode | <o.responseCode> |

    Examples:
      | o.id | o.name  | o.responseCode |
      | 40   | Canada  | 200            |
      | 177  | Poland  | 200            |
      | 233  | Ukraine | 200            |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4754")
  Scenario Outline: C2P Unity API :: Payment Configuration Support Endpoints :: GET /currency :: truth table

    And User gets 'Currency'
      | i.paymentIntegrationType | <i.paymentIntegrationType> |
      | o.id                     | <o.id>                     |
      | o.iso                    | <o.iso>                    |
      | o.name                   | <o.name>                   |
      | o.symbol                 | <o.symbol>                 |
      | o.responseCode           | <o.responseCode>           |

    Examples:
      | i.paymentIntegrationType | o.id | o.iso | o.name                                  | o.symbol | o.responseCode |
      | Secure Acceptance        | 11   | BAM   | Bosnia and Herzegovina convertible mark | KM       | 200            |
      | Unified Checkout         | 144  | USD   | United States dollar                    | $        | 200            |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4755")
  Scenario Outline: C2P Unity API :: Payment Configuration Support Endpoints :: GET /integration-type :: truth table

    And User gets 'Integration Type'
      | o.id           | <o.id>           |
      | o.name         | <o.name>         |
      | o.responseCode | <o.responseCode> |

    Examples:
      | o.id | o.name            | o.responseCode |
      | 1    | Secure Acceptance | 200            |
      | 2    | Unified Checkout  | 200            |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4756")
  Scenario Outline: C2P Unity API :: Payment Configuration Support Endpoints :: GET /locale :: truth table

    And User gets widgetId for UC form
    And User gets 'Locale'
      | o.id           | <o.id>           |
      | o.name         | <o.name>         |
      | o.responseCode | <o.responseCode> |

    Examples:
      | o.id | o.name                  | o.responseCode |
      | 1    | English (Great Britain) | 200            |
      | 5    | Spanish (Traditional)   | 200            |
      | 7    | Portuguese (Brazil)     | 200            |