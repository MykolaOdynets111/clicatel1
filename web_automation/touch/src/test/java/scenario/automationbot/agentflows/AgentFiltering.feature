Feature: Filtering : Chat Desk

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-30909) @no_widget
  Scenario: Verify if agent can receive an error message if search name is not found for tickets
    Given I login as agent of Automation Bot
    When Agent select "Tickets" left menu option
    And Agent search ticket with a customer name "bla bla"
    Then Agent receives an error message "Sorry, no results found. Please refine your search and try again"

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-30907) @no_widget
  Scenario: Verify if agent can receive an error message if search name is not found for closed chats
    Given I login as agent of Automation Bot
    When Agent select "Closed" left menu option
    And Agent click on search button in left menu
    And Agent types a customer name "bla bla" on the search field
    Then Agent receives an error message "Sorry, no results found. Please refine your search and try again"