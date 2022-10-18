@no_widget
@Regression
Feature: Login to unity

  @TestCaseId("")
  Scenario: CCH : Login to Unity
    Given I login as agent of General Bank Demo
    Given Setup ORCA abc integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    Then Valid image for abc integration are shown in left menu with chat
    And Agent should see abcHeader icon in active chat header