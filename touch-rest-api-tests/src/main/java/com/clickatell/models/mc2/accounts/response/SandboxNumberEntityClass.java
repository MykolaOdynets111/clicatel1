package com.clickatell.models.mc2.accounts.response;

import java.util.Objects;

/**
 * Created by oshchur on 08.08.2016.
 */
public class SandboxNumberEntityClass {
    private String id = null;
    private String number = null;
    private Boolean activated = false;
    private Integer integrationsCount = 0;


    public SandboxNumberEntityClass(String id, String number, Boolean activated, Integer integrationsCount) {
        this.id = id;
        this.number = number;
        this.activated = activated;
        this.integrationsCount = integrationsCount;
    }

    public SandboxNumberEntityClass() {
    }

    /**
     **/
    public SandboxNumberEntityClass id(String id) {
        this.id = id;
        return this;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    /**
     **/
    public SandboxNumberEntityClass number(String number) {
        this.number = number;
        return this;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    /**
     **/
    public SandboxNumberEntityClass activated(Boolean activated) {
        this.activated = activated;
        return this;
    }


    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Integer getIntegrationsCount() {
        return integrationsCount;
    }

    public void setIntegrationsCount(Integer integrationsCount) {
        this.integrationsCount = integrationsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SandboxNumberEntityClass sandboxNumberEntityResponse = (SandboxNumberEntityClass) o;
        return Objects.equals(this.id, sandboxNumberEntityResponse.id) &&
                Objects.equals(this.number, sandboxNumberEntityResponse.number) &&
                Objects.equals(this.activated, sandboxNumberEntityResponse.activated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, activated);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SandboxNumberEntityResponse {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    number: ").append(toIndentedString(number)).append("\n");
        sb.append("    activated: ").append(toIndentedString(activated)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
