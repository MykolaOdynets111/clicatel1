@portal
@no_chatdesk
@creating_new_tenant
Feature: Creating new tenant

  Scenario: Admin of newly created account should be able to create new tenant
    Given New account is successfully created
    Given I open portal
    And Login into portal as an admin of SignedUp AQA account
    Then "Get started with Touch" button is shown
    When Click "Get started with Touch" button
    Then "Get started with Touch Go" window is opened
    When I try to create new tenant
    Add verification on tenant creation)
    Given User open new SignedUp AQA tenant
    And Click chat icon
    When User enter chat to support into widget input field

