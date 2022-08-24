@agent_mode
@no_chatdesk
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

  @agent_support_hours
  Scenario: out_of_support_hours resetting to default for Bot mode tenant
    Given Taf out_of_support_hours is set to true for Automation Bot tenant
    Given Taf out_of_support_hours message text is updated for Automation Bot tenant
    Given Set agent support hours with day shift
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Auto Responders page
    And Wait for auto responders page to load
    When Agent click expand arrow for Out of Support Hours message auto responder
    And Click "Reset to default" button for Out of Support Hours message auto responder
    Then out_of_support_hours is reset on backend
    Given User select Automation Bot tenant
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Text response that contains "out_of_support_hours" is shown