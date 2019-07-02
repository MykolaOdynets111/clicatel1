@portal
@updating_touchgo
@no_widget
@no_chatdesk
@testing_env_only
Feature: User should be able to upgrade Touch Go PLan

#  ToDo: Add logic for creating DB dump and after test destroying it

  Background:
    Given New tenant is successfully created

  Scenario: Upgrading Touch Go plan
    When I open portal
    And Login into portal as an admin of Updating AQA account
    When I try to upgrade and buy 3 agent seats
    Then I see "Payment Successful" message
    And Touch Go plan is updated to "TOUCH_STANDARD" in Updating AQA tenant configs
    And Touch Go plan is updated to "TOUCH GO STANDARD" in portal page