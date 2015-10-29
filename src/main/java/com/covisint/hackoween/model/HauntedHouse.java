package com.covisint.hackoween.model;

import java.util.HashMap;

public class HauntedHouse {
	
	private HashMap<String, Room> rooms = new HashMap<String, Room>();
	
	public HauntedHouse() {
		
	}
	
	public Room getRoombyId(String id) {
		return rooms.get(id);
	}
	
	
}
