#@updating_touchgo
#Feature: User should be able to upgrade Touch Go PLan
#
#  Scenario: Upgrading Touch Go plan
#    When I open portal
#    And Login into portal as an admin of Updating AQA account
#    When I try to upgrade and buy 3 agent seats
#    Then I see "Payment Successful" message
#    And Touch Go plan is updated to "TOUCH_STANDARD" in Updating AQA tenant configs
#    And Touch Go PLan is updated to "TOUCH_STANDARD" in portal page