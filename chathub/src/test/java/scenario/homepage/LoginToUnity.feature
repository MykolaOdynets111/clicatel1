Feature: Unity feature

  @TestCaseId("https://jira.clickatell.com/browse/CCH-184")
  @AliChat
  Scenario: Unity :: Landing Page :: Login to main screen and go to Integrations Product
    When I login to Unity with email "chat2payqauser11+echo1@gmail.com" and password "Password#1"
    And I open Products & Services page
    And I open Integrations product
