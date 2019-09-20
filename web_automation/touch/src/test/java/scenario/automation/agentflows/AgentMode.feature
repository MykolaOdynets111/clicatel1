@agent_mode
Feature: User messages handling in "Agent" tenant mode

  Verification of basic communication between user and agent in "Agent" tenant mode


  Scenario: Verify if user is redirected to the agent after NEUTRAL or POSITIVE message
    Given I login as agent of Automation
    Given User select Automation tenant
    And Click chat icon
    When User enter account balance into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with account balance user's message
    Then Valid sentiment icon are shown for account balance message in left menu with chat
    Then Message account balance shown like last message in left menu with chat
    And There is no more than one from user message
    And There is no from agent response added by default for account balance user message
    When Agent responds with hello to User
    Then User have to receive 'hello' text response for his 'account balance' input


