@orca_api
Feature: Agent emoticons

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2926")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1707")
  @Regression
  @no_widget
  Scenario Outline: CD::<channelType>::Emoji::Verify if Agent can send emoji message to a User via <channelType>
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send chat to agent message by ORCA
    And Agent has new conversation request from <userType> user
    And Agent click on new conversation request from <userType>
    And Conversation area becomes active with chat to agent user's message
    And Agent click on emoji icon
    Then Agent response with emoticon to User
    And Sent emoji is displayed on chatdesk
    Examples:
      | channelType | userType |
      | Whatsapp    | orca     |
      | SMS         | sms      |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1788")
    @Regression
    @no_widget
  Scenario Outline: CD:: <channelType>:: Emoji::Verify if user is able to send emoji to agent via <channelType>
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send chat to agent message by ORCA
    And Agent has new conversation request from <userType> user
    And Agent click on new conversation request from <userType>
    And Conversation area becomes active with chat to agent user's message
    When Send 😃 message by ORCA
    And Sent emoji from user 😃 is displayed on chatdesk
    Examples:
      | channelType | userType |
      | SMS         | sms      |