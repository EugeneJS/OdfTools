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
public class DrupalNode {
    
    private Integer nid;
    
    private String title;
    @JsonProperty(value = "field_doc_num")
    private DrupalDocIdUnd filedDocNum;
    private DrupalBodyUnd body;
    private String type;

    @JsonProperty(value = "field_parent_doc")
    private DrupalParentDocUnd parentDoc;
    @JsonProperty(value = "field_workers")
    private DrupalWorkersUnd workers;
    @JsonProperty(value = "field_files")
    private DrupalFiles files;
    @JsonProperty(value = "field_cert_id")
    private DrupalUserCertUnd certId;


    public DrupalNode() {
    }

    public DrupalNode(String title, DrupalDocIdUnd filedDocNum, DrupalBodyUnd body, DrupalWorkersUnd workers,
                      DrupalParentDocUnd parentDoc, DrupalFiles files, String type) {
        this.title = title;
        this.filedDocNum = filedDocNum;
        this.body = body;
        this.type = type;
        this.parentDoc = parentDoc;
        this.workers = workers;
        this.files = files;
        //this.certId = certId;
    }

    public DrupalNode(String title, DrupalDocIdUnd filedDocNum, DrupalBodyUnd body,
                      DrupalParentDocUnd parentDoc, DrupalFiles files, String type) {
        this.title = title;
        this.filedDocNum = filedDocNum;
        this.body = body;
        this.type = type;
        this.parentDoc = parentDoc;
        this.files = files;
    }

    public String getTitle() {
        return title;
    }

    public DrupalBodyUnd getBody() {
        return body;
    }

    public DrupalParentDocUnd getField_parent_doc() {
        return parentDoc;
    }

    public DrupalWorkersUnd getField_workers() {
        return workers;
    }

    public DrupalFiles getField_files() {
        return files;
    }

    public Integer getNid() {
        return nid;
    }

    public String getType() {
        return type;
    }

    /*public DrupalUserCertUnd getCertId(){
        return certId;
    } */
    
        
    
    
}
