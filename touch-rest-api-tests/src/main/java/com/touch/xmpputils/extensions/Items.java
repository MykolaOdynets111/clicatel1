package com.touch.xmpputils.extensions;

import javax.xml.bind.annotation.*;

/**
 * Created by oshcherbatyy on 06-09-17.
 */

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class Items {

    @XmlElement
    private String data;

    private Items() {
        // Private no-args default constructor for JAXB.
    }

    public Items(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

}