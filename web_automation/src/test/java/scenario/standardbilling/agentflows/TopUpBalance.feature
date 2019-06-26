@portal
@no_widget
@no_chatdesk
@touch_go
@without_tct
Feature: Top up balance

  Scenario: Admin should be able to top up balance
    When I open portal
    And Login into portal as an admin of Standard Billing account
    When I select Settings in left menu and Billing & payments in submenu
    And Select 'Top up balance' in nav menu
    When Admin clicks Top up balance on Billing details
    Then 'Top up balance' window is opened
    When Agent enter allowed top up sum
    And Click 'Add to cart' button
    And Make the balance top up payment
    Then I see "Payment Successful" message
    When Admin closes Confirm details window
    Then Top up balance updated up to 10 minutes

