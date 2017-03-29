package com.touch.models.touch.tenant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kmakohoniuk on 3/7/2017.
 */
public class TenantConfig {
    private Integer csOfferWaitTimeSec = null;
    private Integer agentInactivityTimeoutSec = null;
    private Integer agentWaitClientTimeoutMin = null;
    private Integer tbotWaitClientTimeoutMin = null;
    private Integer showSmCardTimeoutMin = null;
    private String primaryEmail = null;
    private String timezone = null;
    private List<String> cc = new ArrayList<String>();

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

    public Integer getCsOfferWaitTimeSec() {
        return csOfferWaitTimeSec;
    }

    public void setCsOfferWaitTimeSec(Integer csOfferWaitTimeSec) {
        this.csOfferWaitTimeSec = csOfferWaitTimeSec;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public Integer getAgentInactivityTimeoutSec() {
        return agentInactivityTimeoutSec;
    }

    public void setAgentInactivityTimeoutSec(Integer agentInactivityTimeoutSec) {
        this.agentInactivityTimeoutSec = agentInactivityTimeoutSec;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TenantConfig that = (TenantConfig) o;

        if (csOfferWaitTimeSec != null ? !csOfferWaitTimeSec.equals(that.csOfferWaitTimeSec) : that.csOfferWaitTimeSec != null)
            return false;
        if (primaryEmail != null ? !primaryEmail.equals(that.primaryEmail) : that.primaryEmail != null) return false;
        return cc != null ? cc.equals(that.cc) : that.cc == null;

    }

    @Override
    public int hashCode() {
        int result = csOfferWaitTimeSec != null ? csOfferWaitTimeSec.hashCode() : 0;
        result = 31 * result + (primaryEmail != null ? primaryEmail.hashCode() : 0);
        result = 31 * result + (cc != null ? cc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TenantConfig{" +
                "csOfferWaitTimeSec=" + csOfferWaitTimeSec +
                ", primaryEmail='" + primaryEmail + '\'' +
                ", cc=" + cc +
                '}';
    }
}
