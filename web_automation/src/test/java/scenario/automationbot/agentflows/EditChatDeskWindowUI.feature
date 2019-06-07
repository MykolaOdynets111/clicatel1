@no_widget
@no_chatdesk
@agent_session_capacity
Feature: Managing Chat desk Window

  Scenario: Check changing available agent and off/on  Chat Conclusion
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch Preferences in submenu
    And Click "Chat Desk" nav button
    When Change chats per agent:"0"
    Then Error message is shown
    When Change chats per agent:""
    Then Error message is shown
    When Click off/on  Chat Conclusion
    Then  On backand AGENT_FEEDBACK tenant feature is set to false for Automation Bot
    When Click off/on  Chat Conclusion
    Then  On backand AGENT_FEEDBACK tenant feature is set to true for Automation Bot

