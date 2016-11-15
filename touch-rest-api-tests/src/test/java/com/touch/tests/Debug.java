package com.touch.tests;

import com.clickatell.touch.tbot.xmpp.XmppClient;
import com.touch.models.touch.chats.ChatRoomResponse;
import com.touch.models.touch.integration.IntegrationUserLoginMC2Response;
import com.touch.utils.ApplicationProperties;
import tigase.jaxmpp.core.client.BareJID;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by kmakohoniuk on 9/8/2016.
 */
public class Debug extends BaseTestClass {

    IntegrationUserLoginMC2Response response;
    String signInToken;

    //@Test
    public void testUSerAgent() {
        ArrayList<String> messages = new ArrayList<>();
        String tenantWithBot = "20a9c80d53fb11e6a0280626baf6c11d";
        ChatRoomResponse chatRoom = chatsActions.getChatRoom(tenantWithBot, "testclient1@clickatelllabs.com", "test1").as(ChatRoomResponse.class);
        XmppClient client = new XmppClient(ApplicationProperties.getInstance().getPropertyByName("xmpp.host"), 5222, ApplicationProperties.getInstance().getPropertyByName("xmpp.domain"), 30000, null);
        client.connect();

        client.addMucYouJoinedHandler((sessionObject, room1, asNickname) -> {
            System.out.println("Joined to room " + room1 + " as " + asNickname);
        });

        client.addMucMessageReceivedHandler((sessionObject, message, room1, nickname, timestamp) -> {
            try {
                System.out.println("Received message " + message.getBody());
                messages.add(message.getBody());

            } catch (Exception e) {
                System.out.println("Failed to receive messages: " + e.getMessage());
            }
        });
        BareJID room = BareJID.bareJIDInstance(chatRoom.getChatroomJid());
//    BareJID room = BareJID.bareJIDInstance("fy1523wd45hdaxs743gxu2epdjowgm40@muc.clickatelllabs.com");
        client.joinRoom(room.getLocalpart(), "muc.clickatelllabs.com", "client1");
//    client.joinRoom(room.getLocalpart(), "muc.clickatelllabs.com", "client2");
        String localpart = room.getLocalpart();
        client.sendRoomMessage(room, "Teststee1");
        client.sendRoomMessage(room, "Teststee2");
        client.sendMessage("testclient1@clickatelllabs.com", "RRR", "Teststee2");
        for (String m : messages) {
            System.out.println("Test Message: " + m);
        }
        int i = 0;
    }



//        @Test
//    public void deleteAllTestUserProfiles() {
//        ListUserProfilesResponse allUserProfiles = userProfileActions.getAllUserProfiles(ListUserProfilesResponse.class);
//        int size = allUserProfiles.getUserProfiles().size();
//        for (UserProfileResponse userProfile : allUserProfiles.getUserProfiles()) {
//            String id = userProfile.getChatroomJid();
//            if (userProfile.getName().contains("Test"))
//                userProfileActions.deleteUserProfile(userProfile.getChatroomJid());
//        }
//    }

//        @Test
//    public void deleteAllTestTenants() {
//        List<TenantResponseV4> tenantsList = tenantActions.getTenantsList();
//        for (TenantResponseV4 tenant : tenantsList) {
//            if (tenant.getTenantOrgName().contains("Test") || tenant.getTenantOrgName().contains("test"))
//                tenantActions.deleteTenant(tenant.getChatroomJid());
//        }
//    }


    private String getFullPathToFile(String pathToFile) {
        return Debug.class.getClassLoader().getResource(pathToFile).getPath();
    }

    private boolean isEqualInputStreams(InputStream i1, InputStream i2) throws IOException {

        try {
            // do the compare
            while (true) {
                int fr = i1.read();
                int tr = i2.read();

                if (fr != tr)
                    return false;

                if (fr == -1)
                    return true;
            }

        } finally {
            if (i1 != null)
                i1.close();
            if (i2 != null)
                i2.close();
        }
    }
}
