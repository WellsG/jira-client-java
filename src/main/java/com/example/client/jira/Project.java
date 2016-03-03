package com.example.client.jira;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Project implements Serializable {

    private String name;

    private Integer id;

    private String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
