Feature: connect_agent auto responder (Bot mode)

  Scenario: Connecting Agent message disabling for Bot mode tenant
    Given Taf connect_agent is set to false for Automation Bot tenant
    Given I login as agent of Automation Bot
    Given User select Automation Bot tenant without creating profile
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Card with a Let me connect you with a live agent to assist you further. Before I transfer you, please give us some basic info: text is shown on user chat to agent message
    And User is able to provide some info about himself in the card after his chat to agent message
    And User click 'Submit' button in the card after user message: chat to agent
    Then There is no connect_agent response

  Scenario: Connecting Agent message enabling and editing for Bot mode tenant
    Given Taf connect_agent is set to true for Automation Bot tenant
    Given Taf connect_agent message text is updated for Automation Bot tenant
    Given I login as agent of Automation Bot
    Given User select Automation Bot tenant without creating profile
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Card with a Let me connect you with a live agent to assist you further. Before I transfer you, please give us some basic info: text is shown on user chat to agent message
    And User is able to provide some info about himself in the card after his chat to agent message
    And User click 'Submit' button in the card after user message: chat to agent
    Then Text response that contains "connect_agent" is shown