Feature: Widgets operations
# CRUD operations for Widgets

  Background:
    Given User is logged in to unity

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4336")
  Scenario Outline: C2P :: Unity API :: Widget Configuration :: POST :: Create widget for an account

    Then User creates widget for an account
      | i.widget       | <i.widget>       |
      | i.type         | <i.type>         |
      | i.environment  | <i.environment>  |
      | o.responseCode | <o.responseCode> |
      | o.errors       | <o.errors>       |
      | o.errorMessage | <o.errorMessage> |

    Then User delete newly created widget


    Examples:
      | i.widget   | i.type      | i.environment | o.responseCode | o.errorMessage                | o.errors                          |
      | valid      | CHAT_TO_PAY | SANDBOX       | 200            |                               |                                   |
      | valid      | CHAT_TO_PAY | PRODUCTION    | 200            |                               |                                   |
      | nonexisted | CHAT_TO_PAY | xxx           | 400            | Request failed for /v2/widget | Invalid value for EnvironmentMode |
      | nonexisted | xxx         | SANDBOX       | 400            | Request failed for /v2/widget | Invalid value for WidgetType      |

