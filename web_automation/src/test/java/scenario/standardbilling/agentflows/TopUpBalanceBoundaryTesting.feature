@portal
@no_widget
@no_chatdesk
@touch_go
@without_tct
Feature: Top up balance: boundary testing

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-3697")
  Scenario: Touch Plan :: Add Top up balance (maximum purchase)
        if USD, then max value = 40000,
        if EUR, then max value = 35000,
        if ZAR, then max value = 500000
    When I open portal
    And Login into portal as an admin of Standard Billing account
    When I select Settings in left menu and Billing & payments in submenu
    And Select 'Top up balance' in nav menu
    When Admin clicks Top up balance on Billing details
    Then 'Top up balance' window is opened
    When Agent enter <max value+1> top up amount
    And Click 'Add to cart' button
    Then "The maximum purchase amount is max_value" message is displayed


  @TestCaseId("https://jira.clickatell.com/browse/TPORT-3699")
  Scenario: Touch Plan :: Add Top up balance (minimum purchase)
        if USD, them min value = 10,
        if EUR, then min value = 10,
        if ZAR, then min value = 100
    When I open portal
    And Login into portal as an admin of Standard Billing account
    When I select Settings in left menu and Billing & payments in submenu
    And Select 'Top up balance' in nav menu
    When Admin clicks Top up balance on Billing details
    Then 'Top up balance' window is opened
    When Agent enter <min value+1> top up amount
    And Click 'Add to cart' button
    Then "The minimum purchase amount is min_value" message is displayed

