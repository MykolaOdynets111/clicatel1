@agent_support_hours
Feature: Supervisor desk

  Background:
    Given User select Automation tenant
    And Set agent support hours with day shift
    Given Click chat icon
    When User enter chat to agent into widget input field

  Scenario: Supervisor desk: Verify if supervisor can use different filter options for filtering tickets
    Given I open portal
    And Login into portal as an admin of Automation account
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    Then Ticket from touch is present on All tickets filter page
    When Agent filter by "Webchat" channel and "Positive" sentiment
    And User enter dsdfsdf into widget input field
    When User enter sfdsfsdfsd into widget input field
    And Agent refreshes the page
    When Agent filter by "Webchat" channel and "Neutral" sentiment
    Then Ticket from touch is present on All tickets filter page
    When User enter hate you into widget input field
    And Agent refreshes the page
    When Agent filter by "Webchat" channel and "Negative" sentiment
    Then Ticket from touch is present on All tickets filter page