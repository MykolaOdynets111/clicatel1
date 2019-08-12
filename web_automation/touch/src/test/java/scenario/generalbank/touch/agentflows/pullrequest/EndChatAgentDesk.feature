@agent_feedback
Feature: Closing chat and no empty CRM ticket creating

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon

  Scenario: Verify if agent is able to close chat in chat desk and no Empty CRM ticket created
    Given AGENT_FEEDBACK tenant feature is set to true for General Bank Demo
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup should be opened
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then User have to receive 'exit' text response for his 'connect to Support' input
    Then CRM ticket is not created

