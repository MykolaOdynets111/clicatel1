@no_widget
@no_chatdesk
@setting_changes
Feature: Managing Chat desk Window

  Scenario: Check changing available agent and off/on Chat Conclusion
    Given Transfer timeout for Automation Bot tenant is set to 600 seconds
    Given AGENT_FEEDBACK tenant feature is set to true for General Bank Demo
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    When Change chats per agent:"0"
    Then Error message is shown
    When Change chats per agent:""
    Then Error message is shown
    When Change chats per agent:"50"
    When Click off/on Chat Conclusion
    Then  On backand AGENT_FEEDBACK tenant feature status is set to false for General Bank Demo
    When Click off/on Chat Conclusion
    Then  On backand AGENT_FEEDBACK tenant feature status is set to true for General Bank Demo

  Scenario: Check changing available chats per agent
    Given Transfer timeout for Automation Bot tenant is set to 600 seconds
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    When Change chats per agent:"6"
    And Agent click 'Save changes' button
    And Agent refreshes the page
    Then Chats per agent became:"6"

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1166")
  @Regression
  Scenario: CD :: Dashboard :: Settings :: Preferences :: Verify if supervisor can't input decimal number for maximum number of chat
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    When Change chats per agent:"0.5"
    Then Decimal numbers (e.g., 3.5) are not allowed. Please enter a whole number Error message is shown

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2657")
  @Regression
  Scenario: CD:: Dashboard:: Setting ::  Verify if error is displayed if user enters 0 in the Maximum Chats per Agent
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    When Change chats per agent:"0"
    Then Maximum Chats per Agent must be a positive number Error message is shown