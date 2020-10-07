@agent_support_hours
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
    And Agent search chat from touch on Supervisor desk
    Then Ticket from touch is present on All tickets filter page
    And Verify that only 1 ticket is shown
    When Agent refreshes the page
    When Agent filter by "Webchat" channel and "Positive" sentiment
    Then Ticket from touch is present on All tickets filter page
    And User enter dsdfsdf into widget input field
    When User enter sfdsfsdfsd into widget input field
    And Agent refreshes the page
    When Agent filter by "Webchat" channel and "Neutral" sentiment
    Then Ticket from touch is present on All tickets filter page
    When User enter hate you into widget input field
    And Agent refreshes the page
    When Agent filter by "Webchat" channel and "Negative" sentiment
    Then Ticket from touch is present on All tickets filter page
    When Agent filter by "Webchat" channel and "Positive" sentiment
    Then Ticket from Touch is not present on Supervisor Desk


  @no_widget
  @dot_control
  Scenario Outline: Supervisor desk: Verify if supervisor can use different filter options for filtering <channelName> tickets
    Given Create .Control integration for Automation and adapter: <adapter>
    When Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    When Send message call
    And I select Touch in left menu and Supervisor Desk in submenu
    When Agent select "Tickets" left menu option
    And Agent search chat from dotcontrol on Supervisor desk
    Then Ticket from dotcontrol is present on All tickets filter page
    And Verify that only 1 ticket is shown
    When Agent refreshes the page
    And Agent filter by "<channelName>" channel and "Positive" sentiment
    Then Ticket from dotcontrol is present on All tickets filter page
    When Send dsdfsdf message for .Control
    And Send sfdsfsdfsd message for .Control
    When Agent refreshes the page
    And Agent filter by "<channelName>" channel and "Neutral" sentiment
    Then Ticket from dotcontrol is present on All tickets filter page
    When Send hate you message for .Control
    When Agent refreshes the page
    And Agent filter by "<channelName>" channel and "Negative" sentiment
    Then Ticket from dotcontrol is present on All tickets filter page
    When Agent filter by "<channelName>" channel and "Positive" sentiment
    Then Ticket from dotcontrol is not present on Supervisor Desk

    Examples:
      |adapter                                             |channelName|
      |fbmsg                                               |Facebook|
      |whatsapp                                            |WhatsApp|
      |twdm                                                |Twitter|


#  @no_widget
#  @orca_api
#  Scenario: Supervisor desk: Verify if supervisor can use different filter options for filtering ORCA tickets
#    Given Setup ORCA integration for Automation tenant
#    When Send to agent message by ORCA
#    And I select Touch in left menu and Supervisor Desk in submenu
#    When Agent select "Tickets" left menu option
#    And Agent search chat from orca on Supervisor desk
#    Then Ticket from orca is present on All tickets filter page
#    And Verify that only 1 ticket is shown
#    When Agent refreshes the page
#    And Agent filter by "Apple Business Chat" channel and "Positive" sentiment
#    Then Ticket from orca is present on All tickets filter page
#    When Send to dsdfsdf message by ORCA
#    And Send sfdsfsdfsd message by ORCA
#    When Agent refreshes the page
#    And Agent filter by "Apple Business Chat" channel and "Neutral" sentiment
#    Then Ticket from orca is present on All tickets filter page
#    When Send hate you message by ORCA
#    When Agent refreshes the page
#    And Agent filter by "Apple Business Chat" channel and "Negative" sentiment
#    Then Ticket from orca is present on All tickets filter page
#    When Agent filter by "Apple Business Chat" channel and "Positive" sentiment
#    Then Ticket from orca is not present on Supervisor Desk