@no_widget
@no_chatdesk
Feature: Managing business details

  @Issue("https://jira.clickatell.com/browse/TADMIN-1626")
  Scenario: Check changing business details
    Given I open portal
    And Login into portal as an admin of Automation Bot account

    When I select Touch in left menu and Touch preferences in submenu
    And Click "About your business" nav button
    And Change business details
    Then Refresh page and verify business details was changed for Automation Bot



