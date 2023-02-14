Feature: Two-way Numbers Configuration

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4739")
  Scenario Outline: C2P Unity API :: Two-way Numbers Configuration :: GET /two-way-numbers :: truth table

    Given User is logged in to unity
    And User gets two-way numbers
      | i.widgetId     | <i.widgetId>     |
      | o.number       | <o.number>       |
      | o.default      | <o.default>      |
      | o.responseCode | <o.responseCode> |
      | o.errors       | <o.errors>       |
      | o.path         | <o.path>         |

    Examples:
      | i.widgetId                       | o.number     | o.default | o.responseCode | o.errors  | o.path                              |
      | 2c9acd56862b180001862b679a390001 | 447860092970 | true      | 200            |           |                                     |
      | 2c9acd56862b180001862b679a390001 | 491771783647 | false     | 200            |           |                                     |
      | 2c9aca99850b073801850d519f2200a6 | " "          |           | 200            |           |                                     |
      |                                  |              |           | 404            | NOT_FOUND | URL /v2/widget/null/two-way-numbers |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4740")
  Scenario Outline: C2P Unity API :: Two-way Numbers Configuration :: POST /two-way-numbers :: truth table

    Given User is logged in to unity
    And User updates two-way numbers
      | i.widgetId       | <i.widgetId>       |
      | o.numbers        | <o.numbers>        |
      | o.defaultNumbers | <o.defaultNumbers> |
      | o.responseCode   | <o.responseCode>   |
      | o.errors         | <o.errors>         |
      | o.path           | <o.path>           |

    Examples:
      | i.widgetId                       | o.numbers                  | o.defaultNumbers | o.responseCode | o.errors  | o.path                              |
      | 2c9ac7b285c8be190185ddd9cd9f002b | " "                        | " "              | 200            |           |                                     |
      | 2c9ac7b285c8be190185ddd9cd9f002b | 447938562268               | 447938562268     | 200            |           |                                     |
      | 2c9ac7b285c8be190185ddd9cd9f002b | 447938562268, 441138562268 | 447938562268     | 200            |           |                                     |
      |                                  |                            |                  | 404            | NOT_FOUND | URL /v2/widget/null/two-way-numbers |