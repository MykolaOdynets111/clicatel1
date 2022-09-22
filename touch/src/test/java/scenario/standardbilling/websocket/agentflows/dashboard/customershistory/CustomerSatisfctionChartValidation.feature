@orca_api
@no_widget
@start_orca_server
@off_rating_whatsapp
Feature: Dashboard: Customers History: Customer Satisfaction chart

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2323")
    @Regression
  Scenario Outline: Dashboard:: Verify that supervisor can check average CSAT surveys per selected duration of time and specific channel

    Given I login as agent of Standard Billing
    And Setup ORCA whatsapp integration for Standard Billing tenant
    When Send connect to agent message by ORCA
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled | true        |
      | surveyType    | CSAT        |
      | ratingScale   | ONE_TO_FIVE |
      | ratingIcon    | NUMBER      |
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message

    And Agent closes chat
    And Send 5 message by ORCA
    And Agent switches to opened Portal page

    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard

    And Admin filter Customers History by <channelType> channel
    And Admin filter Customers History by Past 4 weeks period
    Then Admin is able to see Customer Satisfaction graphs
    Then Verify vertical Customer Satisfaction graph scale starts from: 0 and ends: 5
    Examples:
      | channelType |
      | whatsapp    |
