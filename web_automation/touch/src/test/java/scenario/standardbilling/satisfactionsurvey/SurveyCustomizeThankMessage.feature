@no_widget
@no_chatdesk
Feature: Satisfaction Survey: Customize customer thank you message

  Background:
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled        | true             |
      | ratingType           | CSAT             |
      | ratingScale          | ONE_TO_TEN       |
      | ratingIcon           | NUMBER           |
      | commentEnabled       | true             |
      | setThanksMessageEnabled | true             |
    And I open portal
    Given Login into portal as an admin of Standard Billing account

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-18591")
  Scenario: Verify if tenant can Customize customer thank you message text
    When I select Touch in left menu and Touch Preferences in submenu
    And Click "Survey management" nav button
    Then Survey Management page should be shown
    When Selects NPS survey type
    And Customize your survey thank message
    When Agent click save survey configuration button
    Then Thank Survey thank message was updated on backend for Standard Billing and webchat chanel
