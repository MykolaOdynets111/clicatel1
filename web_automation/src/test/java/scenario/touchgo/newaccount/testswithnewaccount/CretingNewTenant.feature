@portal
@no_chatdesk
@creating_new_tenant
@testing_env_only
  @egor
Feature: Creating new tenant
#
##  Must run after new account is created, Please see the following test:
##  touchgo.touchgoonethread.SignUpNewAccount.feature
##
##  POSTPONED because there is no possibility for now to delete the tenant
##  And even after account closing tenant continue existing
#
  Scenario: Admin of newly created account should be able to create new tenant
    Given I open portal
    And Login into portal as an admin of SignedUp AQA account
    Then "Get started with Touch" button is shown
    When Click "Get started with Touch" button
    Then "Get started with Touch Go" window is opened
    When I try to create new tenant
#    Given User open new SignedUp AQA tenant
#    And Click chat icon
#    When User enter chat to support into widget input field

