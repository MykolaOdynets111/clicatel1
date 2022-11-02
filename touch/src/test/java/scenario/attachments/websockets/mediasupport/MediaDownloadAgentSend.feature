@no_chatdesk
@skip
@orca_api
Feature: Media Support: Agent sends files and user downloads

  Background:
    Given I login as agent of Attachments
    And Setup ORCA whatsapp integration for Attachments tenant
    When Send connect to Support message by ORCA

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2073")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-2066")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-2071")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-1604")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-1491")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-1608")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-1532")
  Scenario Outline: CD :: Verify if agent is able to send to user (Whatsapp) a file in .<fileType> format
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    And Conversation area becomes active with connect to Support user's message
    And Agent attach <fileType> file type
    And Agent send attached file
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