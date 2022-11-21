@Regression
@orca_api
Feature: Last agent activate

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2419")
  Scenario: CD :: Agent Desk :: Live Chat :: Verify if returning customer can be assigned to the last agent that they spoke to
    Given Setup ORCA abc integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    When I login as second agent of General Bank Demo
    And Agent click "End chat" button
    Then End chat popup for agent should be opened
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk from orca
    When Send connect to Support message by ORCA
    Then Second agent should not see from user chat in agent desk from orca
    Then Agent has new conversation request from orca user