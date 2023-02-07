Feature: Channels Management

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4714")
  Scenario Outline: C2P Unity API :: POST /link-channels :: truth table

    Given QA User is logged in to unity
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
      | token           | 2c9acc0c85881a0e0185a110da8f0129 | bd1841e281444637a3abb6d640065d98 | 875b399337ea4898a3665ce9e921b639 | 202            |              |                                                               |
      | token           |                                  | bd1841e281444637a3abb6d640065d98 | 875b399337ea4898a3665ce9e921b639 | 404            | NOT_FOUND    | URL /v2/widget/null/link-channels                             |
      | token           | 2c9acc0c85881a0e0185a110da8f0129 |                                  | 875b399337ea4898a3665ce9e921b639 | 202            |              |                                                               |
      | token           | 2c9acc0c85881a0e0185a110da8f0129 | bd1841e281444637a3abb6d640065d98 |                                  | 202            |              |                                                               |
      | token           | test                             | bd1841e281444637a3abb6d640065d98 | 875b399337ea4898a3665ce9e921b639 | 404            | NOT_FOUND    | URL /v2/widget/test/link-channels                             |
      | token           | 2c9acc0c85881a0e0185a110da8f0129 | test                             | 875b399337ea4898a3665ce9e921b639 | 404            | NOT_FOUND    | URL /v2/widget/2c9acc0c85881a0e0185a110da8f0129/link-channels |
      | token           | 2c9acc0c85881a0e0185a110da8f0129 | bd1841e281444637a3abb6d640065d98 | test                             | 404            | NOT_FOUND    | URL /v2/widget/2c9acc0c85881a0e0185a110da8f0129/link-channels |
      | test            |                                  |                                  |                                  | 401            | Unauthorized | /v2/widget/null/link-channels                                 |
      | " "             |                                  |                                  |                                  | 401            | Unauthorized | /v2/widget/null/link-channels                                 |