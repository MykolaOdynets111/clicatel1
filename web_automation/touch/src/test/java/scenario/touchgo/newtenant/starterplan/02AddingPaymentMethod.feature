@portal
@no_widget
@no_chatdesk
@adding_payment_method
@touch_go
Feature: Admin should be able to add payment methods

  Background:
    Given New tenant is successfully created
    Given Tenant SignedUp AQA has no Payment Methods

  Scenario: Admin should be able to add payment method from Billing Details page add see it while purchasing
    When I open portal
    And Login into portal as an admin of SignedUp AQA account
    When I select Settings in left menu and Billing & payments in submenu
    And Select "Payment methods" nav button
    Then 'Add a payment method now?' button is shown
    When Admin clicks 'Add a payment method now?' button
    Then 'Add Payment Method' window is opened
    When Admin tries to add new card for Tom Schmied
    Then New card for Tom Schmied is shown in Payment methods tab
    When Admin adds to cart 3 agents
    And Admin opens Confirm Details window
    Then Added payment method is able to be selected
