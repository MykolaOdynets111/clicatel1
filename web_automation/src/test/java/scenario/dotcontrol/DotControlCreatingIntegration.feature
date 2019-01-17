@no_widget
@dot_control
@no_chatdesk
Feature: Creating .Control integration

  Scenario: Creating .Control integration
    When Create .Control integration for General Bank Demo tenant
    Then Created .Control integration is correctly returned with GET response
    And 409 status code for multiple integration creation
    When I delete .Control integration
    Then Http integration status is updated after deleting