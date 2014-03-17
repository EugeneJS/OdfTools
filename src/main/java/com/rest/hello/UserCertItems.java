package com.rest.hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created with IntelliJ IDEA.
 * User: Desmond
 * Date: 06.03.14
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCertItems {

    private String value;

    public UserCertItems() {
    }

    public UserCertItems(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
