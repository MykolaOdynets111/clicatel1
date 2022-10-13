@no_widget
@off_rating_sms
@off_rating_whatsapp
@off_rating_abc
@orca_api
@start_orca_server
Feature: Satisfaction Survey

  Background:
    Given I login as agent of Automation Bot
    And I select Touch in left menu and Dashboard in submenu
    When Navigate to Auto Responders page
    And Wait for auto responders page to load
    And Agent click expand arrow for End Chat message auto responder
    And Click "Reset to default" button for End Chat message auto responder

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2125")
    @Regression
  Scenario Outline: CD:: Settings :: Survey :: Verify the error message when end user send rating as "-1" or "+1" for NPS/CSAT -
    Given Setup ORCA <channelType> integration for Automation Bot tenant
    And Update survey management chanel <channelType> settings by ip for Automation Bot
      | ratingEnabled        | true        |
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    When Send connect to agent message by ORCA
    And I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    And Verify Orca returns Great, thanks - could you please rate our service from 0 - 10. 0 being "Very unsatisfied" and 10 being "Very satisfied". Simply type "skip" keyword to skip the survey. response during 40 seconds
    Then Send -1 message by ORCA
    And Verify Orca returns survey Great, thanks - could you please rate our service from 0 - 10. 0 being "Very unsatisfied" and 10 being "Very satisfied". Simply type "skip" keyword to skip the survey. response 2 number of times during 40 seconds
    Examples:
      | channelType |
      | ABC         |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1703")
    @Regression
  Scenario Outline: CD:: <channelType>:: Settings :: Survey :: Verify that user can 'rate and leave comment' for SMS CSAT survey type if close chat with //end command
    Given Setup ORCA <channelType> integration for Automation Bot tenant
    And Update survey management chanel <channelType> settings by ip for Automation Bot
      | ratingEnabled        | true        |
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    When Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Agent switch "Allow customer to give thank message" in survey management
    And Agent switch "Allow customer to leave a note" in survey management
    And Agent click save survey configuration button for <channelType> survey form
    When Send connect to agent message by ORCA
    Then I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from <channelType> user
    And Agent click on new conversation request from <channelType>
    And Conversation area becomes active with connect to agent user's message
    And Send //end message by ORCA
    And Verify Orca returns Great, thanks - could you please rate our service from 0 - 10. 0 being "Very unsatisfied" and 10 being "Very satisfied". Simply type "skip" keyword to skip the survey. response during 40 seconds
    And Send 5 message by ORCA
    And Verify Orca returns Thanks, please leave a message on how we can improve our service. Simply type "skip" keyword to skip this step. response during 40 seconds
    And Send happy with your service message by ORCA
    And Verify Orca returns Thank you for taking the time to provide us with your feedback. response during 40 seconds
    Examples:
      | channelType |
      | SMS         |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1778")
    @Regression
  Scenario Outline: CD:: <channelType>:: Settings :: Survey :: Verify if User should be able to enter a Skip keyword to cancel the survey
    Given Setup ORCA <channelType> integration for Automation Bot tenant
    And Update survey management chanel <channelType> settings by ip for Automation Bot
      | ratingEnabled        | true        |
      | surveyType           | NPS         |
      | ratingScale          | ZERO_TO_TEN |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    When Send connect to agent message by ORCA
    And I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from <channelType> user
    And Agent click on new conversation request from <channelType>
    And Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    And Verify Orca returns Great, thanks - could you please rate our service from 0 - 10. 0 being "Very unsatisfied" and 10 being "Very satisfied". Simply type "skip" keyword to skip the survey. response during 40 seconds
    And Send skip message by ORCA
    Then Verify Orca returns Thank you for taking the time to provide us with your feedback. response during 40 seconds
    And Verify Orca returns Thank you. Please don't hesitate to reach out if you ever need help! response during 40 seconds
    Examples:
      | channelType |
      | SMS         |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2443")
  Scenario Outline: CD:: Survey:: verify if user has an option to skip the survey for Whatsapp NPS survey type - Customer Feedback turned OFF in Flow
    Given Setup ORCA <channelType> integration for Automation Bot tenant
    And Update survey management chanel <channelType> settings by ip for Automation Bot
      | ratingEnabled        | true        |
      | surveyType           | NPS         |
      | ratingScale          | ZERO_TO_TEN |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
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
    Given Setup ORCA <channelType> integration for Automation Bot tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled        | true              |
      | surveyType           | NPS               |
      | ratingScale          | ZERO_TO_TEN       |
      | ratingIcon           | NUMBER            |
      | commentEnabled       | true              |
      | thanksMessageEnabled | true              |
    When Send connect to agent message by ORCA
    And I select Touch in left menu and Agent Desk in submenu
    And Agent has new conversation request from orca user
    And Agent click on new conversation request from orca
    And Conversation area becomes active with connect to agent user's message
    And Agent closes chat
    And Send 7 message by ORCA
    Then Verify Orca returns Thanks, please leave a message on how we can improve our service. Simply type "skip" keyword to skip this step. response during 40 seconds
    Examples:
      | channelType         |
      | whatsapp            |