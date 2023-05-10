Feature:  Customer application Integration Management
# Operations for Customer application Integration Management information

  Background:
    Given User is logged in to unity

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4729")
  Scenario Outline: C2P Unity API :: Integration Management :: POST /integration/customer-application :: Add customer-application to the widget

    Then User adds customer application to the widget
      | i.widgetId                  | <i.widgetId>                  |
      | i.paymentStatusNotification | <i.paymentStatusNotification> |
      | i.applicationStatus         | <i.applicationStatus>         |
      | o.responseCode              | <o.responseCode>              |
      | o.status                    | <o.status>                    |
      | o.errorMessage              | <o.errorMessage>              |
      | o.errors                    | <o.errors>                    |

    Examples:
      | i.widgetId                       | i.paymentStatusNotification     | i.applicationStatus | o.responseCode | o.status  | o.errorMessage | o.errors                  |
      | 2c9acd9a86ea43d30186eeee78ac03e8 | ClickatellExtention-UpdateOrder | ACTIVATED           | 200            |           |                |                           |
      | non_existed                      | non_existed                     |                     | 404            | NOT_FOUND | URL /v2/widget | Widget does not exist, id |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4730")
  Scenario Outline: C2P Unity API :: Integration Management :: PUT /integration/customer-application/{applicationId} ::Update customer application details
    Then User adds customer application to the widget
      | i.widgetId                  | <i.widgetId>                  |
      | i.paymentStatusNotification | <i.paymentStatusNotification> |
      | i.applicationStatus         | <i.postApplicationStatus>     |
      | o.responseCode              | <o.responseCode>              |
      | o.status                    | <o.status>                    |
      | o.errorMessage              | <o.errorMessage>              |
      | o.errors                    | <o.errors>                    |

    Then User updates customer application to the widget
      | i.widgetId                  | <i.widgetId>                  |
      | i.paymentStatusNotification | <i.paymentStatusNotification> |
      | i.applicationStatus         | <i.applicationStatus>         |
      | o.responseCode              | <o.responseCode>              |
      | o.status                    | <o.status>                    |
      | o.errorMessage              | <o.errorMessage>              |
      | o.errors                    | <o.errors>                    |

    Examples:
      | i.widgetId                       | i.paymentStatusNotification     | i.postApplicationStatus | i.applicationStatus | o.responseCode | o.status  | o.errorMessage | o.errors                  |
      | 2c9acd9a86ea43d30186eeee78ac03e8 | ClickatellExtention-UpdateOrder | ACTIVATED               | DEACTIVATED         | 200            |           |                |                           |
      | non_existed                      | non_existed                     |                         |                     | 404            | NOT_FOUND | URL /v2/widget | Widget does not exist, id |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4731")
  Scenario Outline: C2P Unity API :: Integration Management :: DELETE /integration/customer-application/{applicationId} ::Delete customer application details

    Then User adds customer application to the widget
      | i.widgetId                  | <i.widgetId>                  |
      | i.applicationStatus         | <i.postApplicationStatus>     |
      | i.paymentStatusNotification | <i.paymentStatusNotification> |
      | o.responseCode              | <o.postResponseCode>          |
      | o.errorMessage              | <o.errorMessage>              |
      | o.errors                    | <o.errors>                    |

    Then User updates customer application to the widget
      | i.widgetId                  | <i.widgetId>                  |
      | i.paymentStatusNotification | <i.paymentStatusNotification> |
      | i.applicationStatus         | <i.putApplicationStatus>      |
      | o.responseCode              | <o.responseCode>              |
      | o.status                    | <o.status>                    |
      | o.errorMessage              | <o.errorMessage>              |
      | o.errors                    | <o.errors>                    |

    And User deletes customer-application from the widget
      | i.widgetId     | <i.widgetId>     |
      | o.responseCode | <o.responseCode> |
      | o.status       | <o.status>       |
      | o.errorMessage | <o.errorMessage> |
      | o.errors       | <o.errors>       |

    Examples:
      | i.widgetId                       | i.paymentStatusNotification     | i.postApplicationStatus | i.putApplicationStatus | o.postResponseCode | o.responseCode | o.status  | o.errorMessage | o.errors                  |
      | 2c9acd9a86ea43d30186eeee78ac03e8 | ClickatellExtention-UpdateOrder | ACTIVATED               | DEACTIVATED            | 200                | 200            |           |                |                           |
      | non_existed                      | non_existed                     |                         |                        | 404                | 404            | NOT_FOUND | URL /v2/widget | Widget does not exist, id |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4732")
  Scenario Outline: C2P Unity API :: Integration Management :: DELETE /integration/customer-application/all ::Delete all customer application details

    Then User adds customer application to the widget
      | i.widgetId                  | <i.widgetId>                  |
      | i.applicationStatus         | <i.postApplicationStatus>     |
      | i.paymentStatusNotification | <i.paymentStatusNotification> |
      | o.responseCode              | <o.postResponseCode>          |
      | o.errorMessage              | <o.errorMessage>              |
      | o.errors                    | <o.errors>                    |

    And User deletes all customer-applications from the widget
      | i.widgetId     | <i.widgetId>     |
      | o.responseCode | <o.responseCode> |
      | o.status       | <o.status>       |
      | o.errorMessage | <o.errorMessage> |
      | o.errors       | <o.errors>       |

    Examples:
      | i.widgetId                       | i.paymentStatusNotification     | i.postApplicationStatus | o.postResponseCode | o.responseCode | o.status  | o.errorMessage | o.errors                  |
      | 2c9acd9a86ea43d30186eeee78ac03e8 | ClickatellExtention-UpdateOrder | ACTIVATED               | 200                | 200            |           |                |                           |
      | non_existed                      | non_existed                     |                         | 404                | 404            | NOT_FOUND | URL /v2/widget | Widget does not exist, id |
