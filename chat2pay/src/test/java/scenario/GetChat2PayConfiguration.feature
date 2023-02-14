Feature: Get Chat 2 pay configuration
#This API returns the configuration data for the configured Chat 2 Pay product.

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4695")
  Scenario Outline: c2p-Widget-Payment-Service :: GET /config :: Get Chat 2 Pay configuration

    Given User is logged in to unity
    And User get the C2P configuration
      | i.activationKey                 | <i.activationKey>                 |
      | o.responseCode                  | <o.responseCode>                  |
      | o.whatsappChannelEnabled        | <o.whatsappChannelEnabled>        |
      | o.smsChannelEnabled             | <o.smsChannelEnabled>             |
      | o.apiKey                        | <o.apiKey>                        |
      | o.environment                   | <o.environment>                   |
      | o.supportedCurrency.id          | <o.supportedCurrency.id>          |
      | o.supportedCurrencies.iso       | <o.supportedCurrencies.iso>       |
      | o.supportedCurrencies.name      | <o.supportedCurrencies.name>      |
      | o.supportedCurrencies.symbol    | <o.supportedCurrencies.symbol>    |
      | o.supportedCurrencies.isDefault | <o.supportedCurrencies.isDefault> |
      | o.integration.id                | <o.integration.id>                |
      | o.integration.name              | <o.integration.name>              |
      | o.integration.status            | <o.integration.status>            |
      | o.integration.type              | <o.integration.type>              |
      | o.errors                        | <o.errors>                        |
      | o.path                          | <o.path>                          |

    Examples:
      | i.activationKey                  | o.responseCode | o.whatsappChannelEnabled | o.smsChannelEnabled | o.apiKey                         | o.supportedCurrency.id | o.supportedCurrencies.iso | o.supportedCurrencies.name | o.supportedCurrencies.symbol | o.supportedCurrencies.isDefault | o.environment | o.integration.id                     | o.integration.name | o.integration.status | o.integration.type | o.errors     | o.path         |
      | e9ac1ace130a465a8fb6f8eea293589e | 200            | false                    | true                | e9ac1ace130a465a8fb6f8eea293589e | 27                     | CAD                       | Canadian dollar            | $                            | false                           | SANDBOX       | d1aa3b2b-c8d7-48c0-84f3-1e5710e8d2ef | Application Name   | ACTIVATED            | APPLICATION        |              |                |
      | c80a79a9857040acafbea2774008ed1b | 200            | false                    | false               | c80a79a9857040acafbea2774008ed1b | 156                    | ZAR                       | South African rand         | R                            | true                            | SANDBOX       | 4562605b-28b4-466f-b2e4-fd67df78c816 | Test               | ACTIVATED            | APPLICATION        |              |                |
      | test                             | 401            |                          |                     |                                  |                        |                           |                            |                              |                                 |               |                                      |                    |                      |                    | Unauthorized | /api/v2/config |
      | " "                              | 401            |                          |                     |                                  |                        |                           |                            |                              |                                 |               |                                      |                    |                      |                    | Unauthorized | /api/v2/config |