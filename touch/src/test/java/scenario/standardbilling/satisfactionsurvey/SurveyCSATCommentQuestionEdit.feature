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
      | ratingEnabled | true       |
      | surveyType    | CSAT       |
      | ratingScale   | ONE_TO_FIVE |
      | ratingIcon    | NUMBER     |
    And I open portal
    Given Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Admin selects CSAT survey type for <channelType> survey form
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
  Scenario Outline: CD :: SMS :: Settings :: Survey :: Verify if Supervisor should be allowed to type plain text message more then the total count of 160 in the survey question
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled | true        |
      | surveyType    | CSAT        |
      | ratingScale   | ONE_TO_FIVE |
      | ratingIcon    | NUMBER      |
    And I open portal
    Given Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Admin selects CSAT survey type for <channelType> survey form
    When Customize your survey "Please rate your experience with our agent" question
    Then Agent checks question title character limit as 160 characters in survey form
    And Supervisor is able to see the number of characters typed for text in survey form
    When Customize your survey "Please rate your experience with our agent Please rate your experience with our agent Please rate your experience with our agent Please rate your experience with" question
    Then Agent is able to see the number of characters typed more than 160 in survey form
    And Supervisor is able to see the number of characters typed for text in survey form
    And Supervisor is able to see character limit error with text Maximum text length for this field is 160 characters
    When Customize your survey "Please rate your experience with our agent Please rate your experience with our agent Please rate your experience with our agent 😃" question with emoji
    Then Agent is able to see the number of characters typed more than 70 in survey form
    And Supervisor is able to see the number of characters typed for text in survey form
    And Supervisor is able to see character limit error with text Maximum text length for this field is 70 characters
    Examples:
      | channelType |
      | sms         |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1868")
    @off_survey_sms
  Scenario Outline: CD:: SMS:: Settings :: Survey :: Verify if Supervisor message contains only Unicode characters then the total count should be out of 70 in the survey question
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled | true        |
      | surveyType    | CSAT        |
      | ratingScale   | ONE_TO_FIVE |
      | ratingIcon    | NUMBER      |
    And I open portal
    Given Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Admin selects CSAT survey type for <channelType> survey form
    When Customize your survey "Please rate your experience with our agent 😃" question with emoji
    And Agent click save survey configuration button for <channelType> survey form
    Then Agent checks question title character limit as 70 characters in survey form
    And Supervisor is able to see the number of characters typed for text in survey form
    When Customize your survey "Please rate your experience with our agent" question with emoji
    And Agent click save survey configuration button for <channelType> survey form
    Then Agent checks question title character limit as 170 characters in survey form
    When Customize your survey "ふりがな" question with emoji
    And Agent click save survey configuration button for <channelType> survey form
    Then Agent checks question title character limit as 4 / 70 characters in survey form
    And Supervisor is able to see the number of characters typed for text in survey form
    When Customize your survey "الْأَبْجَدِيَّة الْعَرَبِيَّة" question with emoji
    And Agent click save survey configuration button for <channelType> survey form
    Then Agent checks question title character limit as 29 / 70 characters in survey form
    And Supervisor is able to see the number of characters typed for text in survey form
    Examples:
      | channelType |
      | sms         |