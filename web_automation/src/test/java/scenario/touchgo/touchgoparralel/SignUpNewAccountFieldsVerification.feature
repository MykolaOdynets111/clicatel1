@portal
@no_widget
@no_chatdesk
Feature: Signing up the account: verification fields limitation
  password: size must be between 8 and 128

  Background:
    Given Portal Sign Up page is opened

  Scenario Outline: Verification account name field limitation in the following account name case: <account_name>
    When I try to create new account with following data: Test Aqa , <account_name>, aqa@test.com, 123456789
    Then Error popup with text <expected_error>  is shown

    Examples:
      | account_name | expected_error                                                                            |
      | generalbank  | accountName: account with name 'generalbank' already exists.                             |
      | Test Bank    | accountName: accountName cannot contain whitespaces                                      |
      | thebestbank! | accountName: Account name can contain alphanumeric characters, minus or underscore only.|

  Scenario: Verification password length
    When I try to create new account with following data: Test Aqa , fieldtesting, aqa@test.com, 1234567
    Then Error popup with text password: size must be between 8 and 128  is shown