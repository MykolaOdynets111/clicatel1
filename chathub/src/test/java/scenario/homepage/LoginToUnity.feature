Feature: Unity feature

  @TestCaseId("https://jira.clickatell.com/browse/CCH-000")
  @AliChat
  Scenario: Chat Hub :: Landing Page :: Login to main screen and go to Zendesk Page
    When I login to Unity with email "chat2payqauser11+echo1@gmail.com" and password "Password#1"
    And I open Products & Services page
    And I open Integrations product
    And I click on Zendesk Integrations Card
