@no_widget
@start_orca_server
@orca_api
Feature: Satisfaction Survey

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19270")
  Scenario Outline: verify if user has an option to skip the survey for Whatsapp NPS survey type - Customer Feedback turned OFF in Flow
    Given I login as agent of Standard Billing
    And Setup ORCA <channelType> integration for Standard Billing tenant
    When Send connect to agent message by ORCA
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled | true        |
      | surveyType    | CSAT        |
      | ratingScale   | ONE_TO_FIVE |
      | ratingIcon    | NUMBER      |
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    And Survey Management page should be shown
    And Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Admin selects NPS survey type for <channelType> survey form
    And Agent click save survey configuration button for <channelType> survey form
    And Wait for 5 second
    Then I select Touch in left menu and Agent Desk in submenu
    And Agent click on last opened conversation request from orca
    And Wait for 5 second
    And Agent closes chat
    And Send skip message by ORCA
    And Verify Orca returns Thank you. Please don't hesitate to reach out if you ever need help! response during 40 seconds
    Examples:
      | channelType |
      | whatsapp    |
      | abc         |

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-18595")
  Scenario Outline: verify if agent is able to see NPS rating from the survey that the customer completed on the chat view
    Given I login as agent of Standard Billing
    And Setup ORCA <channelType> integration for Standard Billing tenant
    When Send connect to agent message by ORCA
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled | true        |
      | surveyType    | CSAT        |
      | ratingScale   | ONE_TO_FIVE |
      | ratingIcon    | NUMBER      |
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    And Survey Management page should be shown
    And Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Admin selects NPS survey type for <channelType> survey form
    And Agent click save survey configuration button for <channelType> survey form
    And Wait for 5 second
    Then I select Touch in left menu and Agent Desk in submenu
    And Agent click on last opened conversation request from orca
    And Wait for 5 second
    And Agent closes chat
    And Send skip message by ORCA
    When Agent select "Closed" left menu option
    And Agent searches and selects chat from orca in chat history list
    And Agent open first 'History view'
    And Agent sees the particular message skip in History Details window
    Examples:
      | channelType |
      | whatsapp    |
      | abc         |

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-121014")
  Scenario Outline: CD:: Survey:: Dashboard:: Verify If the client enables NPS Survey, there will be no update in the agent scores in the CSAT in agent performance tab
    Given I login as agent of Standard Billing
    And Setup ORCA <channelType> integration for Standard Billing tenant
    When Send connect to agent message by ORCA
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled | true        |
      | surveyType    | NPS        |
      | ratingScale   | ZERO_TO_TEN |
      | ratingIcon    | NUMBER      |
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by Whatsapp channel
    Then Admin is able to see the average CSAT survey response converted to 0-10
    And I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Wait for 5 second
    And Agent closes chat
    And Send 10 message by ORCA
    And Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by Whatsapp channel
    Then Admin is able to see the new average CSAT survey response converted to 0-10
    And Admin is able to see the same average CSAT rating for NPS response
    Examples:
      | channelType |
      | whatsapp    |
      | abc         |