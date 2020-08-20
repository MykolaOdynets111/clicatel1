@agent_feedback
Feature: Create tag

  Background:
    Given AGENT_FEEDBACK tenant feature is set to true for Automation
    Given User select Automation tenant
    Given I open portal
    And Login into portal as an admin of Automation account
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Chat Tags page


  Scenario: verify when supervisor creates a tag, the newly created tag is available for the agent to use
    And Create chat tag
    When Agent switches to opened Portal page
    And I select Touch in left menu and Agent Desk in submenu
    When Click chat icon
    And User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    Then Agent select precreated tag
    Then Agent type Note:CheckTagsCreation, Link:TagsCreation, Number:23544 for CRM ticket
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then CRM ticket is created on backend with correct information