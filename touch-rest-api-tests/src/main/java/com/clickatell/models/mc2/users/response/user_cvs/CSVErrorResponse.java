package com.clickatell.models.mc2.users.response.user_cvs;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by oshchur on 21.07.2016.
 */
public class CSVErrorResponse {
    private long lineNumber;
    private String line;
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public CSVErrorResponse() {
    }

    /**
     *
     * @param message
     * @param line
     * @param lineNumber
     */
    public CSVErrorResponse(long lineNumber, String line, String message) {
        this.lineNumber = lineNumber;
        this.line = line;
        this.message = message;
    }

    /**
     *
     * @return
     * The lineNumber
     */
    public long getLineNumber() {
        return lineNumber;
    }

    /**
     *
     * @param lineNumber
     * The lineNumber
     */
    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public CSVErrorResponse withLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    /**
     *
     * @return
     * The line
     */
    public String getLine() {
        return line;
    }

    /**
     *
     * @param line
     * The line
     */
    public void setLine(String line) {
        this.line = line;
    }

    public CSVErrorResponse withLine(String line) {
        this.line = line;
        return this;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public CSVErrorResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(lineNumber).append(line).append(message).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CSVErrorResponse) == false) {
            return false;
        }
        CSVErrorResponse rhs = ((CSVErrorResponse) other);
        return new EqualsBuilder().append(lineNumber, rhs.lineNumber).append(line, rhs.line).append(message, rhs.message).isEquals();
    }
}
