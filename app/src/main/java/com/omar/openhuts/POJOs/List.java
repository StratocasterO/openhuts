package com.omar.openhuts.POJOs;

import java.util.ArrayList;

@Deprecated
// This class is a POJO (Plain Old Java Object) that represents a hut list
public class List {
	private int id;
	private int user;
	private String name;
	private ArrayList<Hut> huts;

	public List(int id, int user, String name, ArrayList<Hut> huts){
		this.id = id;
		this.user = user;
		this.name = name;
		this.huts = huts;
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

	public ArrayList<Hut> getHuts() {
		return huts;
	}

	public void setHuts(ArrayList<Hut> huts) {
		this.huts = huts;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}
}
