package com.touch.tests.extensions;

import javax.xml.bind.annotation.*;
import java.io.IOException;

/**
 * Created by oshcherbatyy on 06-09-17.
 */
@XmlRootElement(name = "tcard_submit")
@XmlAccessorType(XmlAccessType.FIELD)
public class TcardSubmit {

    @XmlAttribute(name = "action")
    private String action;


    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "tcard_name")
    private String tcardName;


    @XmlElement(name = "data")
    private String data;

    public TcardSubmit() {
    }

    public TcardSubmit(String action, String tcardName, String id, String data) {
        this.action = action;
        this.id = id;
        this.tcardName = tcardName;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTcardName() {
        return tcardName;
    }

    public void setTcardName(String tcardName) {
        this.tcardName = tcardName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public void setCDATA(String cdata) throws IOException {
        this.data = "<![CDATA[\"" + cdata + "\"]]>";
    }

}