package com.touch.models.touch.tenant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kmakohoniuk on 3/7/2017.
 */
public class TenantConfig {
    private Integer userWaitingTimeOfferMsg = null;
    private Integer userWaitingTimeOfferCard = null;
    private Integer agentInactivityTimeoutMaxSec = null;
    private Integer agentInactivityTimeoutPreferredSec = null;
    private Integer agentInactivityTimeoutWarnSec = null;
    private Integer agentWaitClientTimeoutMin = null;
    private Integer tbotWaitClientTimeoutMin = null;
    private Integer welcomeMessageTimer = null;
    private Integer showSmCardTimeoutMin = null;
    private String attachmentManager = null;
    private Integer agentOfferTimeoutSec = null;
    private Integer clientOfferTimeoutSec = null;
    private String userWaitingTimeMessage = null;
    private String primaryEmail = null;
    private String timezone = null;
    private String availabilityWidget = null;
    private String welcomeMessage = null;
        private String mode = null;
    private List<String> cc = new ArrayList<String>();

    public Integer getWelcomeMessageTimer() {
        return welcomeMessageTimer;
    }

    public void setWelcomeMessageTimer(Integer welcomeMessageTimer) {
        this.welcomeMessageTimer = welcomeMessageTimer;
    }

    public Integer getUserWaitingTimeOfferMsg() {
        return userWaitingTimeOfferMsg;
    }

    public void setUserWaitingTimeOfferMsg(Integer userWaitingTimeOfferMsg) {
        this.userWaitingTimeOfferMsg = userWaitingTimeOfferMsg;
    }

    public Integer getUserWaitingTimeOfferCard() {
        return userWaitingTimeOfferCard;
    }

    public void setUserWaitingTimeOfferCard(Integer userWaitingTimeOfferCard) {
        this.userWaitingTimeOfferCard = userWaitingTimeOfferCard;
    }

    public Integer getAgentOfferTimeoutSec() {
        return agentOfferTimeoutSec;
    }

    public void setAgentOfferTimeoutSec(Integer agentOfferTimeoutSec) {
        this.agentOfferTimeoutSec = agentOfferTimeoutSec;
    }

    public Integer getClientOfferTimeoutSec() {
        return clientOfferTimeoutSec;
    }

    public void setClientOfferTimeoutSec(Integer clientOfferTimeoutSec) {
        this.clientOfferTimeoutSec = clientOfferTimeoutSec;
    }

    public String getUserWaitingTimeMessage() {
        return userWaitingTimeMessage;
    }

    public void setUserWaitingTimeMessage(String userWaitingTimeMessage) {
        this.userWaitingTimeMessage = userWaitingTimeMessage;
    }

    public Integer getAgentInactivityTimeoutMaxSec() {
        return agentInactivityTimeoutMaxSec;
    }

    public void setAgentInactivityTimeoutMaxSec(Integer agentInactivityTimeoutMaxSec) {
        this.agentInactivityTimeoutMaxSec = agentInactivityTimeoutMaxSec;
    }

    public Integer getAgentInactivityTimeoutPreferredSec() {
        return agentInactivityTimeoutPreferredSec;
    }

    public void setAgentInactivityTimeoutPreferredSec(Integer agentInactivityTimeoutPreferredSec) {
        this.agentInactivityTimeoutPreferredSec = agentInactivityTimeoutPreferredSec;
}

    public Integer getAgentInactivityTimeoutWarnSec() {
        return agentInactivityTimeoutWarnSec;
    }

    public void setAgentInactivityTimeoutWarnSec(Integer agentInactivityTimeoutWarnSec) {
        this.agentInactivityTimeoutWarnSec = agentInactivityTimeoutWarnSec;
    }

    public String getAttachmentManager() {
        return attachmentManager;
    }

    public void setAttachmentManager(String attachmentManager) {
        this.attachmentManager = attachmentManager;
    }

    public Integer getAgentWaitClientTimeoutMin() {
        return agentWaitClientTimeoutMin;
    }

    public void setAgentWaitClientTimeoutMin(Integer agentWaitClientTimeoutMin) {
        this.agentWaitClientTimeoutMin = agentWaitClientTimeoutMin;
    }

    public Integer getTbotWaitClientTimeoutMin() {
        return tbotWaitClientTimeoutMin;
    }

    public void setTbotWaitClientTimeoutMin(Integer tbotWaitClientTimeoutMin) {
        this.tbotWaitClientTimeoutMin = tbotWaitClientTimeoutMin;
    }

    public Integer getShowSmCardTimeoutMin() {
        return showSmCardTimeoutMin;
    }

    public void setShowSmCardTimeoutMin(Integer showSmCardTimeoutMin) {
        this.showSmCardTimeoutMin = showSmCardTimeoutMin;
    }


    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getAvailabilityWidget() { return availabilityWidget; }

    public void setAvailabilityWidget(String availabilityWidget) { this.availabilityWidget = availabilityWidget; }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }


    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }



    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TenantConfig that = (TenantConfig) o;


        if (primaryEmail != null ? !primaryEmail.equals(that.primaryEmail) : that.primaryEmail != null) return false;
        return cc != null ? cc.equals(that.cc) : that.cc == null;

    }

    @Override
    public int hashCode() {
        int result = userWaitingTimeOfferMsg != null ? userWaitingTimeOfferMsg.hashCode() : 0;
        result = 31 * result + (userWaitingTimeOfferCard != null ? userWaitingTimeOfferCard.hashCode() : 0);
        result = 31 * result + (agentInactivityTimeoutMaxSec != null ? agentInactivityTimeoutMaxSec.hashCode() : 0);
        result = 31 * result + (agentInactivityTimeoutPreferredSec != null ? agentInactivityTimeoutPreferredSec.hashCode() : 0);
        result = 31 * result + (agentInactivityTimeoutWarnSec != null ? agentInactivityTimeoutWarnSec.hashCode() : 0);
        result = 31 * result + (agentWaitClientTimeoutMin != null ? agentWaitClientTimeoutMin.hashCode() : 0);
        result = 31 * result + (tbotWaitClientTimeoutMin != null ? tbotWaitClientTimeoutMin.hashCode() : 0);
        result = 31 * result + (showSmCardTimeoutMin != null ? showSmCardTimeoutMin.hashCode() : 0);
        result = 31 * result + (attachmentManager != null ? attachmentManager.hashCode() : 0);
        result = 31 * result + (agentOfferTimeoutSec != null ? agentOfferTimeoutSec.hashCode() : 0);
        result = 31 * result + (clientOfferTimeoutSec != null ? clientOfferTimeoutSec.hashCode() : 0);
        result = 31 * result + (userWaitingTimeMessage != null ? userWaitingTimeMessage.hashCode() : 0);
        result = 31 * result + (primaryEmail != null ? primaryEmail.hashCode() : 0);
        result = 31 * result + (timezone != null ? timezone.hashCode() : 0);
        result = 31 * result + (mode != null ? mode.hashCode() : 0);
        result = 31 * result + (welcomeMessage != null ? welcomeMessage.hashCode() : 0);
        result = 31 * result + (availabilityWidget != null ? availabilityWidget.hashCode() : 0);
        result = 31 * result + (cc != null ? cc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TenantConfig{" +
                "userWaitingTimeOfferMsg=" + userWaitingTimeOfferMsg +
                ", userWaitingTimeOfferCard=" + userWaitingTimeOfferCard +
                ", agentInactivityTimeoutMaxSec=" + agentInactivityTimeoutMaxSec +
                ", agentInactivityTimeoutPreferredSec=" + agentInactivityTimeoutPreferredSec +
                ", agentInactivityTimeoutWarnSec=" + agentInactivityTimeoutWarnSec +
                ", agentWaitClientTimeoutMin=" + agentWaitClientTimeoutMin +
                ", tbotWaitClientTimeoutMin=" + tbotWaitClientTimeoutMin +
                ", showSmCardTimeoutMin=" + showSmCardTimeoutMin +
                ", attachmentManager='" + attachmentManager + '\'' +
                ", agentOfferTimeoutSec=" + agentOfferTimeoutSec +
                ", clientOfferTimeoutSec=" + clientOfferTimeoutSec +
                ", userWaitingTimeMessage=" + userWaitingTimeMessage +
                ", primaryEmail='" + primaryEmail + '\'' +
                ", timezone='" + timezone + '\'' +
                ", welcomeMessage='" + welcomeMessage + '\'' +
                ", mode='" + mode + '\'' +
                ", availabilityWidget='" + availabilityWidget + '\'' +
                ", cc=" + cc +
                '}';
    }
}
