@no_widget
@no_chatdesk
Feature: Managing Agent's Auto responders

  Scenario: Changing agent auto responders, save it and check if it saved on backend
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch Preferences in submenu
    And Click "Auto responders" nav button
    When Wait for auto responders page to load
    And Type new message: New message to Enable Web Chat message message field
    And Type new message: New Connecting Agent message to Enable Connecting Agent message message field
    And Type new message: New Agent Busy message message to Enable Agent Busy message message field
    And Type new message: New Out of Support Hours message to Enable Out of Support Hours message message field
    And Type new message: New End chat message to Enable End chat message message field
    And Type new message: New Direct channel message to Enable Direct channel message message field
    When Agent click 'Save changes' button
    Then welcome_message on backend corresponds to Enable Web Chat message on frontend
    And connect_agent on backend corresponds to Enable Connecting Agent message on frontend
    And agents_away on backend corresponds to Enable Agent Busy message on frontend
    And out_of_support_hours on backend corresponds to Enable Out of Support Hours message on frontend
    And start_new_conversation on backend corresponds to Enable End chat message on frontend
    And contact_us_message on backend corresponds to Enable Direct channel message on frontend
