@Regression
@no_widget
Feature: Available agents on headphones

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2854)
  Scenario: Chat desk: if agent set unavailable he is disappearing from drop-down
    Given I login as agent of Automation Bot
    Given I login as second agent of Automation Bot
    When First Agent click on 'headphones' icon and see 2 available agents
    When First Agent changes status to: Unavailable
    Then First Agent click on 'headphones' icon and see 1 available agents

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2973)
  Scenario: CD :: Agent Desk :: Verify that agent is able to type phone number in "send to" input field(Whatsapp)
    Given I login as agent of Automation Bot
    And click on send external message icon on the right side header
    And agent click Whatsapp message icon button on the top bar
    When agent fill the customer contact number +1 9055197655
    Then agent verify customer contact number is filled
