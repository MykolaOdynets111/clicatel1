#@no_widget
#@agent_photo
Feature: Managing tenant's brand image

  Scenario: Adding tenant new image
    Given Agent of Automation Bot tenant has no brand image
    When I open portal
    And Login into portal as an admin of Automation Bot account
    And I select Touch in left menu and Touch preferences in submenu
    And Click "Configure your brand" nav button
    And Upload: photo for tenant
    And Agent click 'Save changes' button
    Then New brand image is saved on backend for Automation Bot tenant
    When I select Touch in left menu and Launch Chat Desk in submenu
    Then Tenant photo is shown on chatdesk
    When User select Automation Bot tenant
    Then Tenant photo is shown on widget

