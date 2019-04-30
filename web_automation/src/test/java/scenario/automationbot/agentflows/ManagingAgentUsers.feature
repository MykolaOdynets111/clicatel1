@no_widget
@newagent
@testing_env_only
@no_chatdesk
Feature: Managing agent user

  Scenario: Editing agent roles
    Given New Automation Bot agent is created
    Then New agent is added into touch database
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Manage Agent users in submenu
    And Click 'Manage' button for created user
    When Admin clicks 'Edit user roles'
    And Add new platform admin solution
    When Admin logs out from portal
    And Login as newly created agent
    Then Agent is not redirected to chatdesk

  Scenario: Deleting agent
    Given New Automation Bot agent is created
    Then New agent is added into touch database
    Given I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Touch in left menu and Manage Agent users in submenu
    And Click 'Manage' button for created user
    When Admin clicks Delete user button
#    Then User is removed from User management page ToDo: Clarify locators with dev team (user rows' classes in Manage agent user and User management page differ)
    Then Newly created agent is deleted on backend
    When Admin logs out from portal
    Then Deleted agent is not able to log in portal

#  Scenario: Editing agent name and email
#    Given New Automation Bot agent is created
#    Then New agent is added into touch database
#    Given I open portal
#    And Login into portal as an admin of Automation Bot account
#    When I select Touch in left menu and Manage Agent users in submenu
#    And Click 'Manage' button for created user
#    When Admin updates agent's personal details
#    #    Then Created User is removed from User management page ToDo: Clarify locators with dev team (user rows' classes in Manage agent user and User management page differ)
#    #    Then updated user added to User management page ToDo: Clarify locators with dev team (user rows' classes in Manage agent user and User management page differ)
#    Then Newly created agent is deleted on backend
#    And  Updated agent is present on backend
#    When Admin logs out from portal
#    And Login as updated agent
#    Then Agent is logged in chat desk



