#@portal
#@no_widget
#@no_chatdesk
#Feature: Signing up the account: testing mandatory fields
#
#  Background:
#    Given Portal Sign Up page is opened
#
#  Scenario Outline: While signing up all field should be mandatory. Data set: <first_and_last_name>, <account_name>, <email>, <pass>
#    When I try to create new account with following data: <first_and_last_name>, <account_name>, <email>, <pass>
#    Then Required error should be shown
#
#    Examples:
#      |first_and_last_name | account_name |email        |pass     |
#      |                    |              |             |         |
#      |                    |testAccount   |aqa@test.com |123456789|
#      |Test Aqa            |              |aqa@test.com |123456789|
#      |Test Aqa            |testAccount   |             |123456789|
#      |Test Aqa            |testAccount   |aqa@test.com |         |