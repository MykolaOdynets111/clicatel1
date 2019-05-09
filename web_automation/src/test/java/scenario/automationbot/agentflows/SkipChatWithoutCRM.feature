@agent_feedback
Feature: Interaction with 'Skip' button on Agent Feedback window

  Background:
    Given AGENT_FEEDBACK tenant feature is set to true for Automation Bot
    Given I login as agent of Automation Bot
    Given User select Automation Bot tenant
    And Click chat icon

    Scenario: Agent is able to 'Skip' in end-chat pop-up in chat desk, no CRM ticket created
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup should be opened
    When Agent click 'Skip' button
    Then Agent should not see from user chat in agent desk
    Then User should see 'exit' text response for his 'connect to Support' input
    Then CRM ticket is not created

  Scenario: Agent is able fill form and to 'Skip' in end-chat pop-up in chat desk, no CRM ticket created
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup should be opened
    Then Agent fills form
    When Agent click 'Skip' button
    Then Agent should not see from user chat in agent desk
    Then User should see 'exit' text response for his 'connect to Support' input
    Then CRM ticket is not created



