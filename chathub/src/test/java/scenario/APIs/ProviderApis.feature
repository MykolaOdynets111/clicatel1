Feature: Provider API


@TestCaseId("https://jira.clickatell.com/browse/CCH-509")
Scenario: CH :: Public provider APIs: Execute end point Get /api/providers with Authentication token should respond all the activated providers.
  Given User is able to GET providers in API response
