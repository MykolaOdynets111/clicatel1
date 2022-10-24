@no_widget
@Regression
@orca_api
Feature: Chat2Pay ::Chat2PayCustomerClosedChat

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1426")
  Scenario: CD :: Agent Desk :: Closed Chat :: Chat2Pay :: Verify that the customer can close a chat using //end or //stop keywords, when payment is not concluded
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And Agent creates tenant extension with label and name
      | extensionType | label              | name        |
      | CHAT_2_PAY    | Send C2P Extension | Chat to Pay |
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Agent open c2p form
    And Agent fill c2p form with orderNumber 43333, price 9 and send
    When Send //end message by ORCA
    Then Agent should not see from user chat in agent desk from orca

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1163")
    @skip
#    it's not stable and takes too much time
  Scenario: CD :: Agent Desk :: Live Chat :: Chat2Pay :: Verify if payment link expires after 5 mins
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And Agent creates tenant extension with label and name
      | extensionType | label              | name        |
      | CHAT_2_PAY    | Send C2P Extension | Chat to Pay |
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Agent open c2p form
    And Agent fill c2p form with orderNumber 45545, price 10 and send
    When Send Thank you for payment link message by ORCA
    When Wait for 360 second
    Then Agent get Payment link expired update is sent to agent desk by C2P

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1359")
  Scenario: CD :: Agent Desk :: Live Chat :: Chat2Pay :: Verify that the agent gets an options to mark the chat as pending when agent tries to close a chat where payment is not conluded
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And Agent creates tenant extension with label and name
      | extensionType | label              | name        |
      | CHAT_2_PAY    | Send C2P Extension | Chat to Pay |
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

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1660")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-1683")
  Scenario Outline: CD:: C2P:: Verify if Agent can use Chat2Pay in WhatsApp
    Given I login as agent of General Bank Demo
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    And Agent creates tenant extension with label and name
      | extensionType | label              | name        |
      | CHAT_2_PAY    | Send C2P Extension | Chat to Pay |
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from <userType> user
    When Agent click on new conversation request from <userType>
    Then Conversation area becomes active with connect to agent user's message
    Then Agent can see c2p extension icon
    Examples:
      | channelType | userType |
      | sms         | sms      |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1687")
  Scenario: CD::SMS :: Chat2Pay :: Verify that when agent cancels a payment, the customer receives a text notification for the cancellation via SMS channel
    Given I login as agent of General Bank Demo
    And Setup ORCA sms integration for General Bank Demo tenant
    And Agent creates tenant extension with label and name
      | extensionType | label              | name        |
      | CHAT_2_PAY    | Send C2P Extension | Chat to Pay |
    When Send connect to Support message by ORCA
    And Agent has new conversation request from sms user
    And Agent click on new conversation request from sms
    And Conversation area becomes active with connect to Support user's message
    And Agent open c2p form
    And Agent fill c2p form with orderNumber 45545, price 10 and send
    And Agent click 'Pending On' chat button
    And Agent select "Pending" left menu option
    And Agent click on new conversation request from sms
    And Agent clicks on the cancel payment button on the payment card
    Then Agent get Payment link cancelled update is sent to agent desk by C2P
    And Agent select "Pending" left menu option

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1708")
  Scenario: CD :: SMS :: Chat2Pay :: Verify that payment received update is treated a new user event via SMS channel
    Given I login as agent of General Bank Demo
    Given Setup ORCA sms integration for General Bank Demo tenant
    And Agent creates tenant extension with label and name
      | extensionType | label              | name        |
      | CHAT_2_PAY    | Send C2P Extension | Chat to Pay |
    When Send connect to chat 2 pay message by ORCA
    And Agent has new conversation request from sms user
    And Agent click on new conversation request from sms
    And Conversation area becomes active with connect to chat 2 pay user's message
    And Agent open c2p form
    And Agent fill c2p form with orderNumber 45545, price 10 and send
    And Agent click 'Pending On' chat button
    And Agent select "Pending" left menu option
    And Agent click on new conversation request from sms
    And Send Thank you for payment link message by ORCA
    Then Agent gets pending to live chat dialog with header Chat moved to live
    And Agent clicks on go to chat button
    And Agent click on new conversation request from sms
    And Conversation area becomes active with connect to chat 2 pay user's message