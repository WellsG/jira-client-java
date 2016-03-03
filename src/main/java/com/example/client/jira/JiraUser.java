package com.example.client.jira;

public class JiraUser {

    private final String username;

    private final String password;

    public JiraUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
