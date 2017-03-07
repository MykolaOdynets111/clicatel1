package com.touch.models.touch.tenant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kmakohoniuk on 3/7/2017.
 */
public class TenantConfig {
    private Integer csOfferWaitTimeSec = null;
    private String primaryEmail = null;
    private List<String> cc = new ArrayList<String>();

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
