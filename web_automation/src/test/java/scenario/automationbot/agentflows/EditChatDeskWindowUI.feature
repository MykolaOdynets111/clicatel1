#@no_widget
#@no_chatdesk
#Feature: Managing Chat desk Window
#
#  Scenario: Changing available agent
#    Given I open portal
#    And Login into portal as an admin of Automation Bot account
#    When I select Touch in left menu and Touch Preferences in submenu
#    And Click "Auto responders" nav button
#    When Wait for auto responders page to load
#    And Type new message: New message to  Welcome message message field
#    And Type new message: New Connecting Agent message to  Connecting Agent message field
#    And Type new message: New Agent Busy message message to  Agent Busy message message field
#    And Type new message: New Out of Support Hours message to  Out of Support Hours message field
#    And Type new message: New End chat message to  End chat message field
#    And Type new message: New Direct channel message to  Direct channel message field
#    When Agent click 'Save changes' button
#    Then welcome_message on backend corresponds to Welcome message on frontend
#    And connect_agent on backend corresponds to Connecting Agent on frontend
#    And agents_away on backend corresponds to Agent Busy message on frontend
#    And out_of_support_hours on backend corresponds to Out of Support Hours on frontend
#    And start_new_conversation on backend corresponds to End chat on frontend
#    And contact_us_message on backend corresponds to Direct channel on frontend
