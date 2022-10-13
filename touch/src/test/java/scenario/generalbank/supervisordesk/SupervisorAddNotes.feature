@no_widget
@orca_api
@Regression
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1427")
  Scenario: CD :: Supervisor Desk :: Chat :: Notes :: Verify if supervisor is able to add notes to a live chat
    Given Setup ORCA abc integration for General Bank Demo tenant
    And Send Notes for Live Chat message by ORCA
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent click On Live Supervisor Desk chat from Orca channel
    When Supervisor adds a note "Live Chat Test Note Message", Jira link "https://livechatdummy.com" and Ticket Number "662210"
    Then Supervisor sees note "Live Chat Test Note Message", Jira link "https://livechatdummy.com/" and Ticket Number "662210"

  @TestCaseId("https://jira.clickatell.com/browse/CCD-3882")
  Scenario: CD :: Supervisor Desk :: Chat :: Notes :: Verify if supervisor is able to add notes to a closed chat

    Given Setup ORCA abc integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send to agent message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    And Agent closes chat

    And I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Supervisor Desk in submenu
    And Admin select "Closed" left menu option
    And Supervisor opens closed chat
    When Supervisor adds a note "Closed Chat Test Note Message", Jira link "https://closedchatdummy.com" and Ticket Number "662220"
    Then Supervisor sees note "Closed Chat Test Note Message", Jira link "https://closedchatdummy.com/" and Ticket Number "662220"

  @TestCaseId("https://jira.clickatell.com/browse/CCD-3883")
  @support_hours
  Scenario: CD :: Supervisor Desk :: Chat :: Notes :: Verify if supervisor is able to add notes to a ticket
    Given Setup ORCA abc integration for General Bank Demo tenant
    And Set agent support hours with day shift
    And Send Notes for Ticket message by ORCA
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent select "Tickets" left menu option
    And Supervisor clicks on first ticket
    When Supervisor adds a note "Ticket Test Note Message", Jira link "https://ticketdummy.com" and Ticket Number "662230"
    Then Supervisor sees note "Ticket Test Note Message", Jira link "https://ticketdummy.com/" and Ticket Number "662230"