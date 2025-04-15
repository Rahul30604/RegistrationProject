package com.Service1.Registration.dto;


public class User {
	private String Id;
	private String name;
	private String email;
	private String mobile;
	private String city;
	public User() {}

    public User(String Id,String name, String email, String mobile, String city) {
        this.Id=Id;
    	this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.city = city;
    }

    // Getters
    public String getId() {
    	return Id;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCity() {
        return city;
    }

    // Setters
    public void setId(String Id) {
    	this.Id=Id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCity(String city) {
        this.city = city;
    }
	

}
