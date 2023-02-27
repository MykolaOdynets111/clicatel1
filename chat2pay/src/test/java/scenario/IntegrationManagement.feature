Feature: # Enter feature name here
# Operations for Integration Management information

  Background:
    Given User is logged in to unity

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4723")
  Scenario Outline: C2P Unity API :: Integration Management :: GET /integration ::Get list of all integrations information for widget

    Then User gets integration information for widget
      | i.widgetId          | <i.widgetId>          |
      | o.responseCode      | <o.responseCode>      |
      | o.notificationUrls  | <o.notificationUrls>  |
      | o.integrationStatus | <o.integrationStatus> |
      | o.integrator        | <o.integrator>        |
      | o.status            | <o.status>            |
      | o.errorMessage      | <o.errorMessage>      |
      | o.errors            | <o.errors>            |

    Examples:
      | i.widgetId                       | o.responseCode | o.notificationUrls | o.integrationStatus | o.integrator | o.status  | o.errorMessage | o.errors                  |
      | 2c9ac7b285c8be190185d02a8a680012 | 200            | notNull            | ACTIVATED           | notNull      |           |                |                           |
      | non_existed                      | 404            |                    |                     |              | NOT_FOUND | URL /v2/widget | Widget does not exist, id |

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4724")
  Scenario Outline: C2P Unity API :: Integration Management :: GET /integration/{applicationId} ::Get details about application

    Then User gets details about application
      | i.widgetId          | <i.widgetId>          |
      | i.applicationId     | <i.applicationId>     |
      | o.responseCode      | <o.responseCode>      |
      | o.notificationUrls  | <o.notificationUrls>  |
      | o.integrationStatus | <o.integrationStatus> |
      | o.integrator        | <o.integrator>        |
      | o.status            | <o.status>            |
      | o.errorMessage      | <o.errorMessage>      |
      | o.errors            | <o.errors>            |


    Examples:
      | i.widgetId                       | i.applicationId                      | o.responseCode | o.notificationUrls | o.integrationStatus | o.integrator | o.status  | o.errorMessage | o.errors                  |
      | 2c9acd56862b180001862b679a390001 | fc689978-d649-4862-8834-97b3d32327c7 | 200            | notNull            | ACTIVATED           | notNull      |           |                |                           |
      | non_existed                      | none                                 | 404            |                    |                     |              | NOT_FOUND | URL /v2/widget | Widget does not exist, id |
