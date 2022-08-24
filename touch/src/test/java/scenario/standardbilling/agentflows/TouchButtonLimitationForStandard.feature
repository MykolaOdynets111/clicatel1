@portal
@no_widget
@no_chatdesk
@touch_go
Feature: User of Standard tenant should not see tbutton

  Scenario: tbutton should be turned off for Standard
    Given User select Standard Billing tenant
    And Click chat icon
    Then Touch button is not shown
