package com.rest.hello;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Desmond
 * Date: 06.03.14
 * Time: 15:47
 * To change this template use File | Settings | File Templates.
 */
public class UserCertUnd {

    private ArrayList<UserCertItems> und;

    public UserCertUnd(){

    }

    public UserCertUnd(ArrayList<UserCertItems> und){
        this.und = und;
    }

    public ArrayList<UserCertItems> getUnd() {
        return und;
    }
}
