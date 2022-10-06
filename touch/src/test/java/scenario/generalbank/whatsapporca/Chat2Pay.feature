@no_widget
Feature: Chat2Pay ::Chat2PayCustomerClosedChat

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-105084")
    Scenario: CD :: Agent Desk :: Closed Chat :: Chat2Pay :: Verify that the customer can close a chat using //end or //stop keywords, when payment is not concluded
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Agent open c2p form
    And Agent fill c2p form with orderNumber 43333, price 9 and send
    When Send //end message by ORCA
    Then Agent should not see from user chat in agent desk from orca

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1163")
  @Regression
  Scenario: CD :: Agent Desk :: Live Chat :: Chat2Pay :: Verify if payment link expires after 5 mins
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Agent open c2p form
    And Agent fill c2p form with orderNumber 45545, price 10 and send
    When Send Thank you for payment link message by ORCA
    When Wait for 360 second
    Then Agent get 'payment link expired' update is sent to agent desk by C2P

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-105082")
  Scenario: CD :: Agent Desk :: Live Chat :: Chat2Pay :: Verify that the agent gets an options to mark the chat as pending when agent tries to close a chat where payment is not conluded
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Agent open c2p form
    And Agent fill c2p form with orderNumber 43333, price 9 and send
    When Agent sees C2P link with 43333 number in chat body
    And Agent click "End chat" button without window loading
    When Agent get "Cannot close chat" notification modal open
    And Agent click on option "Move to Pending" from notification
    When Agent select "Pending" left menu option
    Then Agent has new conversation request from orca user

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1683")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1660")
    @Regression
  Scenario Outline: CD:: C2P:: Verify if Agent can use Chat2Pay in WhatsApp
    Given I login as agent of General Bank Demo
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from <userType> user
    When Agent click on new conversation request from <userType>
    Then Conversation area becomes active with connect to agent user's message
    Then Agent can see c2p extension icon
    Examples:
      | channelType | userType |
      | whatsapp    | orca     |
      | sms         | sms      |
