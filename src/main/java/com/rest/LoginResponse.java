/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author alan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class LoginResponse {
    
    private String token;
    private String sessid;
    @JsonProperty(value = "session_name")
    private String sessionName;

    public String getToken() {
        return token;
    }

    public String getSessid() {
        return sessid;
    }

    public String getSessionName() {
        return sessionName;
    }
    
    
    
    
    
}
