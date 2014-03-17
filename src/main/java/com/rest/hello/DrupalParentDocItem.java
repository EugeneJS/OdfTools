/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rest.hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author alan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrupalParentDocItem {
    
    @JsonProperty(value = "target_id")
    private Integer targetId;

    public DrupalParentDocItem() {
    }

    public Integer getTargetId() {
        return targetId;
    }

    
    
}
