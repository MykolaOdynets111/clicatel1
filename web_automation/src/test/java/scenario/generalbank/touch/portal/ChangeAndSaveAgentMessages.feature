@no_widget
Feature: Managing Agent's Auto responders

  Scenario: Changing agent auto responders, save it and check if it saved on backend
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch Preferences in submenu
    And Click "Auto responders" nav button
    When Agent click expand arrow for  Welcome message auto responder
    Then Type new message: New message to  Welcome message message field
    When Agent click expand arrow for  Connecting Agent auto responder
    Then Type new message: New Connecting Agent message to  Connecting Agent message field
    When Agent click expand arrow for  Agent Busy message auto responder
    Then Type new message: New Agent Busy message message to  Agent Busy message message field
    When Agent click expand arrow for  Out of Support Hours auto responder
    Then Type new message: New Out of Support Hours message to  Out of Support Hours message field
    When Agent click expand arrow for  End chat auto responder
    Then Type new message: New End chat message to  End chat message field
    When Agent click expand arrow for  Direct channel auto responder
    Then Type new message: New Direct channel message to  Direct channel message field
    When Agent click 'Save changes' button
    Then welcome_message on backend corresponds to Welcome message on frontend
    Then connect_agent on backend corresponds to Connecting Agent on frontend
    Then agents_away on backend corresponds to Agent Busy message on frontend
    Then out_of_support_hours on backend corresponds to Out of Support Hours on frontend
    Then start_new_conversation on backend corresponds to End chat on frontend
    Then contact_us_message on backend corresponds to Direct channel on frontend
