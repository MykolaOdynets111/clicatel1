Feature: Two-way Numbers Configuration

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4714")
  Scenario Outline: C2P Unity API :: Two-way Numbers Configuration :: GET /two-way-numbers :: truth table

    Given User is logged in to unity
    And User gets two-way numbers
      | i.widgetId     | <i.widgetId>     |
      | o.number       | <o.number>       |
      | o.default      | <o.default>  |
      | o.responseCode | <o.responseCode> |
      | o.errors       | <o.errors>       |
      | o.path         | <o.path>         |

    Examples:
      | i.widgetId                       | o.number     | o.default | o.responseCode | o.errors  | o.path                          |
      | 2c9acd56862b180001862b679a390001 | 447860092970 | true      | 200            |           |                                 |
      | 2c9aca99850b073801850d519f2200a6 |              |           | 200            |           |                                 |
      |                                  |              |           | 404            | NOT_FOUND | URL /v2/widget//two-way-numbers |
