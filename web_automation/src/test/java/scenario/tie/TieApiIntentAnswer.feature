Feature:  Tie API (intent answers)

     ### Intent Answers ###

  Scenario: Tie should be able to give list of answers
        API GET /tenants/{tenant}/answers_map/?intents=<normalized intent name>,<normalized intent name>
    When I send balance check,trading hours for generalbank tenant then response code is 200 and list of answers is shown

  Scenario: Tie should not give answer if there is only one intent
        API GET /tenants/{tenant}/answers_map/?intents=<normalized intent name>,<normalized intent name>
    When I send only balance check for generalbank tenant then response code is 404

  Scenario: Tie should return list of all categories
        API GET /tenants/{tenant}/answers/?category=all
    When I want to get all categories for generalbank response has status 200

  Scenario: Tie should return list of all answers for specific category
        API GET /tenants/{tenant}/answers/?category={CATEGORY ID}
    When I want to get all answers of Some FAQ category for generalbank response has status 200

#  Scenario: Creating, editing, deleting new intent and answers
#        API POST /tenants/{tenant}/answers/?intent={INTEND_ID}&answer={ANSWER_TXT}&answer_url={URL}
#        API PUT /tenants/{tenant}/answers/?intent={INTEND_ID}&answer={ANSWER_TXT}&answer_url={URL}&category={CATEGORY}&type={TYPE}
#        API DELETE /tenants/{tenant}/answers/?intent={INTEND_ID}
#    Given  I create new tenant with TIE API
#    And Wait for a minute
#    When I schedule training for a new tenant
