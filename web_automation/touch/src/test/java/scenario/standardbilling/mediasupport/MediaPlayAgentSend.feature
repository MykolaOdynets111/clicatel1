
Feature: Media Support: Agent send files and user playing

  Background:
    And User select Standard Billing tenant
    Given I login as agent of Standard Billing
    And Click chat icon

  Scenario Outline: Verify if agent is able to send to user a file in .<fileType> format
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent attach <fileType> file type
    And Agent send attached file
    Then Widget contains attachment message
    Then User can play <fileType> file

    Examples:
      | fileType            |
      | mp3                 |
      | opus                |
      | mp4                 |
      | aac                 |
      | amr                 |