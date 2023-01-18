Feature: Get Chat 2 pay configuration
#This API returns the configuration data for the configured Chat 2 Pay product.

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4433")
  Scenario Outline: chat2pay-service :: GET /chat-2-pay :: User can get Chat 2 Pay configuration

    Given User is logged in to unity
    And User gets widgetId for UC form
    And User get the C2P configuration
      | activationKey          | <i.activationKey>          |
      | statusCode             | <o.statusCode>             |
      | whatsappChannelEnabled | <o.whatsappChannelEnabled> |
      | smsChannelEnabled      | <o.smsChannelEnabled>      |
      | apiKey                 | <o.apiKey>                 |
      | supportedCurrencies    | <o.supportedCurrencies>    |
      | environment            | <o.environment>            |
      | integrations           | <o.integrations>           |
      | error                  | <o.error>                  |
      | path                   | <o.path>                   |

    Examples:
      | i.activationKey                  | o.statusCode | o.whatsappChannelEnabled | o.smsChannelEnabled | o.apiKey                         | o.supportedCurrencies | o.environment | o.integrations | o.error      | o.path         |
      | e9ac1ace130a465a8fb6f8eea293589e | 200          | FALSE                    | TRUE                | e9ac1ace130a465a8fb6f8eea293589e | 2                     | SANDBOX       | 2              |              |                |
      | qwerty                           | 401          |                          |                     |                                  |                       |               |                | Unauthorized | /api/v2/config |
      | " "                              | 401          |                          |                     |                                  |                       |               |                | Unauthorized | /api/v2/config |
      | c80a79a9857040acafbea2774008ed1b | 200          | FALSE                    | TRUE                | c80a79a9857040acafbea2774008ed1b | 1                     | SANDBOX       | 1              |              |                |
      | test                             | 401          |                          |                     |                                  |                       |               |                | Unauthorized | /api/v2/config |
      | " "                              | 401          |                          |                     |                                  |                       |               |                | Unauthorized | /api/v2/config |
