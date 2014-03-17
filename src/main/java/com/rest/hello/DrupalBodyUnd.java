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
public class DrupalBodyUnd {
    @JsonProperty(value = "und")
    private ArrayList<DrupalBodyItems> und;

    public DrupalBodyUnd() {
    }

    public DrupalBodyUnd(ArrayList<DrupalBodyItems> und) {
        this.und = und;
    }
    
    
}
