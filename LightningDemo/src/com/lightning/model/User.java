package com.lightning.model;

public class User extends LightningModel {
	@Column(name = "name", type = "TEXT", order = 2)
	private String name;

	@Column(name = "email", type = "TEXT", order = 3)
	private String email;

	public User() {
		super("user");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}