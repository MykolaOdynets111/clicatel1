@Regression
Feature: Supervisor desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2838")
  Scenario: CD :: Dashboard :: Configure :: Download Transcript :: Verify if error message is displayed when no results are matched (search using only the filters without entering a MSISDN )
    Given I login as agent of General Bank Demo
    When I select Touch in left menu and Supervisor Desk in submenu
    And Agent search chat NoChatsErrorMessage on Supervisor desk
    Then Error "Sorry, no results found.Please refine your search and try again" message is displayed
    When Agent select "Tickets" left menu option
    And Agent search chat NoChatsErrorMessage on Supervisor desk
    Then Error "There are no available unassigned tickets with given search params.  Please refine your search and try again" message is displayed
    When Agent select "Closed" left menu option
    And Agent search chat NoChatsErrorMessage on Supervisor desk
    Then Error "Sorry, no results found.Please refine your search and try again" message is displayed