Feature: Order Management System

  Background:
    Given User is logged in to unity
    When User creates new widget

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4733")
  Scenario Outline: C2P Unity API :: Integration Management :: POST /integration/oms :: truth table

    Then User adds order management system to the widget
      | i.widgetId                  | <i.widgetId>                  |
      | i.paymentStatusNotification | <i.paymentStatusNotification> |
      | i.applicationType           | <i.applicationType>           |
      | o.responseCode              | <o.responseCode>              |
      | o.status                    | <o.status>                    |
      | o.errorMessage              | <o.errorMessage>              |
      | o.errors                    | <o.errors>                    |

    Examples:
      | i.widgetId | i.paymentStatusNotification           | i.applicationType | o.responseCode | o.status  | o.errorMessage | o.errors                  |
      | valid      | https://en60qjud1gl72.x.pipedream.net | OMS               | 200            |           |                |                           |
      | invalid    | non_existed                           |                   | 404            | NOT_FOUND | URL /v2/widget | Widget does not exist, id |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4734")
  Scenario Outline: C2P Unity API :: Integration Management :: PUT /integration/oms/{applicationId} :: truth table

    When User adds order management system to the widget
      | i.widgetId                  | <i.widgetId>                  |
      | i.paymentStatusNotification | <i.paymentStatusNotification> |
      | i.applicationType           | <i.applicationType>           |
      | o.responseCode              | <o.responseCode>              |
      | o.status                    | <o.status>                    |
      | o.errorMessage              | <o.errorMessage>              |
      | o.errors                    | <o.errors>                    |

    Then User puts order management system to the widget
      | i.widgetId                  | <i.widgetId>                  |
      | i.paymentStatusNotification | <i.paymentStatusNotification> |
      | i.applicationType           | <i.applicationType>           |
      | o.responseCode              | <o.responseCode>              |
      | o.status                    | <o.status>                    |
      | o.errorMessage              | <o.errorMessage>              |
      | o.errors                    | <o.errors>                    |

    Examples:
      | i.widgetId | i.paymentStatusNotification           | i.applicationType | o.responseCode | o.status  | o.errorMessage | o.errors                  |
      | valid      | https://en60qjud1gl72.x.pipedream.net | OMS               | 200            |           |                |                           |
      | invalid    | non_existed                           |                   | 404            | NOT_FOUND | URL /v2/widget | Widget does not exist, id |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4735")
  Scenario Outline: C2P Unity API :: DELETE /integration/oms/{applicationId} :: truth table

    When User adds order management system to the widget
      | i.widgetId                  | <i.widgetId>                  |
      | i.paymentStatusNotification | <i.paymentStatusNotification> |
      | i.applicationType           | <i.applicationType>           |
      | o.responseCode              | <o.responseCode>              |
      | o.status                    | <o.status>                    |
      | o.errorMessage              | <o.errorMessage>              |
      | o.errors                    | <o.errors>                    |

    Then User deletes order management system
      | i.widgetId                  | <i.widgetId>                  |
      | i.paymentStatusNotification | <i.paymentStatusNotification> |
      | i.applicationType           | <i.applicationType>           |
      | o.responseCode              | <o.responseCode>              |
      | o.status                    | <o.status>                    |
      | o.errorMessage              | <o.errorMessage>              |
      | o.errors                    | <o.errors>                    |

    Examples:
      | i.widgetId | i.paymentStatusNotification           | i.applicationType | o.responseCode | o.status  | o.errorMessage | o.errors                  |
      | valid      | https://en60qjud1gl72.x.pipedream.net | OMS               | 200            |           |                |                           |
      | invalid    | non_existed                           |                   | 404            | NOT_FOUND | URL /v2/widget | Widget does not exist, id |