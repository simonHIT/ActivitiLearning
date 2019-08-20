package com.simon.activiti.model;

import java.io.Serializable;

/**
 * @program: activiti
 * @description: user 实体类
 * @author: simon
 * @create: 2019-08-20 15:27
 **/
public class User implements Serializable {


    //private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
