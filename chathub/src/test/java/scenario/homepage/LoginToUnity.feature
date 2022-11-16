Feature: Integrations feature

  @TestCaseId("https://jira.clickatell.com/browse/CCH-163")
  Scenario: CH :: My Workspace: If user clicks on 'Integrations', the application will redirect to Integrations landing page.
    Given I login to Unity as QA_C2P_USER_ONE
    And User clicks on My Workspace page link
    When User clicks on Integrations Tab
    Then Application redirects to Integrations page

  @TestCaseId("https://jira.clickatell.com/browse/CCH-437")
  Scenario: CH :: My Workspace:: Integrations card should be first card on the Numbers & Integrations section
    Given I login to Unity as QA_C2P_USER_ONE
    And User clicks on My Workspace page link
    Then Integrations card is displayed as first card
    
