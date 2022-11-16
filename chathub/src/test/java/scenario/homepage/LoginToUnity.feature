Feature: Integrations feature

  @TestCaseId("https://jira.clickatell.com/browse/CCH-162")
  Scenario: CH :: My Workspace: If user clicks on 'Integrations', the application will redirect to Integrations landing page.
    Given I login to Unity as QA_C2P_USER_ONE
    And User clicks on My Workspace page link
    When User clicks on Integrations Tab
    Then Application redirects to Integrations page
    
