Feature: VM flow regarding Policy queries

  Background:
    Given User select Virgin Money tenant
    And Click chat icon

  Scenario Outline: "Policy queries: Funeral" flow should work for "user message" user message
    When User enter <user message> into widget input field
#    Then Card with a Hi ${firstName}, are you asking about a car, funeral or household insurance policy? text is shown on user <user message> message
    Then Card with a Are you asking about a car, funeral or household insurance policy? text is shown on user <user message> message
    And Card with a buttons Funeral; Auto or household is shown on user <user message> message
    When User click Funeral button in the card on user message <user message>
    Then User have to receive 'We cover any person aged 18-70, who has a South African ID.  Extended family may be added, up to the age of 75. Remember that this is just the sign-up age, your policy remains active beyond 70 if you signed up before this.' text response for his 'Funeral' input
    Examples:
      |user message                             |
      |Age limits of policy holder & dependants |
      |How old must I be to apply for a policy? |
      |Whats the cut off age for a policy?      |


  Scenario Outline: "Policy queries: Auto or household" flow should work for "user message" user message
    When User enter <user message> into widget input field
#    Then Card with a Hi ${firstName}, are you asking about a car, funeral or household insurance policy? text is shown on user <user message> message
    Then Card with a Are you asking about a car, funeral or household insurance policy? text is shown on user <user message> message
    And Card with a buttons Funeral; Auto or household is shown on user <user message> message
    When User click Auto or household button in the card on user message <user message>
    Then User have to receive 'We cover eligible persons aged 21-70, who have a South African ID.' text response for his 'Auto or household' input
    Examples:
      |user message                             |
      |Age limits of policy holder & dependants |
      |How old must I be to apply for a policy? |
      |Whats the cut off age for a policy?      |


