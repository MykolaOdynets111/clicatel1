Feature:  Clickatell product Integration Management
# Operations for Clickatell product Integration Management information

  Background:
    Given User is logged in to unity

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4726")
  Scenario Outline: C2P Unity API :: Integration Management :: POST /integration/clickatell-product :: Add  clickatell-product application

    Then User adds clickatell-product application to the widget
      | i.widgetId          | <i.widgetId>          |
      | i.applicationId     | <i.applicationId>     |
      | i.applicationStatus | <i.applicationStatus> |
      | o.responseCode      | <o.responseCode>      |
      | o.status            | <o.status>            |
      | o.errorMessage      | <o.errorMessage>      |
      | o.errors            | <o.errors>            |

    Examples:
      | i.widgetId                       | i.applicationId | i.applicationStatus | o.responseCode | o.status  | o.errorMessage | o.errors                  |
      | 2c9acd56862b180001862b679a390001 | 2               | ACTIVATED           | 200            |           |                |                           |
      | non_existed                      | non_existed     |                     | 404            | NOT_FOUND | URL /v2/widget | Widget does not exist, id |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4725")
  Scenario Outline: C2P Unity API :: Integration Management :: PUT /integration/clickatell-product ::Update application details
    Then User adds clickatell-product application to the widget
      | i.widgetId          | <i.widgetId>              |
      | i.applicationId     | <i.applicationId>         |
      | i.applicationStatus | <i.postApplicationStatus> |
      | o.responseCode      | <o.postResponseCode>      |
      | o.errorMessage      | <o.errorMessage>          |
      | o.errors            | <o.errors>                |


    And User updates clickatell-product application to the widget
      | i.widgetId          | <i.widgetId>          |
      | i.applicationId     | <i.applicationId>     |
      | i.applicationStatus | <i.applicationStatus> |
      | o.responseCode      | <o.responseCode>      |
      | o.status            | <o.status>            |
      | o.errorMessage      | <o.errorMessage>      |
      | o.errors            | <o.errors>            |

    Examples:
      | i.widgetId                       | i.applicationId | i.postApplicationStatus | i.applicationStatus | o.postResponseCode | o.responseCode | o.status    | o.errorMessage | o.errors                                          |
      | 2c9acd56862b180001862b679a390001 | 2               | ACTIVATED               | DEACTIVATED         | 200                | 200            |             |                |                                                   |
      | 2c9acd56862b180001862b679a390001 | 2               | DEACTIVATED             | DEACTIVATED         | 200                | 400            | BAD_REQUEST | URL /v2/widget | Current integration status is already Deactivated |
      | non_existed                      | non_existed     |                         |                     | 404                | 404            | NOT_FOUND   | URL /v2/widget | Widget does not exist, id                         |


  @TestCaseId("https://jira.clickatell.com/browse/C2P-4727")
  Scenario Outline: C2P Unity API :: Integration Management :: GET /integration/clickatell-product{applicationId} ::Get details about clickatell-product application
    Then User adds clickatell-product application to the widget
      | i.widgetId          | <i.widgetId>              |
      | i.applicationId     | <i.applicationId>         |
      | i.applicationStatus | <i.postApplicationStatus> |
      | o.responseCode      | <o.postResponseCode>      |
      | o.errorMessage      | <o.errorMessage>          |
      | o.errors            | <o.errors>                |


    And User gets clickatell-product application for the widget
      | i.widgetId      | <i.widgetId>      |
      | i.applicationId | <i.applicationId> |
      | o.body          | <o.body>          |
      | o.responseCode  | <o.responseCode>  |
      | o.status        | <o.status>        |
      | o.errorMessage  | <o.errorMessage>  |
      | o.errors        | <o.errors>        |

    Examples:
      | i.widgetId                       | i.applicationId | i.postApplicationStatus | o.postResponseCode | o.responseCode | o.body | o.status    | o.errorMessage | o.errors                                                                                 |
      | 2c9acd56862b180001862b679a390001 | 2               | ACTIVATED               | 200                | 200            | true   |             |                |                                                                                          |
      | 2c9acd56862b180001862b679a390001 | 2               | DEACTIVATED             | 200                | 400            | false  | BAD_REQUEST | URL /v2/widget | Statuses don't match in the database: false and from the request to the integrator: true |
      | non_existed                      | non_existed     |                         | 404                | 404            |        | NOT_FOUND   | URL /v2/widget | Widget does not exist, id                                                                |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4728")
  Scenario Outline: C2P Unity API :: Integration Management :: DETELE /integration/clickatell-product/{applicationId} ::Delete clickatell-product application
    Then User adds clickatell-product application to the widget
      | i.widgetId          | <i.widgetId>              |
      | i.applicationId     | <i.applicationId>         |
      | i.applicationStatus | <i.postApplicationStatus> |
      | o.responseCode      | <o.postResponseCode>      |
      | o.errorMessage      | <o.errorMessage>          |
      | o.errors            | <o.errors>                |


    And User deletes clickatell-product application from the widget
      | i.widgetId      | <i.widgetId>      |
      | i.applicationId | <i.applicationId> |
      | o.applicationId | <i.applicationId> |
      | o.responseCode  | <o.responseCode>  |
      | o.status        | <o.status>        |
      | o.errorMessage  | <o.errorMessage>  |
      | o.errors        | <o.errors>        |

    Examples:
      | i.widgetId                       | i.applicationId | i.postApplicationStatus | o.postResponseCode | o.responseCode | o.status  | o.errorMessage | o.errors                  |
      | 2c9acd56862b180001862b679a390001 | 2               | DEACTIVATED             | 200                | 200            |           |                |                           |
      | non_existed                      | non_existed     |                         | 404                | 404            | NOT_FOUND | URL /v2/widget | Widget does not exist, id |
