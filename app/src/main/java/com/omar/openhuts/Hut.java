package com.omar.openhuts;

import com.google.android.gms.maps.model.LatLng;

// This class is a POJO (Plain Old Java Object) that represents a hut
public class Hut {
	private int id;
	private String name;
	//private Float rating;
	private LatLng location;
	//private String img;

	// public Hut(int id, String name, String description, Float rating, LatLng location, String img){
	public Hut(int id, String name, LatLng location){
		this.id = id;
		this.name = name;
		//this.rating = rating;
		this.location = location;
		//this.img = img;
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

//	public Float getRating() {
//		return rating;
//	}
//
//	public void setRating(Float rating) {
//		this.rating = rating;
//	}

	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}

//	public String getImg() {
//		return img;
//	}
//
//	public void setImg(String img) {
//		this.img = img;
//	}
}
