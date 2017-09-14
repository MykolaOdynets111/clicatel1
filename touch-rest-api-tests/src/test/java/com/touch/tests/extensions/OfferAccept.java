package com.touch.tests.extensions;

import javax.xml.bind.annotation.*;

/**
 * Created by oshcherbatyy on 07-09-17.
 */
@XmlRootElement(name = "offer-accept", namespace = "clickatell:touch:agent-manager")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferAccept {
    @XmlAttribute
    private String id;

    private OfferAccept(){

    }

    public OfferAccept(String id) {
        this.id = id;

    }

    public String getId() {
        return id;
    }

}
