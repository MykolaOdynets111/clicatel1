#@start_server
#@no_widget
#@dot_control
#@chat_transcript
#@without_tct
#
#  Feature: Chat Transcript .Control
#
#    Scenario: Agent receiving chat transcript after .Control conversation ends
#      Given Create .Control integration for Automation tenant
#      Given Set Chat Transcript attribute to ALL for Automation tenant
#      Given Clear Chat Transcript email inbox
#      Given I login as agent of Automation
#      When Send chat to agent message for .Control
#      Then Agent has new conversation request from dotcontrol user
#      When Agent click on new conversation request from dotcontrol
#      And Save clientID value for dotcontrol user
#      Then Conversation area becomes active with chat to agent user's message
#      When Agent responds with check chat transcript email to User
#      Then Agent closes chat
#      Then Chat Transcript email arrives
#      And Email title contains dotcontrol adapter, client ID/Name/Email, chat ID, session number values
#      And Email content contains chat history from the terminated conversation