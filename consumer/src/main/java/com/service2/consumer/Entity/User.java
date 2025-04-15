package com.service2.consumer.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
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

  //getters
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

  //setters
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
