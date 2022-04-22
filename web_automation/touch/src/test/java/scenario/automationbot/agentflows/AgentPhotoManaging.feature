@no_widget
@agent_photo
Feature: Managing Agent's photo

  Scenario: Adding agent new photo
    Given Agent of Automation Bot tenant has no photo uploaded
    Given User select Automation Bot tenant
    And Click chat icon
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Settings in left menu and User Management in submenu
    And Click 'Manage' button for Taras Aqa user
    And Click 'Upload' button
    When Upload new photo
    Then New image is saved on portal and backend
    When Admin click BACK button in left menu
    When Agent launch agent desk from portal
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    And Agent responds with hello to User
    Then User should see 'hello' text response for his 'connect to Support' input
    And Correct agent image is shown in conversation area
