Feature: Integrations feature

  @TestCaseId("https://jira.clickatell.com/browse/CCH-162")
  Scenario: CH :: My Workspace: If user clicks on 'Integrations', the application will redirect to Integrations landing page.
    When I login to Unity with email "chat2payqauser11+echo1@gmail.com" and password "Password#1"
    And User clicks on My Workspace page link
    When User clicks on Integrations Tab
    Then Application redirects to Integrations page
    
