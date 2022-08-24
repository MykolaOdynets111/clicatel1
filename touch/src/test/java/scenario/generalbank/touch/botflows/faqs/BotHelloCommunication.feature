@smoke
Feature: General Bank Hello flow

  Verification of basic communication between user and bot

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user receives answer on "<user input>" message
    When User enter <user input> into widget input field
    Then User have to receive '<expected response>' text response for his '<user input>' input
    Examples:
      | user input                                                    |expected response|
      |hello                                                          |Hello                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |