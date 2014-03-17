package com.rest.hello;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Desmond
 * Date: 06.03.14
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public class UsersItem {



    @JsonProperty(value = "uid")
    private Integer uid;

    //private ArrayList<UsersItem> items;

    public UsersItem(){

    }

    public UsersItem(Integer uid) {
        this.uid = uid;
    }

    public Integer getUid(){
        return uid;
    }


}
