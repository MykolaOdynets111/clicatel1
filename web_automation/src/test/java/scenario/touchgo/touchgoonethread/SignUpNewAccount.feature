#@portal
#@no_widget
#@no_chatdesk
#@signup_account
#Feature: Signing up the account
#
#  Scenario: I should be able to sign up and activate new account
#    Given Portal Sign Up page is opened
#    When I provide all info about new account and click 'Sign Up' button
#    Then Login page is opened with a message that activation email has been sent
#    And Activation ID record is created in DB
#    When I use activation ID and opens activation page
##    And Provide new password
##    Then I am able to log into new account