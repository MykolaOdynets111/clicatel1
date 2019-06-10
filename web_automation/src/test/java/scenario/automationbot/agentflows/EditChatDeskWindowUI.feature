@no_widget
@no_chatdesk
@agent_session_capacity
Feature: Managing Chat desk Window

  Scenario: Check changing available agent and off/on  Chat Conclusion
    Given AGENT_FEEDBACK tenant feature is set to true for Automation Bot
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch Preferences in submenu
    And Click "Chat Desk" nav button
    When Change chats per agent:"0"
    Then Error message is shown
    When Change chats per agent:""
    Then Error message is shown
    When Click off/on  Chat Conclusion
    Then  On backand AGENT_FEEDBACK tenant feature status is set to false for Automation Bot
    When Click off/on  Chat Conclusion
    Then  On backand AGENT_FEEDBACK tenant feature status is set to true for Automation Bot



  Scenario: Check changing available agent by '+' and '-'
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch Preferences in submenu
    And Click "Chat Desk" nav button
    When Change chats per agent:"1"
    When Click "+" button 5 times chats per agent became:"6"
    When Click "-" button 3 times chats per agent became:"3"

