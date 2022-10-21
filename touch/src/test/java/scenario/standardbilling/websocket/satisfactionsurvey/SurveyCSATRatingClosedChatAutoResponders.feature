@no_widget
@off_rating_sms
@off_rating_whatsapp
@off_rating_abc
@orca_api
@start_orca_server
  @Regression
Feature: Satisfaction Survey

  Background:
    Given I login as agent of Standard Billing
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Auto Responders page
    And Wait for auto responders page to load
    And Agent click expand arrow for End Chat message auto responder
    And Click "Reset to default" button for End Chat message auto responder

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2125")
  Scenario Outline: CD:: Settings :: Survey :: Verify the error message when end user send rating as "-1" or "+1" for NPS/CSAT -
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | ratingEnabled        | true        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    And Survey Management page should be shown
    And Customize your survey "Great, thanks - could you please rate our service from 0 - 10. 0 being "Very unsatisfied" and 10 being "Very satisfied". Simply type "skip" keyword to skip the survey." question
    And Agent click save survey configuration button for <channelType> survey form
    And Send connect to agent message by ORCA
    And I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    And Verify Orca returns question update response during 40 seconds
    Then Send -1 message by ORCA
    And Verify Orca returns survey question response 2 number of times during 40 seconds
    Examples:
      | channelType |
      | ABC         |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1703")
  Scenario Outline: CD:: <channelType>:: Settings :: Survey :: Verify that user can 'rate and leave comment' for SMS CSAT survey type if close chat with //end command
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | ratingEnabled        | true        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    And Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Agent switch "Allow customer to give thank message" in survey management
    And Agent switch "Allow customer to leave a note" in survey management
    And Customize your survey "Great, thanks - could you please rate our service from 0 - 10. 0 being "Very unsatisfied" and 10 being "Very satisfied". Simply type "skip" keyword to skip the survey." question
    And Customize your survey notes message to Thanks, please leave a message on how we can improve our service. Simply type "skip" keyword to skip this step.
    And Customize your survey thank message to Thank you for taking the time to provide us with your feedback.
    And Agent click save survey configuration button for <channelType> survey form
    When Send connect to agent message by ORCA
    Then I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from <channelType> user
    And Agent click on new conversation request from <channelType>
    And Conversation area becomes active with connect to agent user's message
    And Send //end message by ORCA
    And Verify Orca returns question update response during 40 seconds
    And Send 5 message by ORCA
    And Verify Orca returns notes update response during 40 seconds
    And Send happy with your service message by ORCA
    And Verify Orca returns thanks message update response during 40 seconds
    Examples:
      | channelType |
      | SMS         |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1778")
  Scenario Outline: CD:: <channelType>:: Settings :: Survey :: Verify if User should be able to enter a Skip keyword to cancel the survey
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | surveyType           | NPS         |
      | ratingScale          | ZERO_TO_TEN |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | ratingEnabled        | true        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    And Customize your survey "Great, thanks - could you please rate our service from 0 - 10. 0 being "Very unsatisfied" and 10 being "Very satisfied". Simply type "skip" keyword to skip the survey." question
    And Customize your survey thank message to Thank you for taking the time to provide us with your feedback.
    And Agent click save survey configuration button for <channelType> survey form
    When Send connect to agent message by ORCA
    And I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from <channelType> user
    And Agent click on new conversation request from <channelType>
    And Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    And Verify Orca returns question update response during 40 seconds
    And Send skip message by ORCA
    Then Verify Orca returns thanks message update response during 40 seconds
    And Verify Orca returns Thank you. Please don't hesitate to reach out if you ever need help! response during 40 seconds
    Examples:
      | channelType |
      | SMS         |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2443")
  Scenario Outline: CD:: Survey:: verify if user has an option to skip the survey for Whatsapp NPS survey type - Customer Feedback turned OFF in Flow
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | surveyType           | NPS         |
      | ratingScale          | ZERO_TO_TEN |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | ratingEnabled        | true        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    When Send connect to agent message by ORCA
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    And Send skip message by ORCA
    Then Verify Orca returns Thank you. Please don't hesitate to reach out if you ever need help! response during 40 seconds
    Examples:
      | channelType |
      | Whatsapp    |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2389")
  Scenario Outline: Verify if user receives customized Thank You message in response to answering survey
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | surveyType           | NPS         |
      | ratingScale          | ZERO_TO_TEN |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | ratingEnabled        | true        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    And Customize your survey notes message to Thanks, please leave a message on how we can improve our service. Simply type "skip" keyword to skip this step.
    And Agent click save survey configuration button for <channelType> survey form
    When Send connect to agent message by ORCA
    And I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    And Send 7 message by ORCA
    Then Verify Orca returns notes update response during 40 seconds
    Examples:
      | channelType |
      | whatsapp    |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1170")
  Scenario: CD:: Dashboard: Dashboard-Customers_Overview:: Verify if admin can open Customers History with CSAT customer survey
    Given Setup ORCA abc integration for Standard Billing tenant
    And Update survey management chanel abc settings by ip for Standard Billing
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | ratingEnabled        | true        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    And Customize your survey "Great, thanks - could you please rate our service from 0 - 10. 0 being "Very unsatisfied" and 10 being "Very satisfied". Simply type "skip" keyword to skip the survey." question
    And Agent click save survey configuration button for abc survey form
    When Send connect to agent message by ORCA
    And I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    And Send 5 message by ORCA
    And Verify Orca returns question update response during 40 seconds
    And Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by channel and period
      | Apple Business Chat | Past week |
    And Admin is able to see Customer Satisfaction,Chats by Channel,Past Sentiment graphs

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2289")
  Scenario Outline: Customer History:: NPS Score:: Verify if Net Promoter Score can display a negative rating
    And Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | surveyType           | NPS         |
      | ratingScale          | ZERO_TO_TEN |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | ratingEnabled        | true        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    And Customize your survey "Great, thanks - could you please rate our service from 0 - 10. 0 being "Very unsatisfied" and 10 being "Very satisfied". Simply type "skip" keyword to skip the survey." question
    And Agent click save survey configuration button for <channelType> survey form
    And Send connect to agent message by ORCA
    And I select Touch in left menu and Agent Desk in submenu
    Then Agent has new conversation request from <userType> user
    And Agent click on new conversation request from <userType>
    And Conversation area becomes active with connect to agent user's message
    When Agent closes chat
    And Verify Orca returns question update response during 40 seconds
    And Send 1 message by ORCA
    And Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    Then Admin is able to see Net Promoter Score graphs
    Then Admin see the Net Promoter Score as negative
    Examples:
      | channelType | userType|
      | sms         | sms     |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1456")
  Scenario: CD:: Dashboard: Dashboard-Customers_Overview:: Verify if admin can open Customers History with NPS customer survey
    Given Setup ORCA abc integration for Standard Billing tenant
    And Update survey management chanel abc settings by ip for Standard Billing
      | surveyType           | NPS         |
      | ratingScale          | ZERO_TO_TEN |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | ratingEnabled        | true        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    And Customize your survey "Great, thanks - could you please rate our service from 0 - 10. 0 being "Very unsatisfied" and 10 being "Very satisfied". Simply type "skip" keyword to skip the survey." question
    And Agent click save survey configuration button for abc survey form
    When Send connect to agent message by ORCA
    Then I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    And Verify Orca returns question update response during 40 seconds
    And Send 5 message by ORCA
    And Agent switches to opened Portal page
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Customers History on dashboard
    And Admin filter Customers History by channel and period
      | Apple Business Chat | Past week |
    And Admin is able to see Net Promoter Score,Chats by Channel,Past Sentiment graphs