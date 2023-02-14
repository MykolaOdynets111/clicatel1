Feature: Messages Configuration operations
# Operations for Messages Configuration

  Background:
    Given User is logged in to unity

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4720")
  Scenario Outline: C2P Unity API :: Messages Configuration :: GET /message-configurations :: get Watsapp and SMS widget information
    Then User creates widget for an account
      | i.widget       | valid       |
      | i.type         | CHAT_TO_PAY |
      | i.environment  | SANDBOX     |
      | o.responseCode | 200         |

    Then User gets configuration for newly created widget
      | i.widgetId     | <i.widgetId>     |
      | o.responseCode | <o.responseCode> |
      | o.updateTime   | <o.updateTime>   |
      | o.errorMessage | <o.errorMessage> |
      | o.errors       | <o.errors>       |

    Then User delete newly created widget


    Examples:
      | i.widgetId  | o.responseCode | o.updateTime | o.errorMessage | o.errors                           |
      | valid       | 200            | TRUE         | 0              | 0                                  |
      | non_existed | 404            | 0            | URL /v2/widget | Settings for widget does not exist |