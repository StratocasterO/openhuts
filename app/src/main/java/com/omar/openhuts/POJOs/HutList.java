package com.omar.openhuts.POJOs;

import com.omar.openhuts.POJOs.Hut;

import java.util.ArrayList;

// This class is a POJO (Plain Old Java Object) that represents a list
public class HutList {
	private String name;
	private ArrayList<Integer> listHuts;
	private int num;
	private int id;

	public HutList(String name, ArrayList<Integer> listHuts, int id){
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

	public void setListHuts(ArrayList<Integer> listHuts) {
		this.listHuts = listHuts;
	}

	public ArrayList<Integer> getListHuts() {
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
