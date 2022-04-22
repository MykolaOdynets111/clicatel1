
package com.touch.models.touch.tenant;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class FlowResponse {

    private String id;
    private String fileName;
    private List<VersionFlow> versions = new ArrayList<VersionFlow>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public FlowResponse() {
    }

    /**
     * 
     * @param id
     * @param versions
     * @param fileName
     */
    public FlowResponse(String id, String fileName, List<VersionFlow> versions) {
        this.id = id;
        this.fileName = fileName;
        this.versions = versions;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public FlowResponse withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 
     * @param fileName
     *     The fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FlowResponse withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     * 
     * @return
     *     The versions
     */
    public List<VersionFlow> getVersions() {
        return versions;
    }

    /**
     * 
     * @param versions
     *     The versions
     */
    public void setVersions(List<VersionFlow> versions) {
        this.versions = versions;
    }

    public FlowResponse withVersions(List<VersionFlow> versionFlowses) {
        this.versions = versionFlowses;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(fileName).append(versions).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FlowResponse) == false) {
            return false;
        }
        FlowResponse rhs = ((FlowResponse) other);
        return new EqualsBuilder().append(id, rhs.id).append(fileName, rhs.fileName).append(versions, rhs.versions).isEquals();
    }

}
