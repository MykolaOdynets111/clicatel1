@img
Feature: Transferring chat, user info

  Background:
    Given User select Automation Bot tenant
    Given I login as agent of Automation Bot
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-1742")
  Scenario: Transfer chat: All required fields in pop-up should be filled. Notification should have user's profile picture, channel and sentiment
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    Given I login as second agent of Automation Bot
    When First Agent click on new conversation
    When Agent click on 'Transfer' chat
    Then Transfer chat pop up appears for agent
    When Agent select an agent in 'Transfer to' drop down
    Then Agent notes field is appeared
    And Agent sees error message 'Notes are required when specific agent is selected.'
    When Agent complete 'Note' field
    And  Click on 'Transfer' button in pop-up
    Then Second agent receives incoming transfer with "Incoming Transfer" header
    Then Second Agent receives incoming transfer on the right side of the screen with user's profile picture, channel and sentiment