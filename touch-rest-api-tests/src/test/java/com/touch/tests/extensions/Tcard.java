package com.touch.tests.extensions;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.IOException;

/**
 * Created by oshcherbatyy on 06-09-17.
 */
@XmlRootElement(name = "tcard", namespace = "jabber:client")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tcard {

    @XmlAttribute(name = "tcard_name")
    private String tcardName;


    @XmlElement(name = "data")
    private String data;


    private Tcard(){

    }

    public Tcard(String tcardName, String data) {
        this.tcardName = tcardName;
        this.data = data;
    }

    public String getTcardName() {
        return tcardName;
    }

    public String getData() {
        return data;
    }

    public <T> T getJsonCDATA(Class<T> clazz) throws IOException {
        String jsonCDATAData = getData();
        //cut CDATA XML prefix and postfix
        String jsonData = jsonCDATAData.substring(new String("<![CDATA[").length(), jsonCDATAData.length() - new String("]]>").length());
        return new ObjectMapper().readValue(jsonData, clazz);
    }

}
