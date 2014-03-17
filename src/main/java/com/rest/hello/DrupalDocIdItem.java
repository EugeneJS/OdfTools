/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rest.hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author alan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrupalDocIdItem {
 
    private String value;

    public DrupalDocIdItem() {
    }

    public String getValue() {
        return value;
    }

    public DrupalDocIdItem(String value) {
        this.value = value;
    }

     
    
    
}
