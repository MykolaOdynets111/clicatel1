@support_hours
@Regression
@no_widget
@orca_api
Feature: WhatsApp ORCA :: Supervisor Desk

  Background:
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    And Set agent support hours with day shift

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1896")
  Scenario: Supervisor desk: Verify if supervisor can use different filter options for filtering WhatsApp ORCA tickets
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send to agent message by ORCA
    And I select Touch in left menu and Supervisor Desk in submenu
    When Agent select "Tickets" left menu option
    And Agent search chat orca on Supervisor desk
    Then Agent see tickets from orca on Assigned filter page
    When Agent refreshes the page
#toDo Uncomment when sentiments will be configured for tenant
#    And Agent filter by "Apple Business Chat" channel and "Positive" sentiment
#    Then Ticket from orca is present on "All tickets" filter page
    When Send to dsdfsdf message by ORCA
    And Send sfdsfsdfsd message by ORCA
#    When Agent refreshes the page
    And Agent filter by "Apple Business Chat" channel and "Neutral" sentiment
    Then Agent checks Ticket from orca is not present on Supervisor Desk
#    When Send hate you message by ORCA
#    When Agent refreshes the page
#    And Agent filter by "Apple Business Chat" channel and "Negative" sentiment
#    Then Ticket from orca is present on "All tickets" filter page
#    When Agent filter by "Apple Business Chat" channel and "Positive" sentiment
#    Then Ticket from orca is not present on Supervisor Desk commented till chat name would be available


  @TestCaseId("https://jira.clickatell.com/browse/CCD-2801")
  Scenario: CD :: Supervisor Desk :: Tickets :: Verify if tickets are sorted from newest to oldest by default
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    Then Tickets are sorted in descending order

  @TestCaseId("https://jira.clickatell.com/browse/CCD-7775")
  Scenario: CD :: Agent Desk :: Supervisor Desk:: Closed Tickets:: Verify the count is updated in the closed tab when filter is applied
    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    And Update survey management chanel whatsapp settings by ip for General Bank Demo
      | ratingEnabled | false        |
    And Send 1 messages chat to agent by ORCA
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    And Wait for 2 second
    And Agent search chat orca on Supervisor desk
    Then Agent see tickets from orca on Unassigned filter page
    When Agent closed ticket for orca
    And Agent select Closed filter on Left Panel
    Then Agent verify ticket is present for orca for 2 seconds
    When Admin filter by 0 year 0 month and 1 days ago start date and 0 year 0 month and 0 days ago end date
    Then Agent verify ticket is present for orca for 2 seconds
    And Agent verify that only "whatsapp" tickets chats are shown

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6278")
  Scenario: CD :: Agent Desk :: Tickets :: Unassigned :: Verify that if tickets are filtered, the quick & custom assign options shall not be available to agents
    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    And I select Touch in left menu and Supervisor Desk in submenu
    And Send 1 messages chat to agent by ORCA
    When Agent select "Tickets" left menu option
    And Agent search chat orca on Supervisor desk
    And Agent see tickets from orca on Unassigned filter page
    And Select orca ticket checkbox
    And Click 'Assign manually' button for orca
    And 'Assign chat' window is opened
    And I assign chat on First Department for Department dropdown
    And I select Touch in left menu and Agent Desk in submenu
    And Agent select "Tickets" left menu option
    Then Agent checks quick & custom assign options on the page are visible
    When Agent search chat orca on Supervisor desk
    Then Agent checks quick & custom assign options on the page are not visible
    When Admin filter by 0 year 0 month and 1 days ago start date and 0 year 0 month and 0 days ago end date
    Then Agent checks quick & custom assign options on the page are not visible

  @TestCaseId("https://jira.clickatell.com/browse/CCD-6002")
    @skip
  Scenario: CD:: Agent Desk:: Tickets:: Agent_Desk-Tickets-Closed:: Verify if Agent selects filter "Closed by you" then count is updated in the closed ticket tab
    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    And Update survey management chanel whatsapp settings by ip for Standard Billing
      | ratingEnabled | false        |
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    And Agent select Unassigned filter on Left Panel
    And Send to agent message by ORCA
    And Agent search chat orca on Supervisor desk
    And Agent see tickets from orca on Unassigned filter page
    And Select orca ticket checkbox
    And Click 'Assign manually' button for orca
    And 'Assign chat' window is opened
    And I assign chat on Agent for Agent dropdown
    And I select Touch in left menu and Agent Desk in submenu
    And Agent select "Tickets" left menu option
    And Agent select Assigned filter on Left Panel
    And Agent search chat orca on Supervisor desk
    And Supervisor clicks on first ticket
    And Agent send closing ticket manually message
    And Agent closes ticket manually
    And Agent select Closed filter on Left Panel
    And Agent clears search field and filters on Supervisor desk
    And Agent search chat orca on Supervisor desk
    Then Agent see tickets from orca on Closed filter page
    When Agent select my closed chats checkbox in container and click "Apply filters" button
    Then Agent see tickets from orca on Closed filter page
    And Agent checks ticket count value in the Closed ticket tab is 1 on agent
    And Agent checks ticket count value 1 in tickets table
    #deselecting the checkbox
    When Agent clears search field and filters on Supervisor desk
    And Agent select my closed chats checkbox in container and click "Apply filters" button
    Then Agent checks ticket count value greater than 1 in Closed ticket tab on agent
    And Agent checks ticket count greater than 1 in tickets table

  @TestCaseId("https://jira.clickatell.com/browse/CCD-5879")
  Scenario: CD:: Agent Desk:: Verify if Agent can filter closed ticket by clicking "Closed by you" checkbox
    Given Setup ORCA Whatsapp integration for General Bank Demo tenant
    And Update survey management chanel whatsapp settings by ip for Standard Billing
      | ratingEnabled | false        |
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    And Send to agent message by ORCA
    And Wait for 2 second
    And Agent search chat orca on Supervisor desk
    And Agent see tickets from orca on Unassigned filter page
    And Select orca ticket checkbox
    And Click 'Assign manually' button for orca
    And 'Assign chat' window is opened
    And I assign chat on Agent for Agent dropdown
    And I select Touch in left menu and Agent Desk in submenu
    And Agent select "Tickets" left menu option
    And Agent select Assigned filter on Left Panel
    And Agent search chat orca on Supervisor desk
    And Supervisor clicks on first ticket
    And Agent send closing ticket manually message
    And Agent closes ticket manually
    And Agent select Closed filter on Left Panel
    Then Agent see tickets from orca on Closed filter page
    When Agent clears search field and filters on Supervisor desk
    And Agent select my closed chats checkbox in container and click "Apply filters" button
    Then Agent see tickets from orca on Closed filter page
    And You is the current agent of orca ticket