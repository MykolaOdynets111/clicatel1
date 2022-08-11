@no_widget
@no_chatdesk
Feature: Satisfaction Survey

  Background:
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And Update survey management chanel whatsapp settings by ip for General Bank Demo
      | ratingEnabled   | true             |
      | surveyType      | NPS              |
      | ratingScale     | ZERO_TO_TEN      |
      | ratingIcon      | NUMBER           |

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19249")
  Scenario: verify if supervisor can customize survey question for whatsapp CSAT survey type
    And I open portal
    Given Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin selects CSAT survey type for abc survey form
    And Customize your survey "Please rate your experience with our agent" question
    And Agent click save survey configuration button for whatsapp survey form
    Then Preview question is updated successfully
