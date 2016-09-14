
package com.clickatell.models;

public class ErrorMessage {

    protected String errorMessage;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ErrorMessage() {
    }

    /**
     * 
     * @param errorMessage
     */
    public ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 
     * @return
     *     The errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 
     * @param errorMessage
     *     The errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
