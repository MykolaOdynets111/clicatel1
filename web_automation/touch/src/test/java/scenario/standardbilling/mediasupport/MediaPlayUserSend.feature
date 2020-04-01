
Feature: Media Support: User send files and agent playing

  Background:
    And User select Standard Billing tenant
    Given I login as agent of Standard Billing
    And Click chat icon

  Scenario Outline: Verify if user is able to send to agent file in .<fileType> format
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When Agent responds with How may I help to User
    Then User attach <fileType> file type
    And User send attached file
    Then Attachment message is shown for Agent
    Then Agent can play <fileType> file
    And Agent closes chat

    Examples:
      | fileType            |
      | mp3                 |
      | opus                |
      | mp4                 |
      | aac                 |
      | amr                 |