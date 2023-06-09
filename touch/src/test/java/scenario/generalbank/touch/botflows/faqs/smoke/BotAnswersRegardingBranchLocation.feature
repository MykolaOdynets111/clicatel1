@smoke
Feature: Bot answers regarding branch location

  Verification of communication between user and bot regarding branch location

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input                                       |expected response|
#      |branch location                                   |Our branches are conveniently located nationwide near main commuter routes and in shopping malls. Use our branch locator to find your nearest branch. |
      |Where can I find a branch?                        | Our branches are conveniently located nationwide near main commuter routes and in shopping malls. Use our branch locator to find your nearest branch. |