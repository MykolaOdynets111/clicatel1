@start_server
@no_widget
@dot_control
@without_tct

  Feature: Suggestion editing

    Scenario: Sending edited suggestion via .Control
      Given Create .Control integration for General Bank Demo tenant
      Given I login as agent of General Bank Demo
      When Send chat to agent message for .Control
      Then Agent has new conversation request from dotcontrol user
      When Agent click on new conversation request from dotcontrol
      Then Conversation area becomes active with chat to agent user's message
      When Send Do you have a job for me? message for .Control
      Then There is correct suggestion shown on user message "Do you have a job for me?"
      And The suggestion for user message "Do you have a job for me?" with the biggest confidence is added to the input field
      When Agent add additional info "_Edited suggestion" to suggested message
      When Agent click send button
      Then Verify dot .Control returns For information regarding vacancies and posts at General Bank you may visit us_Edited suggestion response during 10 seconds
