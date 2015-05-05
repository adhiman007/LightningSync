package com.lightning.model;

public class User extends LightningModel {
	@Column(name = "userId", type = "INTEGER PRIMARY KEY AUTOINCREMENT", order = 1)
	private int userId;

	@Column(name = "name", type = "TEXT", order = 2)
	private String name;

	@Column(name = "email", type = "TEXT", order = 3)
	private String email;

	public User() {
		super("user");
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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