/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rest;

import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 *
 * @author alan
 */
public class BasicAuthInterceptor implements ClientHttpRequestInterceptor {

    private final String tk;
    private final String sid;
    private final String sname;
    
    public BasicAuthInterceptor(String token, String sessionId, String sessionName) {
        tk = token;
        sid = sessionId;
        sname = sessionName;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest arg0, byte[] arg1, ClientHttpRequestExecution arg2) throws IOException {
        
        arg0.getHeaders().add("X-CSRF-Token", tk);
        arg0.getHeaders().add("Cookie", sname + "=" + sid);
        
        return arg2.execute(arg0, arg1);
    }
    
}
