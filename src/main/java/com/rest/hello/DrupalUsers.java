package com.rest.hello;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Desmond
 * Date: 27.02.14
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
public class DrupalUsers {
    private ArrayList<DrupalUsersItems> result;

    public DrupalUsers(){

    }

    public DrupalUsers(ArrayList<DrupalUsersItems> fieldUser){
        this.result = fieldUser;
    }
}
