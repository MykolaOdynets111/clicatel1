@twitter
@agent_to_user_conversation
@chat_transcript
@without_tct

  Feature: Chat Transcript for Twitter
    Scenario: Agent receiving chat transcript after Twitter conversation ends
      Given Set Chat Transcript attribute to ALL for General Bank Demo tenant
      Given Clear Chat Transcript email inbox
      Given I login as agent of General Bank Demo
      Given Open twitter page of General Bank Demo
      Given Open direct message channel
      When User sends twitter direct message regarding chat to support
      Then Agent has new conversation request from twitter user
      When Agent click on new conversation request from twitter
      And Save clientID value for twitter user
      Then Conversation area becomes active with chat to support message from twitter user
      When Agent responds with check chat transcript email to User
      Then Agent closes chat
      Then Chat Transcript email arrives
      And Email title contains twdm adapter, client ID/Name/Email, chat ID, session number values
      And Email content contains chat history from the terminated conversation