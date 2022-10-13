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

  @skip
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

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2620")
    @Regression
    @no_widget
    @start_orca_server
  Scenario Outline: CD :: Agent Desk :: Live Chat :: <channelType> :: Character Count :: Verify if agent message send contains only Unicode characters then the total count should be out of 70
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send chat to agent message by ORCA
    And Agent has new conversation request from <userType> user
    And Agent click on new conversation request from <userType>
    And Conversation area becomes active with chat to agent user's message
    And Agent click on emoji icon
    Then Agent is able to see the typing indicator as 70 Characters on chatdesk
    When Agent clears and types characters الْأَبْجَدِيَّة الْعَرَبِيَّة in conversation input field on chatdesk
    Then Agent is able to see the typing indicator as 70 Characters on chatdesk
    When Agent clears and types characters ふりがな in conversation input field on chatdesk
    Then Agent is able to see the typing indicator as 70 Characters on chatdesk
    And Agent click send button
    And Verify Orca returns ふりがな response during 40 seconds
    Examples:
      | channelType | userType |
      | SMS         | sms      |

  @skip
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2619")
    @Regression
    @no_widget
  Scenario Outline: CD :: Agent Desk :: Live Chat :: <channelType> :: Character Count :: Verify if agent can see the count of the number of text typed out of 160
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    And I login as agent of General Bank Demo
    When Send chat to agent message by ORCA
    And Agent has new conversation request from <userType> user
    And Agent click on new conversation request from <userType>
    And Conversation area becomes active with chat to agent user's message
    And Agent is able to see the typing indicator as 13 / 160 Characters on chatdesk
    And Agent click on emoji icon
    And Agent is able to see the typing indicator as 70 Characters on chatdesk
    When Agent clears and types characters Please rate your experience with our agent Please rate your experience with our agent Please rate your experience with our agent Please rate your experience with Please rate with in conversation input field on chatdesk
    Then Agent is able to see the typing indicator as 178 / 170 Characters on chatdesk
    Examples:
      | channelType | userType |
      | SMS         | sms      |