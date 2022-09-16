Feature: Dashboard: Customer History

  @no_widget
    @off_survey_management
    @off_rating_whatsapp
    @off_rating_abc
    @orca_api
    @TestCaseId("https://jira.clickatell.com/browse/TPORT-50411")
  Scenario Outline: Customer History:: NPS Score:: Verify if Net Promoter Score can display a negative rating
    Given I login as agent of General Bank Demo
    And Setup ORCA <channelType> integration for General Bank Demo tenant
    And Update survey management chanel <channelType> settings by ip for General Bank Demo
      | ratingEnabled | true        |
      | surveyType    | NPS         |
      | ratingScale   | ZERO_TO_TEN |
      | ratingIcon    | NUMBER      |
    And Send connect to agent message by ORCA
    Then Agent has new conversation request from <userType> user
    And Agent click on new conversation request from <userType>
    And Conversation area becomes active with connect to agent user's message
    When Agent closes chat
    And Send 0 message by ORCA
    And Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    Then Admin is able to see Net Promoter Score graphs
    Then Admin see the Net Promoter Score as negative
    Examples:
      | channelType | userType|
      | sms         | sms     |
      | whatsapp    | orca    |
      | abc         | orca    |