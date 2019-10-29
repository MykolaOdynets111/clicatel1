@start_server
@no_widget
@dot_control
@without_tct

  Feature: Suggestion editing .Control

    Scenario: Sending edited suggestion via .Control
      Given Create .Control integration for Automation tenant
      Given I login as agent of Automation
      When Prepare payload for sending chat to agent message for .Control
      Given Send parameterized init call with clientId context correct response is returned
      When Send message call
      Then Agent has new conversation request from dotcontrol user
      When Agent click on new conversation request from dotcontrol
      Then Conversation area becomes active with chat to agent user's message
      When Send Do you have a job for me? message for .Control
      Then There is correct suggestion shown on user message "Do you have a job for me?"
      And The suggestion for user message "Do you have a job for me?" with the biggest confidence is added to the input field
      When Agent add additional info "_Edited suggestion" to suggested message
      When Agent click send button
      Then Verify dot .Control returns edited response in 13 seconds
