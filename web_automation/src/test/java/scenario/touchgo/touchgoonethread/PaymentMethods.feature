@portal
@no_widget
@no_chatdesk
@adding_payment_method
Feature: Admin should be able to manage payment methods

  Background:
    Given Tenant Standard AQA has no Payment Methods


  Scenario: Admin should be able to add payment method from Billing Details page add see it while purchasing
    When I open portal
    And Login into portal as an admin of Standard AQA account
    When I select Settings in left menu and Billing & payments in submenu
    And Select 'Payment methods' in nav menu
    Then 'Add a payment method now?' button is shown
    When Admin clicks 'Add a payment method now?' button
    Then 'Add Payment Method' window is opened
    When Admin tries to add new card
    Then New card is shown in Payment methods tab
    When Admin adds to cart 3 agents
    And Admin opens Confirm Details window
    Then Added payment method is able to be selected


  Scenario: Admin should be able to add payment method from purchasing and to delete it
    When I open portal
    And Login into portal as an admin of Standard AQA account
    When Admin adds to cart 2 agents
    And Admin opens Confirm Details window
    And Click Next button on Details tab
    Then "Add Credit/Debit Card" shown in payment methods dropdown
    When Admin selects "Add Credit/Debit Card" in payment methods dropdown
    When Admin provides all card info
    When Selects all checkboxes for adding new payment
    And Admin clicks 'Next' button
    Then Payment review tab is opened
    When Admin closes Confirm details window
    When Admin select BACK in left menu
    And Admin select Settings in left menu and Billing & payments in submenu
    And Select 'Payment methods' in nav menu
    When Admin clicks Manage -> Remove payment
    Then 'Add a payment method now?' button is shown

  Scenario: Admin should not be able to add payment method without accepting all terms
    When I open portal
    And Login into portal as an admin of Standard AQA account
    When I select Settings in left menu and Billing & payments in submenu
    And Select 'Payment methods' in nav menu
    When Admin clicks 'Add a payment method now?' button
    Then 'Add Payment Method' window is opened
    When Admin provides all card info
    And Admin clicks 'Add payment method' button
    Then 'Add Payment Method' window is opened
    When Admin selects 1 terms checkbox
    And Admin clicks 'Add payment method' button
    Then 'Add Payment Method' window is opened
    When Admin selects 2 terms checkbox
    And Admin clicks 'Add payment method' button
#    Then 'Add Payment Method' window is opened
#    When Admin selects 3 terms checkbox
#    And Admin clicks 'Add payment method' button
    Then New card is shown in Payment methods tab



