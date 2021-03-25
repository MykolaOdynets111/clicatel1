#Run only in remote mode.
#  skipp till shared folder will be ready
@skip
@orca_api
Feature: Media Support: User send files and agent downloading ABC

  Background:
    Given I login as agent of Standard Billing
    Given Setup ORCA integration for General Bank Demo tenant
    When Send connect to Support message by ORCA

  Scenario Outline: Verify if user is able to send to agent file in .<fileType> format ABC
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    And Agent responds with How may I help to User
    When User send <fileType> attachment with orca
    Then Attachment message from dotcontrol is shown for Agent
    When Agent download the file
    Then File is not changed after uploading and downloading

    Examples:
      | fileType            |
      | jpeg                |
#      | jpg                 |
#      | png                 |
#      | xls                 |
#      | doc                 |
#      | pdf                 |
#      | ppt                 |