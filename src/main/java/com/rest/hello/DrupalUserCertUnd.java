package com.rest.hello;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Desmond
 * Date: 27.02.14
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */
public class DrupalUserCertUnd {
    @JsonProperty(value = "value")
    private ArrayList<DrupalUserCertItem> value;

    public DrupalUserCertUnd() {
    }

    public ArrayList<DrupalUserCertItem> getValue() {
        return value;
    }

    public DrupalUserCertUnd(ArrayList<DrupalUserCertItem> value) {
        this.value = value;
    }
}
