Feature:  Dashboard: Launch supervisor desk


  @no_widget @TestCaseId("https://jira.clickatell.com/browse/TPORT-33041")
  Scenario: Verify if supervisor can launch supervisor desk from admin dashboard
    Given I open portal
    And Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    Then Admin can see 'Welcome to the Chat Desk Dashboard'
    When Admin click on Launch Supervisor Desk button
    Then Agent is redirected to supervisor page