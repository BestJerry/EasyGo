package com.easygo.jerry.data.bean;

import java.io.Serializable;

/**
 * Create by wujiewei on 2019/4/6
 */
public class Dynamic implements Serializable {

    private int id;

    private int u_id;

    private String name;

    private String content;

    private String date;

    private String location;

    private String recommend_level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRecommend_level() {
        return recommend_level;
    }

    public void setRecommend_level(String recommend_level) {
        this.recommend_level = recommend_level;
    }
}
