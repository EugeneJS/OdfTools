package com.rest.hello;

/**
 * Created with IntelliJ IDEA.
 * User: Desmond
 * Date: 27.02.14
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class DrupalUser {
    private Integer uid;
    private DrupalUsersItems usersItem;

    public DrupalUser(){

    }

    public DrupalUser(DrupalUsersItems usersItem){
       this.usersItem = usersItem;
    }

    public Integer getUid(){
        return uid;
    }

}
