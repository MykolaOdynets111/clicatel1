@agent_mode
Feature: out_of_support_hours auto responder (Bot mode)

  @agent_support_hours
  Scenario: out_of_support_hours Agent message disabling for Bot mode tenant
    Given Taf out_of_support_hours is set to false for Automation Bot tenant
    Given Set agent support hours with day shift
    Given User select Automation Bot tenant
    And Click chat icon
    When User enter chat to agent into widget input field
    Then There is no out_of_support_hours response


  @agent_support_hours
  Scenario: out_of_support_hours Agent message enabling and editing for Bot mode tenant
    Given Taf out_of_support_hours is set to true for Automation Bot tenant
    Given Taf out_of_support_hours message text is updated for Automation Bot tenant
    Given Set agent support hours with day shift
    Given User select Automation Bot tenant
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Text response that contains "out_of_support_hours" is shown