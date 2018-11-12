@healthcheck
Feature: Tenant Widget health check

  Scenario: Widget is connecting and bot is responding
    Given User opens page with desired tenant widget
    And Click chat icon
    Then Widget is connected
    When User enter hi into widget input field
    Then User have to receive 'Hello' text response for his 'hi' input

  Scenario: Before redirecting to the agent user is asked about his info
    Given User opens page with desired tenant widget
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Card with a Before I transfer you, please give us some basic info: text is shown on user chat to agent message
    And User is able to provide some info about himself in the card after his chat to agent message
