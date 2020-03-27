@media_download
Feature: Media Support: Send jpeg to agent

  Background:
    And User select Standard Billing tenant
    Given I login as agent of Standard Billing
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-18242")
  Scenario: Verify if user is able to send to agent file in .jpeg format
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When Agent responds with How may I help to User
    Then User attach jpeg file type
    And User send attached file
    Then Attachment message is shown for Agent
    When Agent download the file
#    Then jpeg file is not changed after uploading and downloading
    And Agent closes chat
