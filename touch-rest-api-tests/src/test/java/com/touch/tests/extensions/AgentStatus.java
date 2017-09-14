package com.touch.tests.extensions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by oshcherbatyy on 07-09-17.
 */
@XmlRootElement(name = "agent-status", namespace = "clickatell:touch:agent-manager")
@XmlAccessorType(XmlAccessType.FIELD)
public class AgentStatus {
    @XmlElement(name = "max-chats")
    private String maxChats;

    private AgentStatus(){

    }

    public AgentStatus(String maxChats) {
        this.maxChats = maxChats;
    }

    public String getMaxChats() {
        return maxChats;
    }
}
