/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rest.hello;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 *
 * @author alan
 */
public class DrupalDocIdUnd {
    
    @JsonProperty(value = "und")
    private ArrayList<DrupalDocIdItem> undDocId;

    public DrupalDocIdUnd() {
    }

    public ArrayList<DrupalDocIdItem> getUndDocId() {
        return undDocId;
    }

    public DrupalDocIdUnd(ArrayList<DrupalDocIdItem> undDocId) {
        this.undDocId = undDocId;
    }

    
    

    
    
    
    
    
}
