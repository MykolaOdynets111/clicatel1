Feature: Supervisor can unflag live chat

  Background:
    Given User select General Bank Demo tenant
    Given I open portal
    And Login into portal as an admin of General Bank Demo account
    Given I login as second agent of General Bank Demo
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-3591")
  Scenario: Supervisor desk :: Verify if supervisor can unflag chat
    When User enter chat to support into widget input field
    Then Second Agent has new conversation request
    When Second Agent click on new conversation
    When Second Agent click 'Flag chat' button
    And I select Touch in left menu and Supervisor Desk in submenu
    Then Supervisor Desk Live has new conversation request
    When Agent click On Live Supervisor Desk chat from touch channel
    Then Supervisor Desk Live chat have 'flag on' button
    When Agent click 'Unflag chat' button
    Then Supervisor Desk Live chat from touch channel is unflagged