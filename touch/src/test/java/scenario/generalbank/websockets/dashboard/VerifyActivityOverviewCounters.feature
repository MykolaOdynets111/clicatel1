@no_widget
@no_chatdesk
@Regression
@skip
Feature: Activity Overview

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2376")
  @agent_availability
  #Test case fails everytime in automation as sometimes the agent counter shows 1 and sometime it shows 2 because we are logging main agent first. Unreliable test case, will work later.
  Scenario: CD :: Dashboard :: Activity Overview :: Verify the total agents online counter

    Given I login as Agent of General Bank Demo
    When I select Touch in left menu and Dashboard in submenu
    Then Save Agents available counter value
    When I login as second agent of General Bank Demo
    And Admin refreshes the page
    Then Agents available widget value increased on 1
