#Run only in remote mode.
#  skipp till shared folder will be ready
@skip
Feature: Media Support: Agent sends files and user downloads

  Background:
    And User select Standard Billing tenant
    Given I login as agent of Standard Billing
    And Click chat icon

  Scenario Outline: Verify if agent is able to send to user a file in .<fileType> format
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    And Agent attach <fileType> file type
    When Agent send attached file
    Then Widget contains attachment message
    When User is downloading the file
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