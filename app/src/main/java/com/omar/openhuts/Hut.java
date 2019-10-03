package com.omar.openhuts;

import com.google.android.gms.maps.model.LatLng;

// This class is a POJO (Plain Old Java Object) that represents a hut
public class Hut {
	private int id;
	private String name;
	private String description;
	private Float rating;
	private LatLng location;
	private String img;
	private int temp;
	private int wind;
	private int rain;


	public Hut(int id, String name, String description, Float rating, LatLng location, int temp, int wind, int rain, String img){
		this.id = id;
		this.name = name;
		this.description = description;
		this.rating = rating;
		this.location = location;
		this.wind = wind;
		this.temp = temp;
		this.rain = rain;
		this.img = img;
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

	public Float getRating() {		return rating;	}

	public void setRating(Float rating) {		this.rating = rating;	}

	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}

	public String getDescription() {		return description; }

	public void setDescription(String description) {		this.description = description; }

	public int getTemp() {		return temp; }

	public void setTemp(int temp) {		this.temp = temp; }

	public int getWind() {		return wind; }

	public void setWind(int wind) {		this.wind = wind; }

	public int getRain() {		return rain; }

	public void setRain(int rain) {		this.rain = rain; }

	public String getImg() {		return img;	}

	public void setImg(String img) {		this.img = img;	}
}
