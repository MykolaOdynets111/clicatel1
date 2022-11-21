@no_widget
@no_chatdesk
@Regression
Feature: Activity Overview

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2376")
  @agent_availability
  Scenario: CD :: Dashboard :: Activity Overview :: Verify the total agents online counter

    Given I login as Agent of General Bank Demo
    When I select Touch in left menu and Dashboard in submenu
    Then Save Agents available counter value
    When I login as second agent of General Bank Demo
    And Admin refreshes the page
    Then Agents available widget value increased on 1
