@no_widget
@orca_api
@start_orca_server
Feature: HSMTemplate

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1167")
  @Regression
  Scenario: CD :: Agent Desk :: HSM :: Verify if an agent can initiate a chat by sending an HSM template to a WhatsApp via ORCA user
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Agent closes chat
    And Agent click Whatsapp message icon button on the top bar
    And Agent fill the customer contact number
    When Agent select "Welcome Notification" in Chanel Template
    And Agent insert the Variable type "Ravindra A" for template
    When Agent click send button in HSM form
    Then Verify Orca returns welcome_notification HSM sent by Agent during 40 seconds with parameters
      | 1 | Ravindra A |
    When Send I got your HSM message by ORCA
    And Agent has new conversation request from orca user
    Then Agent click on new conversation request from orca
    Then Conversation area becomes active with I got your HSM user's message in it for agent
    When Agent responds with hello to User
    Then Verify Orca returns hello response during 40 seconds


  @TestCaseId("https://jira.clickatell.com/browse/CCD-1161")
  @Regression
  Scenario: CD:: Agent Desk :: Closed Chat :: HSM :: Verify if agent can initiate a chat with WhatsApp via orca user by sending HSM template from Closed tab
    Given I login as agent of General Bank Demo
    When Setup ORCA whatsapp integration for General Bank Demo tenant
    And Agent generate closed ORCA whatsapp chat 24 hours ago
    When Agent select "Closed" left menu option
    And Agent searches and selects chat from orca in chat history list
    When Agent click start chat button
    When Agent select "Welcome Notification" in Chanel Template
    And Agent insert the Variable type "Dummy Parameter" for template
    When Agent click send button in HSM form
    Then Verify Orca returns welcome_notification HSM sent by Agent during 40 seconds with parameters
      | 1 | Dummy Parameter |
    When Send I got your HSM message by ORCA
    And Agent select "Live Chats" left menu option
    And Agent has new conversation request from orca user
    Then Agent click on new conversation request from orca
    Then Conversation area becomes active with I got your HSM user's message in it for agent
    When Agent responds with hello to User
    Then Verify Orca returns hello response during 40 seconds


  @TestCaseId("https://jira.clickatell.com/browse/CCD-1160")
  @Regression
  Scenario: CD :: Agent Desk :: HSM :: Verify if Agent send HSM template to  WhatsApp via orca user then HSM label is displayed in the conversation area
    Given I login as agent of General Bank Demo
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    When Agent closes chat
    And Agent click Whatsapp message icon button on the top bar
    And Agent fill the customer contact number
    When Agent select "Welcome Notification" in Chanel Template
    And Agent insert the Variable type "HSM in Chat" for template
    When Agent click send button in HSM form
    Then Verify Orca returns welcome_notification HSM sent by Agent during 40 seconds with parameters
      | 1 | HSM in Chat |
    When Send I got your HSM message by ORCA
    And Agent has new conversation request from orca user
    Then Agent click on new conversation request from orca
    Then Agent can see message with HSM label in Conversation area
