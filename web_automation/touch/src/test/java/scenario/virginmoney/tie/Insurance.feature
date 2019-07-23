#@tie
#Feature: VM: TIE sentiments and intent for Insurance
#  Removed for now from DataSet according to TPLAT-3132
#
#  Scenario Outline: Verify if TIE sentiment is NEUTRAL or POSITIVE for the following message: "<user_message>"
#    Then TIE sentiment is NEUTRAL or POSITIVE when I send '<user_message>' for Virgin Money tenant
#    And TIE returns 1 intent: "insurance" on '<user_message>' for Virgin Money tenant
#
#    Examples:
#      |user_message            |
#      |Quote for insurance    |
#      |Insurance quote please |
