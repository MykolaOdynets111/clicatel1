@agent_mode
Feature: Pinned chat is disabled to close and to transfer

  Verification of basic pin chat functionality

  Background:
    Given AGENT_FEEDBACK tenant feature is set to true for Automation Bot
    Given User select Automation Bot tenant
    Given I login as agent of Automation Bot
    And Click chat icon
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to agent user's message


  Scenario: Agent receives error message when tries to close the pined chat
    When Agent click 'Pin' button
    Then Agent sees 'flag' icon in this chat
    When Agent click "End chat" button
    Then Agent receives 'pin' error message
    When Agent click 'Unpin' button
    Then Agent click "End chat" button
    Then End chat popup should be opened
    When Agent click 'Skip' button
    Then Agent should not see from user chat in agent desk
    Then User have to receive 'exit' text response for his 'connect to agent' input


  Scenario: "Transfer chat" button disabled for pined chat
    When Agent click 'Pin' button
    Then Agent sees 'flag' icon in this chat
    Then Agent can not click 'Transfer chat' button
    Then Agent click 'Unpin' button
    Given I login as second agent of Automation Bot
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Incoming transfer" header
    Then Second agent receives incoming transfer with "Please take care of this one" note from the another agent
    And Second agent can see transferring agent name, user name and following user's message: 'connect to agent'
    When Second agent click "Accept transfer" button
    Then Second agent has new conversation request
    And Agent should not see from user chat in agent desk
    When Second agent click on new conversation
    Then Conversation area becomes active with connect to agent user's message in it for second agent
    When Second agent responds with hello to User
    Then User have to receive 'hello' text response for his 'connect to agent' input

