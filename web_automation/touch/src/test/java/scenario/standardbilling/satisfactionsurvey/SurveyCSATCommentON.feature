@no_widget
@no_chatdesk
Feature: Satisfaction Survey: Comment enabling

  Background:
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled   | true             |
      | ratingType      | CSAT             |
      | ratingScale     | ONE_TO_TEN       |
      | ratingIcon      | NUMBER           |
      | commentEnabled     | false            |
    And I open portal
    Given Login into portal as an admin of Standard Billing account

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19250")
  Scenario: Verify if supervisor can enable option to allow customer to leave a note for CSAT survey type
    When I select Touch in left menu and Touch Preferences in submenu
    And Click "Survey management" nav button
    Then Survey Management page should be shown
    When Selects CSAT survey type
    And Agent switch "Allow customer to leave a note" in survey management
    When Agent click save survey configuration button
    Then Agent sees comment field in Survey management form
    Then Survey backend was updated for Standard Billing and webchat chanel with following attribute
      | commentEnabled     | true       |

