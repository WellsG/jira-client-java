package com.example.client.jira;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Component implements Serializable {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
