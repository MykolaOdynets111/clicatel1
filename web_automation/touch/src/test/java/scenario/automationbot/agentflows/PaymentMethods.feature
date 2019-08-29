@portal
@no_widget
@no_chatdesk
@adding_payment_method
@touch_go
Feature: Admin should be able to manage payment methods

  Background:
    Given Tenant Automation Bot has no Payment Methods

  Scenario: Admin should be able to add payment method from Billing Details page add see it while purchasing
    When I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Settings in left menu and Billing & payments in submenu
    And Select "Payment methods" nav button
    Then 'Add Payment Method' button is shown
    When Admin clicks 'Add Payment Method' button
    Then 'Add Payment Method' window is opened
    When Admin tries to add new card for John Smith
    Then New card for John Smith is shown in Payment methods tab


  Scenario: Admin should be able to add payment method from purchasing and to delete it
    When I open portal
    And Login into portal as an admin of Automation Bot account
    When Admin adds to cart 2 agents
    And Admin opens Confirm Details window
    Then "Add Credit/Debit Card" shown in payment methods dropdown
    When Admin selects "Add Credit/Debit Card" in payment methods dropdown
    When Admin provides all card info for John Stein cardholder
    When Selects all checkboxes for adding new payment
    And Admin clicks 'Next' button
    Then Payment review tab is opened
    When Admin closes Confirm details window
    When Admin click BACK button in left menu
    And Admin select Settings in left menu and Billing & payments in submenu
    And Select "Payment methods" nav button
    When Admin clicks Manage -> Remove payment for John Stein
    Then Payment method for John Stein is not shown in Payment methods tab

  Scenario: Admin should not be able to add payment method without accepting all terms
    When I open portal
    And Login into portal as an admin of Automation Bot account
    When I select Settings in left menu and Billing & payments in submenu
    And Select "Payment methods" nav button
    When Admin clicks 'Add Payment Method' button
    Then 'Add Payment Method' window is opened
    When Admin provides all card info for John Brown cardholder
    Then 'Add payment method' button is disabled
    When Admin selects 1 terms checkbox
    Then 'Add payment method' button is disabled
    When Admin selects 2 terms checkbox
    And Admin clicks 'Add payment method' button
    Then New card for John Brown is shown in Payment methods tab




