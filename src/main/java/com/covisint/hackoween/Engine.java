package com.covisint.hackoween;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.LoggerFactory;

import com.covisint.hackoween.controller.BaseController;
import com.covisint.hackoween.model.Person;
import com.covisint.hackoween.model.Room;


public class Engine { 
	
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	public enum RoomDirection{
		NORTH(2), SOUTH(3), EAST(0), WEST(1);
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
			logger.debug("ID = " + p.getId());
		}
		
		engine.movePersontoRoom( p1, engine.rooms.get( p1.getCurrentRoom().getRooms()[0] ));
		logger.debug( p1.getCurrentRoom().getName() );
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
	
	public Person movePerson(String id, String refId) throws ClientProtocolException, IOException {
	    logger.debug("id and refid " +id + " " + refId);
		Person person = persons.get(id);
		if(person==null) {
			String name = BaseController.getUser(id, BaseController.getToken());
			if(name == null) {
				name = "NA";
			}
			if(id == null) {
				logger.debug("id is null -- using uuid");
				id = UUID.randomUUID().toString();
			}
			person = new Person(id, name, "NA");
			person.setCurrentRoom(rooms.get("1"));
			persons.put(id, person);
		}
		if(refId == null) {
			this.movePersontoRoom(person, null);
		} else {
			if(person.getCurrentRoom() == null ) {
				person.setCurrentRoom(rooms.get("1"));
			}
			String[] personRooms = person.getCurrentRoom().getRooms();
			String roomId = personRooms[RoomDirection.valueOf(refId).value];
			logger.debug("refId "+refId);
			logger.debug("roomId "+roomId);
			logger.debug("RoomDirection.valueOf(refId).value  "+RoomDirection.valueOf(refId).value);
			if(roomId == null || "".equals(roomId.trim())){
			    roomId = "1";
			}
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
			person.setPreviousRoom(currentRoom);
		}
		if(room == null) {
			roomMap.get("1").add(person);
			person.setCurrentRoom(rooms.get("1"));
		} else {
			roomMap.get(room.getRefId()).add(person);
			logger.debug("Room refId"+room.getRefId());
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
