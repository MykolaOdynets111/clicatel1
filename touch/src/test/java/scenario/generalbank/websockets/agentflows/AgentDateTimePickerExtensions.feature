Feature: Agent Date Time Picker Extension

  @TestCaseId("https://jira.clickatell.com/browse/CCD-860")
    @Regression
    @no_widget
  Scenario Outline: CD :: <channelType> :: Agent Desk :: Live Chat :: Verify the "Date/Time picker" extension cards for the tenant
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    And Agent creates tenant extension with label Schedule Appointment with picker and name WaveBank Sranch
    And I login as agent of General Bank Demo
    When Send chat to agent message by ORCA
    And Agent has new conversation request from <userType> user
    And Agent click on new conversation request from <userType>
    And Conversation area becomes active with chat to agent user's message
    And Agent open c2p form
    And Agent send date picker form with name WaveBank Sranch and send
    And Agent sees date picker link with Schedule Appointment with picker name in chat body
    Examples:
      | channelType | userType |
      | ABC         | orca     |