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

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4721")
  Scenario Outline: C2P Unity API :: Messages Configuration :: PUT /message-configurations :: update Watsapp and SMS widget information
    Then User creates widget for an account
      | i.widget       | valid       |
      | i.type         | CHAT_TO_PAY |
      | i.environment  | SANDBOX     |
      | o.responseCode | 200         |

    Then User updates configuration for newly created widget
      | i.widgetId              | <i.widgetId>              |
      | i.waPaymentTemplateId   | <i.waPaymentTemplateId>   |
      | i.waPaymentTemplateName | <i.waPaymentTemplateName> |
      | i.waReceiptTemplateId   | <i.waReceiptTemplateId>   |
      | i.waReceiptTemplateName | <i.waReceiptTemplateName> |
      | i.smsPaymentTemplate    | <i.smsPaymentTemplate>    |
      | i.smsReceiptTemplate    | <i.smsReceiptTemplate>    |
      | o.responseCode          | <o.responseCode>          |
      | o.updateTime            | <o.updateTime>            |
      | o.waMsgConfigComplete   | <o.waMsgConfigComplete>   |
      | o.smsMsgConfigComplete  | <o.smsMsgConfigComplete>  |
      | o.status                | <o.status>                |
      | o.errorMessage               | <o.errorMessage>               |
      | o.errors                | <o.errors>                |

    Examples:
      | i.widgetId  | i.waPaymentTemplateId | i.waPaymentTemplateName | i.waReceiptTemplateId | i.waReceiptTemplateName | i.smsPaymentTemplate | i.smsReceiptTemplate | o.responseCode | o.updateTime | o.waMsgConfigComplete | o.smsMsgConfigComplete | o.status  | o.errorMessage      | o.errors                                 |
      | valid       | valid                 | valid                   | valid                 | valid                   | valid                | valid                | 200            | TRUE         | true                  | true                   |           |                |                                          |
      | non_existed | valid                 | valid                   | valid                 | valid                   | valid                | valid                | 404            |              |                       |                        | NOT_FOUND | URL /v2/widget | Settings for widget does not exist, id = |

