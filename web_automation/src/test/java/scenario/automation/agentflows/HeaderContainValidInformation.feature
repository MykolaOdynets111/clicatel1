@egor
Feature: Header contain valid information

  
  Background:
    Given AGENT_FEEDBACK tenant feature is set to true for Automation
    Given User select Automation tenant
    Given I login as agent of Automation
    And Click chat icon

  Scenario: All tie tags available for the test tenant in the drop down
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    And  Time stamp displayed in 24 hours format
    And Header in chat box displayed the icon for channel from which the user is chatting
    And Header in chat box displayed "chatting to "customer name""





