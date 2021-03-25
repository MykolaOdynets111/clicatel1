#Run only in remote mode.
#  skipp till shared folder will be ready
@skip
@dot_control
Feature: Media Support: User send files and agent downloading .Control

  Background:
    Given I login as agent of Standard Billing
    Given Create .Control integration for Standard Billing and adapter: whatsapp
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

  Scenario Outline: Verify if user is able to send to agent file in .<fileType> format .Control
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    And Agent responds with How may I help to User
    When User send <fileType> attachment with .Control
    Then Attachment message from dotcontrol is shown for Agent
    When Agent download the file
    Then File is not changed after uploading and downloading

    Examples:
      | fileType            |
      | jpeg                |
      | jpg                 |
      | png                 |
      | xls                 |
      | doc                 |
      | pdf                 |
      | ppt                 |