package com.covisint.hackoween;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.http.client.ClientProtocolException;

import com.covisint.hackoween.controller.BaseController;
import com.covisint.hackoween.model.Person;
import com.covisint.hackoween.model.Room;


public class Engine { 
	
	public enum RoomDirection{
		NORTH(0), SOUTH(1), EAST(2), WEST(3);
		private int value;

		private RoomDirection(int value) {
		this.value = value;
		}
		};
	
	public static void main(String[] args) {
		Engine engine = new Engine();
		engine.initialize();
		
		Person p1 = new Person ("1", "F", "L");
		Person p3 = new Person ("2", "F", "L");
		Person p4 = new Person ("3", "F", "L");
		Person p5 = new Person ("4", "F", "L");
		
		engine.movePersontoRoom(p1, null);
		engine.movePersontoRoom(p3, null);
		engine.movePersontoRoom(p4, null);
		engine.movePersontoRoom(p5, null);
		
		List<Person> persons = engine.inRoomWith(p1);
		Iterator<Person> iter = persons.iterator(); 
		while(iter.hasNext()) {
			Person p = iter.next();
			System.out.println("ID = " + p.getId());
		}
	}
	
	private HashMap<String, Room> rooms = new HashMap<String, Room>();
	
	private HashMap<String, Person> persons = new HashMap<String, Person>();
	
	//Room with people
	private HashMap<String, ArrayList<Person>> roomMap = new HashMap<String, ArrayList<Person>>();
	
	public Engine() {
		initialize();
	}
	
	private void initialize() {
		//Create ten rooms manually
		Room[] myrooms = new Room[] {
				new Room("1", "Foyer", "/images/foyer.png", true, false, new String[]{"7","","2",""}),
				new Room("2", "Foyer", "/images/foyer.png", true, false, new String[]{"3","3","",""}),
				new Room("3", "Foyer", "/images/foyer.png", true, false, new String[]{"5","","","1"}),
				new Room("4", "Foyer", "/images/foyer.png", true, false, new String[]{"10","3","",""}),
				new Room("5", "Foyer", "/images/foyer.png", true, false, new String[]{"","2","3",""}),
				new Room("6", "Foyer", "/images/foyer.png", true, false, new String[]{"","5","4",""}),
				new Room("7", "Foyer", "/images/foyer.png", true, false, new String[]{"9","","5",""}),
				new Room("8", "Foyer", "/images/foyer.png", true, false, new String[]{"9","","9",""}),
				new Room("9", "Foyer", "/images/foyer.png", true, false, new String[]{"","6","8",""}),
				new Room("10", "Foyer", "/images/foyer.png", true, false, new String[]{"-1","","","9"})};
		for(int i=0; i<myrooms.length; i++) {
			rooms.put(myrooms[i].getRefId(), myrooms[i]);
			roomMap.put(myrooms[i].getRefId(), new ArrayList<Person>());
		}
	}
	
	public Person movePerson(String id, String refId) throws ClientProtocolException, IOException {
		Person person = persons.get(id);
		if(person==null) {
			String name = BaseController.getUser(id, BaseController.getToken());
			if(name == null) {
				name = "NA";
			}
			if(id == null) {
				System.out.println("id is null -- using uuid");
				id = UUID.randomUUID().toString();
			}
			person = new Person(id, name, "NA");
		}
		if(refId == null) {
			this.movePersontoRoom(person, null);
		} else {
			String[] personRooms = person.getCurrentRoom().getRooms();
			String roomId = personRooms[RoomDirection.valueOf(refId).value];
			Room room = rooms.get(roomId);
			this.movePersontoRoom(person, room);
		}
		return person;
	}
	
	//passing in null will put person in room 1
	public void movePersontoRoom(Person person, Room room) {
		Room currentRoom = person.getCurrentRoom();
		if(currentRoom != null) {
			roomMap.get(currentRoom.getRefId()).remove(person);
		}
		if(room == null) {
			roomMap.get("1").add(person);
			person.setCurrentRoom(rooms.get("1"));
		} else {
			roomMap.get(room.getRefId()).add(person);
			person.setCurrentRoom(room);
		}
		
	}

	public List<Person> inRoomWith(Person person) {
		ArrayList<Person> origPersonList = roomMap.get(person.getCurrentRoom().getRefId());
		ArrayList<Person> newPersonList = (ArrayList<Person>)origPersonList.clone();
		newPersonList.remove(person);
		return newPersonList;
	}
	
	public String[] getRoomArray(Person p) {
		return p.getCurrentRoom().getRooms();
	}
}
