Feature: connect_agent auto responder (Agent mode)

  Scenario: Connecting Agent message disabling for Agent mode tenant
    Given Taf connect_agent is set to false for Automation tenant
    Given I login as agent of Automation
    Given User select Automation tenant without creating profile
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Card with a Let me connect you with a live agent to assist you further. Before I transfer you, please give us some basic info: text is shown on user chat to agent message
    And User is able to provide some info about himself in the card after his chat to agent message
    And User click 'Submit' button in the card after user message: chat to agent
    Then There is no connect_agent response

  Scenario: Connecting Agent message enabling and editing for Agent mode tenant
    Given Taf connect_agent is set to true for Automation tenant
    Given Taf connect_agent message text is updated for Automation tenant
    Given I login as agent of Automation
    Given User select Automation tenant without creating profile
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Card with a Let me connect you with a live agent to assist you further. Before I transfer you, please give us some basic info: text is shown on user chat to agent message
    And User is able to provide some info about himself in the card after his chat to agent message
    And User click 'Submit' button in the card after user message: chat to agent
    Then Text response that contains "connect_agent" is shown

  @no_chatdesk
  Scenario: Reset to default connect_agent auto responder (Agent mode)
    Given Taf connect_agent is set to true for Automation tenant
    Given Taf connect_agent message text is updated for Automation tenant
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch preferences in submenu
    And Click "Auto responders" nav button
    When Agent click expand arrow for Connecting Agent message auto responder
    And Click "Reset to default" button for Connecting Agent message auto responder
    Then connect_agent is reset on backend


