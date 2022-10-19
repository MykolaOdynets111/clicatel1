@no_widget
@off_rating_whatsapp
@off_rating_abc
@off_rating_sms
@orca_api
@start_orca_server
@Regression
Feature: Satisfaction Survey

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2381")
  Scenario Outline: CD::Survey:: Agent_Desk:: verify if agent is able to see NPS rating from the survey that the customer completed on the chat view
    Given I login as agent of Standard Billing
    And Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | ratingEnabled        | true        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    When Send connect to agent message by ORCA
    Then Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    And Survey Management page should be shown
    And Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Admin selects NPS survey type for <channelType> survey form
    And Agent click save survey configuration button for <channelType> survey form
    Then I select Touch in left menu and Agent Desk in submenu
    And Agent click on last opened conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    And Send 10 message by ORCA
    When Agent select "Closed" left menu option
    And Agent searches and selects chat from orca in chat history list
    And Agent open first 'History view'
    And Agent sees the particular message skip in History Details window
    Examples:
      | channelType |
      | whatsapp    |
      | abc         |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1861")
  Scenario Outline: CD:: Survey:: Dashboard:: Verify If the client enables NPS Survey, there will be no update in the agent scores in the CSAT in agent performance tab
    Given I login as agent of Standard Billing
    And Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | surveyType           | NPS         |
      | ratingScale          | ZERO_TO_TEN |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | ratingEnabled        | true        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    When Send connect to agent message by ORCA
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by <channelFilterType> channel
    Then Admin is able to see the average CSAT survey response converted to 0-100
    And I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    And Send 10 message by ORCA
    And Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by <channelFilterType> channel
    Then Admin is able to see the new average CSAT survey response converted to 0-100
    And Admin is able to see the same average CSAT rating for NPS response
    Examples:
      | channelType | channelFilterType   |
      | whatsapp    | Whatsapp            |
      | abc         | Apple Business Chat |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1170")
  Scenario: CD:: Dashboard: Dashboard-Customers_Overview:: Verify if admin can open Customers History with CSAT customer survey
    Given I login as agent of Standard Billing
    And Setup ORCA abc integration for Standard Billing tenant
    And Update survey management chanel abc settings by ip for Standard Billing
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | ratingEnabled        | true        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    When Send connect to agent message by ORCA
    Then I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    And Send 5 message by ORCA
    And Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by channel and period
      | Apple Business Chat | Past week |
    And Admin is able to see Customer Satisfaction,Chats by Channel,Past Sentiment graphs

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1685")
  Scenario Outline: CD:: <channelType>:: Survey:: CSAT:: Verify if survey rating updates the % value for CSAT scores in the Agent performance reports
    Given I login as agent of Standard Billing
    And Setup ORCA <channelType> integration for Standard Billing tenant
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by channel and period
      | <channelFilter> | Past week    |
      | <channelFilter> | Past 2 weeks |
    Then Admin is able to see the average CSAT survey response converted to 0-100
    And Navigate to Surveys page
    When Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Agent switch "Allow customer to give thank message" in survey management
    And Agent switch "Allow customer to leave a note" in survey management
    And Agent click save survey configuration button for <channelType> survey form
    When Send connect to agent message by ORCA
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | ratingEnabled        | true        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    Then I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from <channelUserType> user
    And Agent click on new conversation request from <channelUserType>
    And Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    And Send 5 message by ORCA
    And Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by channel and period
      | <channelFilter> | Past week    |
      | <channelFilter> | Past 2 weeks |
    And Admin is able to see the new average CSAT survey response converted to 0-100
    And Admin is able to see the different average CSAT rating for CSAT response
    Examples:
      | channelType | channelUserType | channelFilter       |
      | ABC         | orca            | Apple Business Chat |
      | SMS         | sms             | SMS                 |