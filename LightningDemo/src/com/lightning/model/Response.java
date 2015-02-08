package com.lightning.model;

import java.util.List;

public class Response {
	private List<Contact>	contacts;
//	private Phone			phone;

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

//	public Phone getPhone() {
//		return phone;
//	}
//
//	public void setPhone(Phone phone) {
//		this.phone = phone;
//	}
}
