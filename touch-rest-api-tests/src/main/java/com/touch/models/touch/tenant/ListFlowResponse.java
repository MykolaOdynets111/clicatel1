
package com.touch.models.touch.tenant;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class ListFlowResponse {

    private List<FlowResponse> flows = new ArrayList<FlowResponse>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ListFlowResponse() {
    }

    /**
     * 
     * @param flows
     */
    public ListFlowResponse(List<FlowResponse> flows) {
        this.flows = flows;
    }

    /**
     * 
     * @return
     *     The flows
     */
    public List<FlowResponse> getFlows() {
        return flows;
    }

    /**
     * 
     * @param flows
     *     The flows
     */
    public void setFlows(List<FlowResponse> flows) {
        this.flows = flows;
    }

    public ListFlowResponse withFlows(List<FlowResponse> flows) {
        this.flows = flows;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(flows).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ListFlowResponse) == false) {
            return false;
        }
        ListFlowResponse rhs = ((ListFlowResponse) other);
        return new EqualsBuilder().append(flows, rhs.flows).isEquals();
    }

}
