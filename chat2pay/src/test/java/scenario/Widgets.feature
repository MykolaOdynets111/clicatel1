# Created by modynets at 25.01.2023
Feature: Widgets Configuration
  # Enter feature description here

  Background:
    Given User is logged in to unity

  @TestCaseId("https://jira.clickatell.com/browse/C2P-4336")
  Scenario Outline: C2P :: Unity API :: Widget Configuration :: POST :: Create widget for an account

    Then User creates widget for an account

      | i.type        | <i.type>        |
      | i.environment | <i.environment> |
    Then User delete newly created widget


    Examples:
      | i.type      | i.environment | o.responseCode | o.widgetId                       | o.createdTime                 | o.timestamp | o.status | o.error | o.path |
      | CHAT_TO_PAY | SANDBOX       | 200            | 2c9acc1485d2efb70185e97e0c3e02a9 | 2023-01-25T15:13:50.145+00:00 |             |          |         |        |
      | CHAT_TO_PAY | PRODUCTION    | 200            |                                  |                               |             |          |         |        |
