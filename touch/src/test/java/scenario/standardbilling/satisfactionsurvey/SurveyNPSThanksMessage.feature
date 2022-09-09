@no_widget
@off_rating_whatsapp
@no_chatdesk
@start_orca_server
@orca_api
Feature: Satisfaction Survey

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-18593")
  Scenario Outline: Verify if user receives customized Thank You message in response to answering survey
    Given I login as agent of Standard Billing
    And Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled        | true              |
      | surveyType           | NPS               |
      | ratingScale          | ZERO_TO_TEN       |
      | ratingIcon           | NUMBER            |
      | commentEnabled       | true              |
      | thanksMessageEnabled | true              |
    When Send connect to agent message by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    And Send 7 message by ORCA
    And Verify Orca returns Thanks, please leave a message on how we can improve our service. response during 40 seconds
    Examples:
      | channelType         |
      | whatsapp            |