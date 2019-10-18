@agent_mode
Feature: Suggestions in "Agent" tenant mode

  @suggestions
  Scenario: Verify if suggestion are shown in "Agent" mode
    Given AGENT_ASSISTANT tenant feature is set to true for Automation
    Given I login as agent of Automation
    Given User select Automation tenant
    And Click chat icon
    When User enter My staff want to bank with you. Can you help? into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with My staff want to bank with you. Can you help? user's message
    Then There is correct suggestion shown on user message "My staff want to bank with you. Can you help?"
    And The suggestion for user message "My staff want to bank with you. Can you help?" with the biggest confidence is added to the input field
    And Agent is able to delete the suggestion from input field and sends his own "thanks for asking" message
    Then User should see 'thanks for asking' text response for his 'My staff want to bank with you. Can you help?' input




