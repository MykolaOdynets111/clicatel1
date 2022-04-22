
package com.touch.models.touch.tenant;

import javax.annotation.Generated;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class VersionFlow {

    private int version;
    private String path;

    /**
     * No args constructor for use in serialization
     * 
     */
    public VersionFlow() {
    }

    /**
     * 
     * @param path
     * @param version
     */
    public VersionFlow(int version, String path) {
        this.version = version;
        this.path = path;
    }

    /**
     * 
     * @return
     *     The version
     */
    public long getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    public VersionFlow withVersion(int version) {
        this.version = version;
        return this;
    }

    /**
     * 
     * @return
     *     The path
     */
    public String getPath() {
        return path;
    }

    /**
     * 
     * @param path
     *     The path
     */
    public void setPath(String path) {
        this.path = path;
    }

    public VersionFlow withPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(version).append(path).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof VersionFlow) == false) {
            return false;
        }
        VersionFlow rhs = ((VersionFlow) other);
        return new EqualsBuilder().append(version, rhs.version).append(path, rhs.path).isEquals();
    }

}
