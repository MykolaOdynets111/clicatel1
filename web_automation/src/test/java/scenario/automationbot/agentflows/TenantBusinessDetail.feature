@no_widget
@no_chatdesk
Feature: Managing business details

##  @Issue("https://jira.clickatell.com/browse/TADMIN-1626")
#  Scenario: Touch preferences :: Check changing business details
#    Given I open portal
#    And Login into portal as an admin of Automation Bot account
#    When I select Touch in left menu and Touch preferences in submenu
#    And Click "About your business" nav button
#    And Change business details
#    Then Refresh page and verify business details was changed for Automation Bot

  @agent_support_hours
  Scenario: Touch preferences :: Verify if agent can change support hours
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Touch preferences in submenu
    And Click "About your business" nav button
    When Select 'Specific Agent Support hours' radio button in Agent Supported Hours section
    And Uncheck today day and apply changes
    Then Check that today day is unselected in 'Scheduled hours' pop up
    And 'support hours' are updated in Automation Bot configs



