Feature: Agent should be able to filter flagged chats

  Background:
    Given User select Automation tenant
    Given I login as agent of Automation
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-4381")
  Scenario: Verify agent can filter flagged chats
    When User enter to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    When Agent click 'Flag chat' button
    When Agent filter Live Chants with no channel, no  sentiment and flagged is true
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Agent sees 'flag' icon in this chat
    When Agent responds with hello to User
    Then User should see 'hello' text response for his 'to agent' input
    When Agent click 'Unflag chat' button
    Then Agent should not see from user chat in agent desk

