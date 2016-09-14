package com.clickatell.models.mc2.accounts.request;

import java.util.Objects;

/**
 * Created by oshchur on 08.08.2016.
 */
public class ActivationCodeRequest {
    private Integer code = null;

    /**
     **/
    public ActivationCodeRequest code(Integer code) {
        this.code = code;
        return this;
    }

    public ActivationCodeRequest(String code) {
        this.code = Integer.parseInt(code);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActivationCodeRequest activationCodeRequest = (ActivationCodeRequest) o;
        return Objects.equals(this.code, activationCodeRequest.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ActivationCodeRequest {\n");

        sb.append("    code: ").append(toIndentedString(code)).append("\n");
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
