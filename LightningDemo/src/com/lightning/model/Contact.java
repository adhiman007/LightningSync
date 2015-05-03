package com.lightning.model;



public class Contact extends LightningModel {

	@Column(name = "id", type = "TEXT", order = 1)
	private String				id;

	@Column(name = "name", type = "TEXT", order = 2)
	private String				name;

	@Column(name = "email", type = "TEXT", order = 3)
	private String				email;

	@Column(name = "address", type = "TEXT", order = 4)
	private String				address;

	@Column(name = "gender", type = "TEXT", order = 5)
	private String				gender;

	private Phone				phone;

	public Contact() {
		super("contact");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}
}