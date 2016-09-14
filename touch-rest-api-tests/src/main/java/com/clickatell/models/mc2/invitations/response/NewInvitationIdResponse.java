package com.clickatell.models.mc2.invitations.response;

/**
 * Created by oshchur on 15.07.2016.
 */
public class NewInvitationIdResponse {
    private String id;

    /**
     * No args constructor for use in serialization
     *
     */
    public NewInvitationIdResponse() {
    }

    /**
     *
     * @param id
     */
    public NewInvitationIdResponse(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public NewInvitationIdResponse withId(String id) {
        this.id = id;
        return this;
    }
}
