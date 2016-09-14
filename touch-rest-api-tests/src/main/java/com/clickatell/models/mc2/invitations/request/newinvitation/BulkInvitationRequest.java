
package com.clickatell.models.mc2.invitations.request.newinvitation;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class BulkInvitationRequest {

    private List<Invitation> invitations = new ArrayList<Invitation>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public BulkInvitationRequest() {
    }

    /**
     * 
     * @param invitations
     */
    public BulkInvitationRequest(List<Invitation> invitations) {
        this.invitations = invitations;
    }

    /**
     * 
     * @return
     *     The invitations
     */
    public List<Invitation> getInvitations() {
        return invitations;
    }

    /**
     * 
     * @param invitations
     *     The invitations
     */
    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }

    public BulkInvitationRequest withInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
