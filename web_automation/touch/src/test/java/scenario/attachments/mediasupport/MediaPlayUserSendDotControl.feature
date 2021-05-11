@dot_control
Feature: Media Support: User send files and agent playing .Control

  Background:
    Given I login as agent of Attachments
    Given Create .Control integration for Attachments and adapter: whatsapp
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

  Scenario Outline: Verify if user is able to send to agent file in .<fileType> format .Control
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    And Agent responds with How may I help to User
    When User send <fileType> attachment with .Control
    Then Attachment message from dotcontrol is shown for Agent
    Then Agent can play <fileType> file

    Examples:
      | fileType            |
      | mp3                 |
      | opus                |
      | mp4                 |
      | aac                 |
      | amr                 |