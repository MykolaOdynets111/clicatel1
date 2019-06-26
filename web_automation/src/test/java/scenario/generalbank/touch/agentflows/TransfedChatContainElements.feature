@egor
Feature: Transferring chat

  Verification of basic transfer chat functionality

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-1742")
  Scenario: Verify if agent is able to transfer chat via "Transfer chat" button
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    Given I login as second agent of General Bank Demo
    When First Agent click on new conversation
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming transfer" header
    Then Second Agent receives incoming transfer on the right side of the screen with user's profile picture, channel and sentiment

