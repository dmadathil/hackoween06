package com.covisint.hackoween;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.covisint.hackoween.model.Person;
import com.covisint.hackoween.model.Room;


public class Engine {
	
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
		int theRoom = Integer.parseInt(p1.getCurrentRoom().getRooms()[0] );
		engine.movePersontoRoom( p1, engine.rooms.get( p1.getCurrentRoom().getRooms()[0] ));
		System.out.println( p1.getCurrentRoom().getName() );
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
				new Room("1", "Room of the Living Dead", "/images/foyer.png", true, false, new String[]{"7","","2",""}),
				new Room("2", "Asylum", "/images/foyer.png", true, false, new String[]{"3","3","",""}),
				new Room("3", "Saw Room", "/images/foyer.png", true, false, new String[]{"5","","","1"}),
				new Room("4", "The Crypt", "/images/foyer.png", true, false, new String[]{"10","3","",""}),
				new Room("5", "Field of Screams", "/images/foyer.png", true, false, new String[]{"","2","3",""}),
				new Room("6", "Clown Tent", "/images/foyer.png", true, false, new String[]{"","5","4",""}),
				new Room("7", "Slaughter House", "/images/foyer.png", true, false, new String[]{"9","","5",""}),
				new Room("8", "Basement of Torture", "/images/foyer.png", true, false, new String[]{"9","","9",""}),
				new Room("9", "The Graveyard", "/images/foyer.png", true, false, new String[]{"","6","8",""}),
				new Room("10", "The Orphanage", "/images/foyer.png", true, false, new String[]{"-1","","","9"})};
		for(int i=0; i<myrooms.length; i++) {
			rooms.put(myrooms[i].getRefId(), myrooms[i]);
			roomMap.put(myrooms[i].getRefId(), new ArrayList<Person>());
		}
	}
	
	public Person movePerson(String id, String refId) {
		Person person = persons.get(id);
		if(person==null) {
			person = new Person(id,"NA", "NA");
		}
		Room room = rooms.get(refId);
		this.movePersontoRoom(person, room);
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
