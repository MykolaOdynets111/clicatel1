Feature: Header contain valid information

  Background:
    Given User select Automation tenant
    Given I login as agent of Automation
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-1637")
  Scenario: The header should have the icon for channel, user name, time stamp
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    And  Time stamp displayed in 24 hours format
    And Header in chat box displayed the icon for channel from which the user is chatting
    And Header in chat box displayed "chatting to "customer name""





