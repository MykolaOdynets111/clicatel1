package com.touch.xmpputils.extensions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by oshcherbatyy on 07-09-17.
 */
@XmlRootElement(name = "offer-cancel", namespace = "clickatell:touch:agent-manager")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferCancel {
    @XmlAttribute
    private String id;

    private OfferCancel(){

    }

    public OfferCancel(String id) {
        this.id = id;

    }

    public String getId() {
        return id;
    }

}
