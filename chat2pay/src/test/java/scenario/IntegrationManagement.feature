Feature:  Integration Management
# Operations for Integration Management information

  Background:
    Given User is logged in to unity

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4723")
  Scenario Outline: C2P Unity API :: Integration Management :: GET /integration ::Get list of all integrations information for widget

    Then User gets integration information for widget
      | i.widgetId                  | <i.widgetId>                  |
      | o.responseCode              | <o.responseCode>              |
      | o.name                      | <o.name>                      |
      | o.paymentStatusNotification | <o.paymentStatusNotification> |
      | o.integrationStatus         | <o.integrationStatus>         |
      | o.integratorType            | <o.integratorType>            |
      | o.status                    | <o.status>                    |
      | o.errorMessage              | <o.errorMessage>              |
      | o.errors                    | <o.errors>                    |

    Examples:
      | i.widgetId                       | o.responseCode | o.name | o.paymentStatusNotification           | o.integrationStatus | o.integratorType | o.status  | o.errorMessage | o.errors                  |
      | 2c9acd56862b180001862b679a390001 | 200            | Salesforce   | https://en60qjud1gl72.x.pipedream.net | ACTIVATED           | OMS      |           |                |                           |
      | non_existed                      | 404            |        |                                       |                     |                  | NOT_FOUND | URL /v2/widget | Widget does not exist, id |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4724")
  Scenario Outline: C2P Unity API :: Integration Management :: GET /integration/{applicationId} ::Get details about application

    Then User gets details about application
      | i.widgetId                  | <i.widgetId>                  |
      | i.applicationId             | <i.applicationId>             |
      | o.responseCode              | <o.responseCode>              |
      | o.paymentStatusNotification | <o.paymentStatusNotification> |
      | o.integrationStatus         | <o.integrationStatus>         |
      | o.integratorType            | <o.integratorType>            |
      | o.status                    | <o.status>                    |
      | o.errorMessage              | <o.errorMessage>              |
      | o.errors                    | <o.errors>                    |

    Examples:
      | i.widgetId                       | i.applicationId | o.responseCode | o.paymentStatusNotification           | o.integrationStatus | o.integratorType | o.status  | o.errorMessage | o.errors                      |
      | 2c9acd56862b180001862b679a390001 | 1               | 200            | https://en60qjud1gl72.x.pipedream.net | ACTIVATED           | OMS              |           |                |                               |
      | non_existed                      | non_existed     | 404            |                                       |                     |                  | NOT_FOUND | URL /v2/widget | Widget does not exist, id     |
      | 2c9acd56862b180001862b679a390001 | non_existed     | 404            |                                       |                     |                  | NOT_FOUND | URL /v2/widget | Integrator with applicationId |
