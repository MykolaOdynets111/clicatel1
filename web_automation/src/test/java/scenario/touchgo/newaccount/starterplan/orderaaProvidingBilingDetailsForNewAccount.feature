#@portal
#@no_widget
#@no_chatdesk
#Feature: Providing Billing details for newly created Account
#
#  Scenario: Filling Billing details for newly created Account
#    Given New tenant is successfully created
#    Given I open portal
#    And Login into portal as an admin of SignedUp AQA account
#    When Admin clicks 'Upgrade' button
#    Then 'Billing Not Setup' pop up is shown
#    When Admin clicks 'Setup Billing' button
#    Then Billing Details page is opened
#    When Fill in Billing details
#    Then Billing details is saved on backend SignedUp AQA
