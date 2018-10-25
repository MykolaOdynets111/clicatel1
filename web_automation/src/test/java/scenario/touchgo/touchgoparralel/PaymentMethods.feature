@portal
Feature: Admin should be able to manage payment methods

  Scenario: Admin should be able to add payment method from Billing Details page
    When I open portal
    And Login into portal as an admin of Standard AQA account
    When I select Settings in left menu and Billing & payments in submenu
    And Select 'Payment methods' in nav menu
    Then 'Add a payment method now?' button is shown
    When Admin clicks 'Add a payment method now?' button
    Then 'Add Payment Method' window is opened
    When Admin provides all card info
    Then New card is shown in Payment methods tab

