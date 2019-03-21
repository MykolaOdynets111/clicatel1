@no_widget
Feature: Agent should be able to see all filters

  Scenario: Verify agent can see all filters
    When I login as agent of General Bank Demo
    Then Agent see "Live Chats, Webchat, Facebook, Twitter, Chat history, Tickets, Flagged chats" filter options

