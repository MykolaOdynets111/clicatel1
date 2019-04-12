#@portal
#@no_widget
#@no_chatdesk
#Feature: Admin of tenant with Starter plan should be redirected on the billing details page while buying new plan
#
#  Scenario: Admin should be redirected to the Billing Details when they are not added
#    When I open portal
#    And Login into portal as an admin of Automation Bot account
#    When Admin clicks 'Upgrade' button
#    Then 'Billing Not Setup' pop up is shown
#    When Admin clicks 'Setup Billing' button
#    Then Billing Details page is opened
