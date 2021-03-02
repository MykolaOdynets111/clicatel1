Feature: Chat Filtering

  Background:
    Given I login as agent of General Bank Demo

  Scenario: Verify agent can set filter for live chat, closed chat or tickets
    When Agent click on filter button
    Then Agent see channel, sentiment and flagged as filter options for live chats
    When And Agent select "Closed" left menu option
    When Agent click on filter button
    Then Agent see channel, sentiment, flagged and dates as filter options for closed chats
    When And Agent select "Tickets" left menu option
    When Agent click on filter button
    Then Agent see channel, sentiment, flagged and dates as filter options for tickets

