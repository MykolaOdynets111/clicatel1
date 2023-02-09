Feature: Channels Management

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4714")
  Scenario Outline: C2P Unity API :: POST /link-channels :: truth table

    Given User is logged in to unity
    And User links channel to the widget
      | i.activationKey             | <i.activationKey>             |
      | i.widgetId                  | <i.widgetId>                  |
      | i.smsOmniIntegrationId      | <i.smsOmniIntegrationId>      |
      | i.whatsappOmniIntegrationId | <i.whatsappOmniIntegrationId> |
      | o.responseCode              | <o.responseCode>              |
      | o.errors                    | <o.errors>                    |
      | o.path                      | <o.path>                      |

    Examples:
      | i.activationKey | i.widgetId                       | i.smsOmniIntegrationId           | i.whatsappOmniIntegrationId      | o.responseCode | o.errors     | o.path                                                        |
      | token           | 2c9ac7b285c8be190185d02a8a680012 | 2d3a731733dd475f953a22fda647f040 | 2c9acc3078b5cfe80178db9d9c991a79 | 202            |              |                                                               |
      | token           |                                  | 2d3a731733dd475f953a22fda647f040 | 2c9acc3078b5cfe80178db9d9c991a79 | 404            | NOT_FOUND    | URL /v2/widget/null/link-channels                             |
      | token           | 2c9ac7b285c8be190185d02a8a680012 |                                  | 2c9acc3078b5cfe80178db9d9c991a79 | 202            |              |                                                               |
      | token           | 2c9ac7b285c8be190185d02a8a680012 | 2d3a731733dd475f953a22fda647f040 |                                  | 202            |              |                                                               |
      | token           | test                             | 2d3a731733dd475f953a22fda647f040 | 2c9acc3078b5cfe80178db9d9c991a79 | 404            | NOT_FOUND    | URL /v2/widget/test/link-channels                             |
      | token           | 2c9ac7b285c8be190185d02a8a680012 | test                             | 2c9acc3078b5cfe80178db9d9c991a79 | 404            | NOT_FOUND    | URL /v2/widget/2c9ac7b285c8be190185d02a8a680012/link-channels |
      | token           | 2c9ac7b285c8be190185d02a8a680012 | 2d3a731733dd475f953a22fda647f040 | test                             | 404            | NOT_FOUND    | URL /v2/widget/2c9ac7b285c8be190185d02a8a680012/link-channels |
      | test            |                                  |                                  |                                  | 401            | Unauthorized | /v2/widget/null/link-channels                                 |
      | " "             |                                  |                                  |                                  | 401            | Unauthorized | /v2/widget/null/link-channels                                 |