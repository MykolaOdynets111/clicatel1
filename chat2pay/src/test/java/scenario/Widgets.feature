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

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4309")
  Scenario Outline: C2P Unity API :: PUT /widget/{widgetId} :: update widget

    Then User creates widget for an account
      | i.widget       | valid       |
      | i.type         | CHAT_TO_PAY |
      | i.environment  | SANDBOX     |
      | o.responseCode | 200         |

    Then User updates newly created widget
      | i.widgetId          | <i.widgetId>          |
      | i.name              | <i.name>              |
      | i.status            | <i.status>            |
      | i.configStatus_id   | <i.configStatus_id>   |
      | i.configStatus_name | <i.configStatus_name> |
      | i.environment       | <i.environment>       |
      | o.responseCode      | <o.responseCode>      |
      | o.updateTime        | <o.updateTime>        |
      | o.status            | <o.status>            |
      | o.errorMessage      | <o.errorMessage>      |
      | o.errors            | <o.errors>            |

    Then User delete newly created widget
    Examples:
      | i.widgetId   | i.name          | i.status   | i.configStatus_id | i.configStatus_name | i.environment | o.responseCode | o.updateTime | o.status    | o.errorMessage                 | o.errors                          |
      | valid        | My first widget | CONFIGURED | 1                 | Configured          | SANDBOX       | 200            | TRUE         |             |                                |                                   |
      | non_existed  | My first widget | CONFIGURED | 1                 | Configured          | SANDBOX       | 404            |              | NOT_FOUND   | URL /v2/widget                 | Widget does not exist, id =       |
      | wrong_status | My first widget | xxx        | 1                 | Configured          | SANDBOX       | 400            |              | BAD_REQUEST | Request failed for /v2/widget/ | Invalid value for WidgetStatus    |
      | wrong_env    | My first widget | CONFIGURED | 1                 | Configured          | xxx           | 400            |              | BAD_REQUEST | Request failed for /v2/widget/ | Invalid value for EnvironmentMode |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4313")
  Scenario Outline: C2P Unity API :: GET /widget/{widgetId} :: get widget by id

    Then User creates widget for an account
      | i.widget       | valid       |
      | i.type         | CHAT_TO_PAY |
      | i.environment  | SANDBOX     |
      | o.responseCode | 200         |

    Then User updates newly created widget
      | i.widgetId          | <i.widgetId>          |
      | i.name              | <i.name>              |
      | i.status            | <i.status>            |
      | i.configStatus_id   | <i.configStatus_id>   |
      | i.configStatus_name | <i.configStatus_name> |
      | i.environment       | <i.environment>       |
      | o.responseCode      | <o.responseCode>      |
      | o.updateTime        | <o.updateTime>        |
      | o.status            | <o.status>            |
      | o.errorMessage      | <o.errorMessage>      |
      | o.errors            | <o.errors>            |

    Then User gets newly created widget
      | i.widgetId          | <i.widgetId>          |
      | o.name              | <i.name>              |
      | o.status            | <i.status>            |
      | o.configStatus_id   | <i.configStatus_id>   |
      | o.configStatus_name | <i.configStatus_name> |
      | o.environment       | <i.environment>       |
      | o.responseCode      | <o.responseCode>      |
      | o.updateTime        | <o.updateTime>        |
      | o.errorMessage      | <o.errorMessage>      |
      | o.errors            | <o.errors>            |

    Then User delete newly created widget
    Examples:
      | i.widgetId   | i.name          | i.status   | i.configStatus_id | i.configStatus_name | i.environment | o.responseCode | o.updateTime | o.status    | o.errorMessage                 | o.errors                          |
      | valid        | My first widget | CONFIGURED | 1                 | Configured          | SANDBOX       | 200            | TRUE         |             |                                |                                   |
      | non_existed  | My first widget | CONFIGURED | 1                 | Configured          | SANDBOX       | 404            |              | NOT_FOUND   | URL /v2/widget                 | Widget does not exist, id =       |