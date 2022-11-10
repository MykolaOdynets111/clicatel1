@Regression
Feature: Chat Filtering

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2997")
  Scenario: CD:: Sorting_Chats:: Verify agent can set filter for live chat, closed chat or tickets
    Given I login as agent of General Bank Demo
    When Agent click on filter button
    Then Agent see channel, sentiment and flagged as filter options for live chats
    When And Agent select "Closed" left menu option
    When Agent click on filter button
    Then Agent see channel, sentiment, flagged and dates as filter options for closed chats
    When And Agent select "Tickets" left menu option
    Then Agent see channel and dates as filter options for tickets
    | All Channels |
    | WhatsApp |
    | SMS |
    | Apple Business Chat |
    And Agent see sentiments as filter options for tickets
    | All Sentiments |
    | Positive |
    | Neutral |
    | Negative |

