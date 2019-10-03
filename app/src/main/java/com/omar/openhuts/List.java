package com.omar.openhuts;

import java.util.ArrayList;

// This class is a POJO (Plain Old Java Object) that represents a list
public class List {
	private String name;
	private ArrayList<Hut> listHuts;
	private int num;
	private int id;

	public List(String name, ArrayList<Hut> listHuts, int id){
		this.name = name;
		this.listHuts = listHuts;
		this.num = listHuts.size();
		this.id = id;
	}

	// Getters and setters:
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setListHuts(ArrayList<Hut> listHuts) {
		this.listHuts = listHuts;
	}

	public ArrayList<Hut> getListHuts() {
		return listHuts;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getId() {return id;	}

	public void setId(int id) {	this.id = id;	}
}