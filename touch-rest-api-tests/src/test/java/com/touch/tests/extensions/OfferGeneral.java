package com.touch.tests.extensions;

import javax.xml.bind.annotation.*;

/**
 * Created by oshcherbatyy on 07-09-17.
 */
@XmlRootElement(name = "offer", namespace = "clickatell:touch:agent-manager")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferGeneral {
    @XmlAttribute
    private String id;

    @XmlAttribute(name = "room-jid")
    private String roomJid;

    @XmlElement
    private String status;

    @XmlElement
    private String timeout;

    @XmlElement
    private String room;

    @XmlElement(name = "client-id")
    private String clientId;

    @XmlElement(name = "client-jid")
    private String clientJid;

    @XmlElement(name = "session-id")
    private String sessionId;

    private OfferGeneral(){

    }

    public OfferGeneral(String id, String roomJid, String status, String timeout, String room, String clientId, String clientJid, String sessionId) {
        this.id = id;
        this.roomJid = roomJid;
        this.status = status;
        this.timeout = timeout;
        this.room = room;
        this.clientId = clientId;
        this.clientJid = clientJid;
        this.sessionId = sessionId;
    }

    public String getId() {
        return id;
    }

    public String getTimeout() {
        return timeout;
    }

    public String getRoom() {
        return room;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientJid() {
        return clientJid;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getRoomJid() {
        return roomJid;
    }

    public String getStatus() {
        return status;
    }
}
