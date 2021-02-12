@no_widget
@no_chatdesk
Feature: Managing Agent's Auto responders

  Scenario: Changing agent auto responders, save it and check if it saved on backend
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Auto Responders page
    When Wait for auto responders page to load
    And Type new message: New message to: Greeting message message field
    And Type new message: New Connecting Agent message to: Connecting Agent message message field
    And Type new message: Connecting Agent message (Web Chat) to: Connecting Agent message (Web Chat) message field
    And Type new message: New Agent Busy message to: Agent Busy message message field
    And Type new message: New Out of Support Hours message to: Out of Support Hours message message field
    And Type new message: New End chat message to: End chat message message field
    And Type new message: New Direct channel message to: Direct channel message message field
    And Type new message: New Directing to Agent (Social Channels) to: Directing to Agent (Social Channels) message field
    And Type new message: New Connecting Agent message (Social Channels) to: Connecting Agent message (Social Channels) message field
    And Type new message: New //stop and //end message to: End Chat and Opt-Out Keywords message message field
    And Type new message: New Session Timeout message (Web Chat) to: Session Timeout message (Web Chat) message field
    Then welcome_message on backend corresponds to Greeting message on frontend
    And connect_agent on backend corresponds to Connecting Agent message on frontend
    And agents_away on backend corresponds to Agent Busy message on frontend
    And out_of_support_hours on backend corresponds to Out of Support Hours message on frontend
    And start_new_conversation on backend corresponds to End chat message on frontend
    And contact_us_message on backend corresponds to Direct channel message on frontend
    And directing_to_agent on backend corresponds to Directing to Agent (Social Channels) on frontend
    And thanks_for_patience_webchat on backend corresponds to Connecting Agent message (Web Chat) on frontend
    And thanks_for_patience on backend corresponds to Connecting Agent message (Social Channels) on frontend
    And end_and_stop_message on backend corresponds to End Chat and Opt-Out Keywords message on frontend
    And session_timeout on backend corresponds to Session Timeout message (Web Chat) on frontend
