@no_widget
@no_chatdesk
@agent_session_capacity
Feature: Managing Chat desk Window

  Scenario: Check changing available agent and off/on Chat Conclusion
    Given Transfer timeout for Automation Bot tenant is set to 600 seconds
    Given AGENT_FEEDBACK tenant feature is set to true for Automation Bot
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    When Change chats per agent:"0"
    Then Error message is shown
    When Change chats per agent:""
    Then Error message is shown
    When Change chats per agent:"50"
    When Click off/on Chat Conclusion
    Then  On backand AGENT_FEEDBACK tenant feature status is set to false for Automation Bot
    When Click off/on Chat Conclusion
    Then  On backand AGENT_FEEDBACK tenant feature status is set to true for Automation Bot



  Scenario: Check changing available chats per agent
    Given Transfer timeout for Automation Bot tenant is set to 600 seconds
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    When Change chats per agent:"6"
    And Agent click 'Save changes' button
    And Agent refreshes the page
    Then Chats per agent became:"6"

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-114756")
  Scenario: CD :: Dashboard :: Settings :: Preferences :: Verify if supervisor can't input decimal number for maximum number of chat
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Preferences page
    When Change chats per agent:"0.5"
    Then Decimal numbers (e.g., 3.5) are not allowed. Please enter a whole number Error message is shown