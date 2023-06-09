@no_widget
@no_chatdesk
@Regression
Feature: Satisfaction Survey

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2286")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-1790")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-1980")
    @off_survey_abc
    @off_survey_sms
    @off_survey_whatsapp
  Scenario Outline: CD:: <channelType>:: Settings :: Survey :: Verify if tenant can customize his own survey questions for CSAT survey type
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | ratingEnabled        | true        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    And I open portal
    Given Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    And Customize your survey "Please rate your experience with our agent" question
    And Agent click save survey configuration button for <channelType> survey form
    Then Preview question is updated successfully
    Examples:
      | channelType |
      | Whatsapp    |
      | ABC         |
      | SMS         |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1735")
  @off_survey_sms
  Scenario Outline: CD :: SMS :: Settings :: Survey :: Verify if Supervisor is able to see the count for the number of characters typed in the survey question
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled        | true        |
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    And I open portal
    Given Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Customize your survey "Please rate your experience with our agent" question
#    Then Agent checks question title character limit as 42 characters in survey form
    And Supervisor is able to see the number of characters typed for text in survey form
    Examples:
      | channelType |
      | sms         |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1868")
    @off_survey_sms
  Scenario Outline: CD:: SMS:: Settings :: Survey :: Verify if Supervisor message contains only Unicode characters then the total count should be out of 70 in the survey question
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled        | true        |
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    And I open portal
    Given Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    And Survey Management page should be shown
    And Customize your survey "Please rate your experience with our agent 😃" question with emoji
    And Agent click save survey configuration button for <channelType> survey form
    Then Agent checks question title character limit as 45 characters in survey form
    And Supervisor is able to see the number of characters typed for text in survey form
    When Customize your survey "Please rate your experience with our agent" question with emoji
    And Agent click save survey configuration button for <channelType> survey form
    Then Agent checks question title character limit as 42 characters in survey form
    When Customize your survey "ふりがな" question with emoji
    And Agent click save survey configuration button for <channelType> survey form
    Then Agent checks question title character limit as 4 characters in survey form
    And Supervisor is able to see the number of characters typed for text in survey form
    When Customize your survey "الْأَبْجَدِيَّة الْعَرَبِيَّة" question with emoji
    And Agent click save survey configuration button for <channelType> survey form
    Then Agent checks question title character limit as 29 characters in survey form
    And Supervisor is able to see the number of characters typed for text in survey form
    Examples:
      | channelType |
      | sms         |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-5230")
    @off_survey_sms
  Scenario Outline: CD :: Dashboard :: Settings :: Survey :: Character count again set to 70 on refreshing the page
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled        | true        |
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    And I open portal
    Given Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    And Survey Management page should be shown
    And Customize your survey "Please rate your experience with our agent 😃" question with emoji
    And Agent click save survey configuration button for <channelType> survey form
    And Agent checks question title character limit as 45 characters in survey form
    And Supervisor is able to see the number of characters typed for text in survey form
    And Agent refreshes the page
    And Survey Management page should be shown
    And Agent checks question title character limit as 45 characters in survey form
    And Supervisor is able to see the number of characters typed for text in survey form
    Examples:
      | channelType |
      | sms         |