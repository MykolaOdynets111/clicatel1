@Regression
Feature: Available agents on headphones

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2854)
  Scenario: CD:: Agent Desk :: Verify if agent set unavailable he is disappearing from drop-down
    Given I login as agent of Automation Bot
    Given I login as second agent of Automation Bot
    When First Agent click on 'headphones' icon and see 2 available agents
    When First Agent changes status to: Unavailable
    Then First Agent click on 'headphones' icon and see 1 available agents

