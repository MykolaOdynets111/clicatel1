#Run only in remote mode. After run from remove files from /touch/src/test/resources/mediasupport/renamed/  folder

Feature: Media Support: User send doc and pic files to agent

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
    When Agent download the file
    Then File is not changed after uploading and downloading
    And Agent closes chat

    Examples:
      | fileType            |
      | jpeg                |
      | jpg                 |
      | png                 |
      | xls                 |
      | doc                 |
      | pdf                 |
      | ppt                 |