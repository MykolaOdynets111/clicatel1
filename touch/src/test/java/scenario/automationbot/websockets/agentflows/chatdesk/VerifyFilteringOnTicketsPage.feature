@Regression
@no_widget
Feature: Filtering : Chat Desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2315)
  Scenario: CD:: Agent Desk:: Verify if agent can receive an error message if search name is not found for closed chats and for tickets

    Given I login as agent of Automation Bot
    When Agent select "Tickets" left menu option
    And Agent search chat "bla bla" on Supervisor desk
    Then Error "There are no available unassigned tickets with given search params.  Please refine your search and try again" message is displayed

    When Agent select "Closed" left menu option
    And Agent click on search button in left menu
    And Agent types a customer name "bla bla" on the search field
    Then Agent receives an error message "Sorry, no results found. Please refine your search and try again"
