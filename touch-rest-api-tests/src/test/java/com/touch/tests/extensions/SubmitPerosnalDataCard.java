package com.touch.tests.extensions;

import javax.xml.bind.annotation.*;

/**
 * Created by oshcherbatyy on 06-09-17.
 */
@XmlRootElement(name = "tcard_submit")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubmitPerosnalDataCard {

    @XmlAttribute
    private String action;

    @XmlAttribute(name = "tcard_name")
    private String tcard_name;

    @XmlAttribute
    private String id;

    @XmlElement
    private String data;

    private SubmitPerosnalDataCard(){

    }

    public SubmitPerosnalDataCard(String action, String tcard_name, String id, String data) {
        this.action = action;
        this.tcard_name = tcard_name;
        this.id = id;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public String gettTcard_name() {
        return tcard_name;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }
}
