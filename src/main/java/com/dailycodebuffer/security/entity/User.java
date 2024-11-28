package com.dailycodebuffer.security.entity;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
	
	
	@Id
    private Integer id;
    private String userName;
    private String password;
    
    public User(String string, String string2, ArrayList arrayList) {
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
