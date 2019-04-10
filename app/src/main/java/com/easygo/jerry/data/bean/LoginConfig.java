package com.easygo.jerry.data.bean;

import java.io.Serializable;

/**
 * Create by wujiewei on 2019/4/6
 */
public class LoginConfig implements Serializable {

    private int id;

    private int u_id;

    private String phone;

    private String password;

    private int is_remember_pwd;

    private int is_auto_login;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIs_remember_pwd() {
        return is_remember_pwd;
    }

    public void setIs_remember_pwd(int is_remember_pwd) {
        this.is_remember_pwd = is_remember_pwd;
    }

    public int getIs_auto_login() {
        return is_auto_login;
    }

    public void setIs_auto_login(int is_auto_login) {
        this.is_auto_login = is_auto_login;
    }
}
