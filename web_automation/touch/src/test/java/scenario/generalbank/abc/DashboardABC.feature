@no_widget
Feature: Apple Business Chat :: Dashboard

  Background:
    Given Setup ORCA integration for General Bank Demo tenant

  @rating_abc
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45561")
  Scenario: Dashboard:: Verify that if NPS surveys are categorize as Detractors if Apple Business Chat user chooses between 0 – 6
    And Update survey management chanel abc settings by ip for General Bank Demo
      | ratingEnabled | true        |
      | ratingType    | NPS         |
      | ratingScale   | ZERO_TO_TEN |
      | ratingIcon    | NUMBER      |
    And I open portal
    Given Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin selects NPS survey type for abc survey form
    Then Survey Preview should be displayed with correct data for abc channel
    When Admin selects CSAT survey type for abc survey form
    Then Survey Preview should be displayed with correct data for abc channel

  @orca_api
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45561")
  Scenario: Dashboard:: Verify if Apple channel is displayed on live customer charts
    When Send chat to agent message by ORCA
    And I open portal
    And Login into portal as an admin of General Bank Demo account
    And I select Touch in left menu and Dashboard in submenu
    And Admin click on Customers Overview dashboard tab
    And Admin click on Live Customers on dashboard
    Then Admin should see Apple Business Chat chart in Live Chats by Channel
    And Admin should see Apple Business Chat charts in General sentiment per channel
    And Admin should see Apple Business Chat charts in Attended vs. Unattended Chats