@support_hours
Feature: Supervisor desk

  Background:
    Given I open portal
    And Login into portal as an admin of Automation account
    And Set agent support hours with day shift

  Scenario: Supervisor desk: Verify if supervisor can use different filter options for filtering Chat Desk tickets
    Given User select Automation tenant
    And Click chat icon
    When User enter chat to agent into widget input field
    And I select Touch in left menu and Supervisor Desk in submenu
    When Agent select "Tickets" left menu option
    And Agent search chat touch on Supervisor desk
    Then Agent see tickets from orca on Unassigned filter page
    And Agent verify that only 1 ticket is shown
    When Agent refreshes the page
    When Agent filter by "Webchat" channel and "Positive" sentiment
    Then Agent see tickets from orca on Unassigned filter page
    And User enter dsdfsdf into widget input field
    When User enter sfdsfsdfsd into widget input field
    And Agent refreshes the page
    When Agent filter by "Webchat" channel and "Neutral" sentiment
    Then Agent see tickets from orca on Unassigned filter page
    When User enter hate you into widget input field
    And Agent refreshes the page
    When Agent filter by "Webchat" channel and "Negative" sentiment
    Then Agent see tickets from orca on Unassigned filter page
    When Agent filter by "Webchat" channel and "Positive" sentiment
    Then Agent checks Ticket from Touch is not present on Supervisor Desk