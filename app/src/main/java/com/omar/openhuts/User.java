package com.omar.openhuts;

// This class is a POJO (Plain Old Java Object) that represents a user
public class User {
	private int id;
	private String name;
	private String email;
	private String pass;
	private String description;
	private String location;
	private String img;

	public User(int id, String name, String email, String pass, String description, String location, String img){
		this.description = description;
		this.email = email;
		this.id = id;
		this.img = img;
		this.location = location;
		this.name = name;
		this.pass = pass;
	}

	// Getters and setters:
	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
