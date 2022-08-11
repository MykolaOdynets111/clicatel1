@no_widget
@no_chatdesk
@off_survey_management
Feature: Satisfaction Survey

  Background:
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled        | true             |
      | surveyType           | CSAT             |
      | ratingScale          | ONE_TO_TEN       |
      | ratingIcon           | NUMBER           |
      | commentEnabled       | true             |
      | thanksMessageEnabled | true             |
    And I open portal
    Given Login into portal as an admin of Standard Billing account

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-18591")
  Scenario: Verify if tenant can Customize customer thank you message text
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Selects NPS survey type
    And Customize your survey thank message
    And Agent click save survey configuration button for webchat survey form
    Then Thank Survey thank message was updated on backend for Standard Billing and webchat chanel
