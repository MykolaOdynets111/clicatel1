@portal
@no_widget
@no_chatdesk
Feature: User should not be able to upgrade Touch Go PLan if he does not accept terms and conditions

  Scenario: Upgrading Touch Go plan without accepting Terms and conditions
    When I open portal
    And Login into portal as an admin of Updating AQA account
    When I try to upgrade and buy 3 agent seats without accept Clickatell's Terms and Conditions
    Then Payment is not proceeded and Payment Summary tab is still opened