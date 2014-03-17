package com.rest.hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Users {

    private Integer uid;

    private String name;

    @JsonProperty(value = "field_cert_id")
    private UserCertUnd certId;

    public Users() {

    }

    public Users(Integer uid, String name, UserCertUnd certId) {
        this.uid = uid;
        this.name = name;
        //this.certId = certId;
    }
    public Integer getUid(){
        return uid;
    }

    public String getName(){
        return name;
    }


    public UserCertUnd getCertId(){
        return certId;
    }


}
