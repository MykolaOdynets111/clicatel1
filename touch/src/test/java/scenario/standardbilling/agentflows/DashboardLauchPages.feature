Feature:  Dashboard: Launch pages

  @no_widget @TestCaseId("https://jira.clickatell.com/browse/CCD-2804")
  Scenario: Verify if supervisor can launch supervisor desk from admin dashboard
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    Then Admin can see 'Welcome to the Chat Desk Dashboard'
    When Admin click on Launch Supervisor Desk button
    Then Agent is redirected to supervisor page

  @no_widget @TestCaseId("https://jira.clickatell.com/browse/CCD-2827")
  Scenario: Verify if supervisor is able to launch agent desk from dashboard view
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    Then Admin can see 'Welcome to the Chat Desk Dashboard'
    When Admin click on Launch Agent Desk button
    Then Agent is redirected to chatdesk page

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2836")
  @Regression
  Scenario: Verify if supervisor is able to launch department management page from dashboard view
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    Given Admin select TOUCH in left menu and Dashboard in submenu
    Then Admin can see 'Welcome to the Chat Desk Dashboard'
    Then Admin click on Departments Management button
    And Departments Management page should be shown