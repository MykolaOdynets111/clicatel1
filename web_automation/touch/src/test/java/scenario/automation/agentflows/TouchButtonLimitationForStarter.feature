@portal
@no_widget
@no_chatdesk
@touch_go
Feature: User of Starter tenant should not see tbutton

  Background:
    Given Widget is enabled for Automation tenant

  Scenario: tbutton should be turned off for tenant with Starter plan
    Given User select Automation tenant
    And Click chat icon
    Then Touch button is not shown
