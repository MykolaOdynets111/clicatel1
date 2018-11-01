#Feature: Signing up the account
#
#  Scenario: I should be able to sign up and activate new account
#    Given Portal Sign Up page is opened
#    When I provide all info and click 'Sign Up' button
#    Then Activation ID record is created in DB
#    When I use activation ID and opens activation page
#    And Provide new password
#    Then I am able to log into new account