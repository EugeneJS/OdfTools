package com.rest.hello;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Desmond
 * Date: 27.02.14
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class DrupalUserCertItem {
    @JsonProperty(value = "field_cert_id")
    private Integer fieldCertId;
    private DrupalUser user;

    public DrupalUserCertItem(){

    }

    public DrupalUserCertItem(DrupalUser user){
        this.user = user;
    }

    public Integer getFieldCertId(){
        return fieldCertId;
    }
}
