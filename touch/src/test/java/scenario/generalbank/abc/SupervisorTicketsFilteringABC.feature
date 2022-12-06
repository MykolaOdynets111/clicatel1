@support_hours
@Regression
Feature: Apple Business Chat :: Supervisor Desk

  Background:
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    And Set agent support hours with day shift

  @no_widget
  @orca_api

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1158")
  Scenario: Supervisor desk: Verify if supervisor can use different filter options for filtering ORCA tickets
    Given Setup ORCA abc integration for General Bank Demo tenant
    When Send to agent message by ORCA
    And I select Touch in left menu and Supervisor Desk in submenu
    When Agent select "Tickets" left menu option
    And Agent select Assigned filter on Left Panel
    And Agent search chat orca on Supervisor desk
    Then Agent see tickets from orca on Assigned filter page
    When Agent refreshes the page
#    And Agent filter by "Apple Business Chat" channel and "Positive" sentiment
#    Then Ticket from orca is present on "All tickets" filter page
    When Send to dsdfsdf message by ORCA
    And Send sfdsfsdfsd message by ORCA
#    When Agent refreshes the page
     And Agent filter by "Apple Business Chat" channel and "Neutral" sentiment
    Then Agent see tickets from orca on Assigned filter page
#    When Send hate you message by ORCA
#    When Agent refreshes the page
#    And Agent filter by "Apple Business Chat" channel and "Negative" sentiment
#    Then Ticket from orca is present on "All tickets" filter page
#    When Agent filter by "Apple Business Chat" channel and "Positive" sentiment
#    Then Ticket from orca is not present on Supervisor Desk commented till chat name would be available