@setting_changes
@no_chatdesk
Feature: out_of_support_hours auto responder (Bot mode)

  @support_hours
  Scenario: out_of_support_hours Agent message disabling for Bot mode tenant
    Given Taf out_of_support_hours is set to false for Automation Bot tenant
    Given Set agent support hours with day shift
    Given User select Automation Bot tenant
    And Click chat icon
    When User enter chat to agent into widget input field
    Then There is no out_of_support_hours response

  @support_hours
  Scenario: out_of_support_hours Agent message enabling and editing for Bot mode tenant
    Given Taf out_of_support_hours is set to true for Automation Bot tenant
    Given Taf out_of_support_hours message text is updated for Automation Bot tenant
    Given Set agent support hours with day shift
    Given User select Automation Bot tenant
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Text response that contains "out_of_support_hours" is shown

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2600")
  @Regression
  @start_orca_server
  @support_hours
  Scenario: CD :: Dashboard :: Settings :: out_of_support_hours resetting to default for Bot mode tenant
    Given Taf Out of Support Hours message is set to true for Automation Bot tenant
    And Taf Out of Support Hours message message text is updated for Automation Bot tenant
    And Set agent support hours with day shift
    And I login as agent of Automation Bot
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Auto Responders page
    And Wait for auto responders page to load
    And Agent click expand arrow for Out of Support Hours message auto responder
    And Click "Reset to default" button for Out of Support Hours message auto responder
    Then Out of Support Hours message is reset on backend
    And Send connect to agent message by ORCA
    Then Verify Orca returns Out of Support Hours message autoresponder during 40 seconds