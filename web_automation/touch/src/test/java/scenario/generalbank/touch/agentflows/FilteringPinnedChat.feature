Feature: Agent should be able to filter flagged chats

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon

  Scenario: Verify agent can filter flagged chats
    When User enter to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    When Agent click 'Flag chat' button
    Then Agent sees 'flag' icon in this chat
    When Agent select "Flagged chats" filter option
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    When Agent responds with hello to User
    Then User have to receive 'hello' text response as a second response for his 'to agent' input
    When Agent click 'Unflag chat' button
    Then Agent should not see from user chat in agent desk

