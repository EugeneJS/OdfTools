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
public class DrupalFile {

    private Integer fid;
    private Integer uid;

    private String filename;
    private String file;
    private String uri;

    public DrupalFile() {
    }

        
    public DrupalFile(Integer uid, String filename, String file) {
        this.uid = uid;
        this.filename = filename;
        this.file = file;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getFid() {
        return fid;
    }

    public String getUri() {
        return uri;
    }

    public DrupalFile(String filename, String uri) {
        this.filename = filename;
        this.uri = uri;
    }

    

    
    
    
    

}
