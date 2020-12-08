Feature: Filtering : Chat Desk

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-30907) @no_widget
  Scenario: Verify if agent can receive an error message if search name is not found for closed chats and for tickets
    Given I login as agent of Automation Bot
    When Agent select "Tickets" left menu option
    And Agent search ticket with a customer name "bla bla"
    Then Agent receives an error message "Sorry, no results found. Please refine your search and try again"
    When Agent select "Closed" left menu option
    And Agent click on search button in left menu
    And Agent types a customer name "bla bla" on the search field
    Then Agent receives an error message "Sorry, no results found. Please refine your search and try again"

  @agent_support_hours
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-28061) @no_widget
  Scenario: Verify if agent can filter tickets using date range
    Given I login as agent of Automation Bot
    And Agent select "Tickets" left menu option
    And Set agent support hours with day shift
    And Agent receives a few conversation requests
    When Agent filter by 0 year 0 month and 1 days ago start date and today's end date
    Then Verify filtered tickets dates are fitted by filter for agent