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
      | o.id | o.name     | o.responseCode |
      | 1    | AMEX       | 200            |
      | 6    | DINERSCLUB | 200            |
      | 4    | DISCOVER   | 200            |
      | 5    | JCB        | 200            |
      | 3    | MASTERCARD | 200            |
      | 2    | VISA       | 200            |