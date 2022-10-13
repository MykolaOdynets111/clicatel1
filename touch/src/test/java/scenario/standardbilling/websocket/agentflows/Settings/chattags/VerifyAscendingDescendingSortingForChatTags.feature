@no_widget
@Regression
Feature: Dashboard: Settings: Chat Tags : Ascending and Descending sorting

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2794")
  @Regression
  Scenario Outline: CD :: Dashboard :: Settings :: Chat Tags :: Verify if admin is able select filter option (ascending/descending)

    Given I login as agent of Standard Billing
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Chat Tags page
    When Sort <column> column by asc
    Then Verify <column> column is sorted by ASC
    When Sort <column> column by desc
    Then Verify <column> column is sorted by DESC
    Examples:
      | column     |
      | Tag Name   |
      | Created on |
      | Created by |
      | Usage      |
      | Last Used  |
      | Enabled    |