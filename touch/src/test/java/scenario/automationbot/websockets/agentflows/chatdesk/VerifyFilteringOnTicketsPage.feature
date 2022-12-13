@Regression
@no_widget
Feature: Filtering : Chat Desk

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2315")
  Scenario: CD:: Agent Desk:: Verify if agent can receive an error message if search name is not found for closed chats and for tickets

    Given I login as agent of Automation Bot
    When Agent select "Tickets" left menu option
    And Agent search chat "bla bla" on Supervisor desk
    Then Error "There are no available unassigned tickets with given search params.  Please refine your search and try again" message is displayed

    When Agent select "Closed" left menu option
    And Agent click on search button in left menu
    And Agent types a customer name "bla bla" on the search field
    Then Agent receives an error message "Sorry, no results found. Please refine your search and try again"

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2370")
  @support_hours
  Scenario: CD:: Agent Desk:: Verify if agent can filter tickets using date range

    Given Setup ORCA Whatsapp integration for Automation Bot tenant
    Given I login as agent of Automation Bot
    When Agent select "Tickets" left menu option
    And Set agent support hours with day shift
    And Send 2 messages chat to agent by ORCA
    When Admin filter by 0 year 0 month and 1 days ago start date and 0 year 0 month and 0 days ago end date
    Then Verify filtered tickets dates are fitted by filter for agent
