package com.covisint.hackoween.model;

import java.util.List;

public class Room {
	
	private String refId;
	private String name;
	private String image;
	private boolean isStart;
	private boolean isEnd;
	private String[] rooms;
	
	public Room(String refId, String name, String image, boolean isStart, boolean isEnd, String[] rooms) {
		this.refId = refId;
		this.name = name;
		this.image = image;
		this.isStart = isStart;
		this.isEnd = isEnd;
		this.rooms = rooms;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public String[] getRooms() {
		return rooms;
	}

	public void setRooms(String[] rooms) {
		this.rooms = rooms;
	}
}
