package com.touch.tests.extensions;

import javax.xml.bind.annotation.*;

/**
 * Created by oshcherbatyy on 07-09-17.
 */
@XmlRootElement(name = "offer", namespace = "clickatell:touch:agent-manager")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferAccepted {
    @XmlAttribute
    private String id;

    @XmlAttribute(name = "room-jid")
    private String roomJid;

    @XmlElement
    private String status;

    private OfferAccepted(){

    }

    public OfferAccepted(String id, String roomJid, String status) {
        this.id = id;
        this.roomJid = roomJid;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getRoomJid() {
        return roomJid;
    }

    public String getStatus() {
        return status;
    }
}