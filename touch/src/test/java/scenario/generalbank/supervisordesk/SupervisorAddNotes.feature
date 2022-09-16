Feature: Supervisor desk

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1427")
  Scenario: Supervisor Desk :: Chat :: Notes :: Verify if supervisor is able to add notes to a live chat
    Given Setup ORCA abc integration for General Bank Demo tenant
    And Send Notes for Live Chat message by ORCA
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Supervisor Desk in submenu
    And Agent click On Live Supervisor Desk chat from Orca channel
    When Supervisor adds a note "Live Chat Test Note Message", Jira link "https://livechatdummy.com" and Ticket Number "662210"
    Then Supervisor sees note "Live Chat Test Note Message", Jira link "https://livechatdummy.com/" and Ticket Number "662210"

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-3882")
  Scenario: Supervisor Desk :: Chat :: Notes :: Verify if supervisor is able to add notes to a closed chat
    Given Setup ORCA abc integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    And Send Notes for Closed Chat message by ORCA
    When Agent click on new conversation request from ORCA
    And Agent closes chat
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Supervisor Desk in submenu
    And Admin select "Closed" left menu option
    And Supervisor opens closed chat
    When Supervisor adds a note "Closed Chat Test Note Message", Jira link "https://closedchatdummy.com" and Ticket Number "662220"
    Then Supervisor sees note "Closed Chat Test Note Message", Jira link "https://closedchatdummy.com/" and Ticket Number "662220"