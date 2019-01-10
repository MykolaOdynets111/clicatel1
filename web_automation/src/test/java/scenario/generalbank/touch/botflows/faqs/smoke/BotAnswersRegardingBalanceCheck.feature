#@smoke
#Feature: Bot answers regarding balance check
#
#  Verification of communication between user and bot regarding balance check
#
#  Background:
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario Outline: Verify if user receives answer on "<user input>" message
#    When User enter <user input> into widget input field
#    Then User have to receive '<expected response>' text response for his '<user input>' input
#    Examples:
#      | user input                                            |expected response|
#      |Account balance                                        | Hi ${firstName}, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.|
#      |hi, how do i check my balance in the app?              | Hi ${firstName}, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.|
#      |how to check balance at home?                          | Hi ${firstName}, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.|
#
