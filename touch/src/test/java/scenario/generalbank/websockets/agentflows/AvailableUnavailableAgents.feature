@Regression
@no_widget
Feature: Available/Unavailable agents

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2854")
  Scenario: CD:: Agent Desk :: Verify if agent set unavailable he is disappearing from drop-down
    Given I login as agent of General Bank Demo
    Given I login as second agent of General Bank Demo
    When First Agent click on 'headphones' icon and see 2 available agents
    When First Agent changes status to: Unavailable
    Then First Agent click on 'headphones' icon and see 1 available agents

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2973")
  Scenario: CD :: Agent Desk :: Verify that agent is able to type phone number in "send to" input field(Whatsapp)
    Given I login as agent of General Bank Demo
    And Agent click Whatsapp message icon button on the top bar
    When Agent fill the customer contact number +1 9055197655
    Then Agent verify customer contact number +1 905 519 7655 is filled

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2984")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2984")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2683")
  Scenario: CD :: Agent Desk :: Verify that clicking on radio button near "Unavailable" - red dot is appears on agent profile picture

    Given I login as agent of General Bank Demo
    When Agent changes status to: Unavailable
    Then Verify Agent status: Unavailable is displayed on the icon
    When Agent changes status to: Available
    Then Verify Agent status: Available is displayed on the icon
