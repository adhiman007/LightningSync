package com.lightning.model;



public class Phone extends LightningModel {

	@Column(name = "id", type = "TEXT", order = 1)
	private String	id;

	@Column(name = "mobile", type = "TEXT", order = 2)
	private String	mobile;

	@Column(name = "home", type = "TEXT", order = 3)
	private String	home;

	@Column(name = "office", type = "TEXT", order = 4)
	private String	office;

	public Phone() {
		super("phone");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
}