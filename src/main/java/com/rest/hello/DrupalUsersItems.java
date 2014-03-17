package com.rest.hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Desmond
 * Date: 27.02.14
 * Time: 11:11
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrupalUsersItems {
    private Integer uid;

    public DrupalUsersItems(){

    }

    public DrupalUsersItems(Integer uid){
        this.uid = uid;
    }

    private Integer getUid(){
        return uid;
    }
}
