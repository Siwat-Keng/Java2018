package logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Exception.MyInvalidException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import main.Main;
import people.*;
public class FileManagement {

	protected static ArrayList<User> fileUsers = new ArrayList<User>();
	protected static ArrayList<Room> fileRooms = new ArrayList<Room>();
	protected static ArrayList<Patient> filePatients = new ArrayList<Patient>();

	public static void write() {
		ArrayList<User> tokenUser = new ArrayList<User>();
		ArrayList<Room> tokenRoom = new ArrayList<Room>();
		ArrayList<Patient> tokenPatient = new ArrayList<Patient>();
		for (String key : DataCollector.userCollector.keySet()) {
			for (User user : DataCollector.userCollector.get(key)) {
				boolean check = true;
				for (User myuser : fileUsers) {
					if (myuser.getUsername().equals(user.getUsername())) {
						check = false;
						break;
					}
				}
				if (check)
					tokenUser.add(user);
			}
		}
		for (String key : DataCollector.roomCollector.keySet()) {
			for (Room room : DataCollector.roomCollector.get(key)) {
				boolean check = true;
				for (Room myroom : fileRooms) {
					if (myroom.getRoomName().equals(room.getRoomName())) {
						check = false;
						break;
					}
				}
				if (check)
					tokenRoom.add(room);
			}
		}
		for (String key : DataCollector.patientCollector.keySet()) {
			for (Patient patient : DataCollector.patientCollector.get(key)) {
				boolean check = true;
				for (Patient mypatient : filePatients) {
					if (mypatient.getName().equals(patient.getName())) {
						check = false;
						break;
					}
				}
				if (check)
					tokenPatient.add(patient);
			}
		}
		for (User user : tokenUser) {
			if (user instanceof Employee) {
				fileUsers.add(new Employee(user));
			} else if (user instanceof Manager) {
				fileUsers.add(new Manager(user));
			}

		}
		for (Room room : tokenRoom) {
			fileRooms.add(new Room(room.getRoomName()));
		}
		for (Patient patient : tokenPatient) {
			try {
				filePatients.add(new Patient(patient));
			} catch (MyInvalidException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Cannot Load Patient");
				alert.setContentText("Invalid Date");
				alert.showAndWait();
				return;
			}
		}

	}

	public static void remove(Patient patient) {
		for (Patient p : filePatients) {
			if (p.getName().trim().toLowerCase().equals(patient.getName().trim().toLowerCase())) {
				filePatients.remove(p);
				break;
			}
		}
	}

	public static void remove(Room room) {
		for (Room r : fileRooms) {
			if (r.getRoomName().trim().toLowerCase().equals(room.getRoomName().trim().toLowerCase())) {
				fileRooms.remove(r);
				break;
			}
		}
	}

	public static void remove(User user) {
		for (User u : fileUsers) {
			if (u.getUsername().trim().toLowerCase().equals(user.getUsername().trim().toLowerCase())) {
				fileUsers.remove(u);
				break;
			}
		}
	}

	public static ArrayList<User> getUser() {
		ArrayList<User> users = new ArrayList<User>();
		for (User user : fileUsers) {
			if (user instanceof Employee) {
				users.add(new Employee(user));
			}
			if (user instanceof Manager) {
				users.add(new Manager(user));
			}
		}
		return users;
	}

	public static ArrayList<Room> getRoom() {
		ArrayList<Room> rooms = new ArrayList<Room>();
		for (Room room : fileRooms) {
			rooms.add(new Room(room));
		}
		return rooms;
	}

	public static ArrayList<Patient> getPatient() {
		ArrayList<Patient> patients = new ArrayList<Patient>();
		for (Patient patient : filePatients) {
			try {
				patients.add(new Patient(patient));
			} catch (MyInvalidException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Copy Constuctor Error");
				alert.setContentText("Unknown Cause");
				alert.showAndWait();
			}
		}
		return patients;
	}

	// ------------------------------------{ Write File
	// }-----------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	public static void writeJsonUser(User user) throws FileNotFoundException, IOException, ParseException {
		JSONObject userObject = new JSONObject();
		userObject.put("name", user.getUsername());
		userObject.put("password", user.getPassword());
		userObject.put("position", user.getJob());

		JSONArray taskDate = new JSONArray();
		JSONArray date = new JSONArray();
		for (String key : DataCollector.userCollector.keySet()) {
			if (DataCollector.getUser(user.getUsername(), key) != null) {
				JSONArray token = new JSONArray();
				taskDate.add(key);
				for (int i = 0; i < 24; ++i) {
					token.add(i, DataCollector.getUser(user.getUsername(), key).getTask(i));
				}
				date.add(key);
				userObject.put("task" + key, token);
			}
		}
		String userClass = "";
		if (user instanceof Employee)
			userClass = "Employee";
		if (user instanceof Manager)
			userClass = "Manager";
		userObject.put("Date", date);
		userObject.put("Class", userClass);
		Files.write(Paths.get(user.getUsername() + ".json"), userObject.toString().getBytes());
//		@SuppressWarnings("resource")
//		FileWriter file = new FileWriter("res/"+ user.getUsername() + ".json"); 
//		file.write(userObject.toString());
	}

	@SuppressWarnings("unchecked")
	public static void writeJsonRoom(Room room) throws FileNotFoundException, IOException, ParseException {
		JSONObject roomObject = new JSONObject();
		roomObject.put("name", room.getRoomName());

		JSONArray taskDate = new JSONArray();
		for (String key : DataCollector.roomCollector.keySet()) {
			if (DataCollector.getRoom(room.getRoomName(), key) != null) {
				JSONArray token = new JSONArray();
				taskDate.add(key);
				for (int i = 0; i < 24; ++i) {
					token.add(i, DataCollector.getRoom(room.getRoomName(), key).getTask(i));
				}
				roomObject.put("task" + key, token);
			}
		}
//		@SuppressWarnings("resource")
//		FileWriter file = new FileWriter("res/"+ room.getRoomName() + ".json"); 
//		file.write(roomObject.toString());
		Files.write(Paths.get(room.getRoomName() + ".json"), roomObject.toString().getBytes());
	}

	@SuppressWarnings("unchecked")
	public static void writeJsonPatient(Patient patient) throws FileNotFoundException, IOException, ParseException {
		JSONObject patientObject = new JSONObject();
		patientObject.put("name", patient.getName());
		patientObject.put("room", patient.getRoom());
		patientObject.put("from", patient.getFrom());
		patientObject.put("to", patient.getTo());
		Files.write(Paths.get(patient.getName() + ".json"), patientObject.toString().getBytes());
//		@SuppressWarnings("resource")
//		FileWriter file = new FileWriter("res/"+ patient.getName() + ".json"); 
//		file.write(patientObject.toString());		
	}

	// ------------------------------------{ Write File
	// }-----------------------------------------------------------------------

	// ------------------------------------{ Read File
	// }------------------------------------------------------------------------

	public static User readJsonUser(String name) throws FileNotFoundException, IOException, ParseException {
		if (name.equals(""))
			return null;
		User user = null;
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(name.substring(1, name.length() - 1) + ".json"));
		JSONObject jsonObject = (JSONObject) obj;
		if ((boolean) jsonObject.get("Class").equals("Manager")) {
			user = new Manager((String) jsonObject.get("name"), (String) jsonObject.get("password"),
					(String) jsonObject.get("position"));
		} else {
			user = new Employee((String) jsonObject.get("name"), (String) jsonObject.get("password"),
					(String) jsonObject.get("position"));
		}
		return user;
	}

	public static void readJsonUserTask(String name) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(name + ".json"));
		JSONObject jsonObject = (JSONObject) obj;
		String task = jsonObject.get("Date").toString();
		if (task == null || task.equals(""))
			return;
		String[] taskDate = task.substring(1, task.length() - 1).split(",");
		for (String d : taskDate) {
			String current = jsonObject.get("task" + cutJson(d)).toString();
			String[] currentTask = current.substring(1, current.length() - 1).split(",");
			if (DataCollector.userCollector.containsKey(cutJson(d))) {
				boolean check = true;
				for (User user : DataCollector.userCollector.get(cutJson(d))) {
					if (user.getUsername().equals(name)) {
						check = false;
						break;
					}
				}
				if (check)
					DataCollector.userCollector.get(cutJson(d)).add(readJsonUser("a" + name + "a"));
				for (User user : DataCollector.userCollector.get(cutJson(d))) {
					if (user.getUsername().equals(name)) {
						for (int i = 0; i < 24; ++i) {
							if (!cutJson(currentTask[i]).equals(Main.idle)) {
								user.addTask(i, cutJson(currentTask[i]));
								DataCollector.addRoomTask(currentTask[i], LocalDate.parse(cutJson(d)),
										LocalDate.parse(cutJson(d)), i, i+1, name);
							}
						}
						break;
					}
				}
			} else {
				DataCollector.userCollector.put(cutJson(d), new ArrayList<User>());
				for (User user : getUserData()) {
					if (user.getUsername().equals(name)) {
						DataCollector.userCollector.get(cutJson(d)).add(user);
						for (int i = 0; i < 24; ++i) {
							if (!cutJson(currentTask[i]).equals(Main.idle)) {
								DataCollector.userCollector.get(cutJson(d)).get(0).addTask(i, cutJson(currentTask[i]));
								DataCollector.addRoomTask(currentTask[i], LocalDate.parse(cutJson(d)),
										LocalDate.parse(cutJson(d)), i, i+1, name);
							}
						}
					}
				}
			}
		}
	}

	public static Room readJsonRoom(String name) {
		Room room = new Room(name);
		return room;
	}

	public static Patient readJsonPatient(String name)
			throws FileNotFoundException, IOException, ParseException, MyInvalidException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(name + ".json"));
		JSONObject jsonObject = (JSONObject) obj;
		String from = jsonObject.get("from").toString();
		String to = jsonObject.get("to").toString();
		String room = jsonObject.get("room").toString();
		LocalDate fromDate = LocalDate.parse(from);
		LocalDate toDate = LocalDate.parse(to);
		Patient patient = new Patient(name, fromDate, toDate, room);
		DataCollector.setRoomTask(room, fromDate, toDate, name);
		return patient;
	}

	// ------------------------------------{ Read File
	// }------------------------------------------------------------------------

	// ------------------------------------{ Load
	// }-----------------------------------------------------------------------------

	public static void readAllData() {
		JSONParser parser = new JSONParser();
		Object data;
		try {
			data = parser.parse(new FileReader("data.json"));
			JSONObject dataObject = (JSONObject) data;
			setRoom(dataObject.get("room").toString());
			setUser(dataObject.get("user").toString());
			setPatient(dataObject.get("patient").toString());
		} catch (FileNotFoundException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Welcome");
			alert.setContentText("First Open(No data collected) : " + e.getMessage());
			alert.showAndWait();
			return;
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Unknown Error");
			alert.setContentText("IOException" + e.getMessage());
			alert.showAndWait();
			return;
		} catch (ParseException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Parse Error");
			alert.setContentText("Please Check file data : " + e.getMessage());
			alert.showAndWait();
			return;
		} catch (Exception e) {
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Unknown Error");
//			alert.setContentText("Exception :" + e.getLocalizedMessage());
//			alert.showAndWait();
			e.printStackTrace();
		}

	}

	private static void setPatient(String s)
			throws FileNotFoundException, IOException, ParseException, MyInvalidException {
		if (s.length() < 3)
			return;
		String[] data = s.substring(1, s.length() - 1).split(",");
		for (String name : data) {
			filePatients.add(readJsonPatient(name.substring(1, name.length() - 1)));
		}
	}

	private static void setUser(String s) throws FileNotFoundException, IOException, ParseException {
		if (s.length() < 3)
			return;
		String[] data = s.substring(1, s.length() - 1).split(",");
		for (String name : data) {
			User user = readJsonUser(name);
			for (User u : fileUsers) {
				if (u.getUsername().equals(name.subSequence(1, name.length() - 1))) {
					user = null;
				}
			}
			if (user != null) {
				fileUsers.add(user);
			}
		}
	}

	private static void setRoom(String s) throws FileNotFoundException, IOException, ParseException {
		if (s.length() < 3)
			return;
		String[] data = s.substring(1, s.length() - 1).split(",");
		for (String name : data) {
			fileRooms.add(readJsonRoom(name.substring(1, name.length() - 1)));
		}
	}

	// ------------------------------------{ Load
	// }-----------------------------------------------------------------------------

	// ------------------------------------{ Collect
	// }--------------------------------------------------------------------------

	public static void writeAllData() {
		try {
			for (User user : fileUsers) {
				writeJsonUser(user);
			}
			for (Room room : fileRooms) {
				writeJsonRoom(room);
			}
			for (Patient patient : filePatients) {
				writeJsonPatient(patient);
			}
		} catch (FileNotFoundException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Welcome");
			alert.setContentText("Created data file.");
			alert.showAndWait();
			return;
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Unknown Error");
			alert.setContentText("IOException" + e.getMessage());
			alert.showAndWait();
			return;
		} catch (ParseException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Parse Error");
			alert.setContentText("Please Check the data : " + e.getMessage());
			alert.showAndWait();
			return;
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Unknown Error");
			alert.setContentText("Exception : " + e.getMessage());
			alert.showAndWait();
		}
	}

	@SuppressWarnings("unchecked")
	private static void writeJson() {
		JSONObject jsonObject = new JSONObject();
		JSONArray user = new JSONArray();
		JSONArray patient = new JSONArray();
		JSONArray room = new JSONArray();
		for (User u : fileUsers) {
			user.add(u.getUsername());
		}
		for (Room r : fileRooms) {
			room.add(r.getRoomName());
		}
		for (Patient p : filePatients) {
			patient.add(p.getName());
		}
		jsonObject.put("user", user);
		jsonObject.put("room", room);
		jsonObject.put("patient", patient);
		try {
			Files.write(Paths.get("data.json"), jsonObject.toString().getBytes());
//			@SuppressWarnings("resource")
//			FileWriter file = new FileWriter("res/data.json"); 
//			file.write(jsonObject.toString());
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Unknown Error");
			alert.setContentText("IOException" + e.getMessage());
			alert.showAndWait();
			return;
		}
	}

	// ------------------------------------{ Collect
	// }--------------------------------------------------------------------------

	public static void Update() {
		for (User user : Main.users) {
			User us = user;
			for (User u : fileUsers) {
				if (u.getUsername().equals(us.getUsername())) {
					us = null;
					break;
				}
			}
			if (us != null) {
				if (us instanceof Manager)
					fileUsers.add(new Manager(us));
				if (us instanceof Employee)
					fileUsers.add(new Employee(us));
			}
		}
		for (Room room : Main.rooms) {
			Room rm = room;
			for (Room r : fileRooms) {
				if (r.getRoomName().equals(rm.getRoomName())) {
					rm = null;
					break;
				}
			}
			if (rm != null)
				fileRooms.add(new Room(rm));
		}

		for (Patient patient : Main.patients) {
			Patient pa = patient;
			for (Patient p : filePatients) {
				if (p.getName().equals(pa.getName())) {
					pa = null;
					break;
				}
			}
			if (pa != null)
				try {
					filePatients.add(new Patient(pa));
				} catch (MyInvalidException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Load Patient Error");
					alert.setContentText(e.getMessage());
					alert.showAndWait();
					return;
				}
		}
	}

	public static ArrayList<User> getUserData() {
		ArrayList<User> users = new ArrayList<User>();
		for (User user : fileUsers) {
			if (user instanceof Manager)
				users.add(new Manager(user));
			if (user instanceof Employee)
				users.add(new Employee(user));
		}
		return users;
	}

	public static ArrayList<Room> getRoomData() {
		ArrayList<Room> rooms = new ArrayList<Room>();
		for (Room room : fileRooms) {
			rooms.add(new Room(room));
		}
		return rooms;
	}

//	-------------------Start Program-------------------------------
	public static void writeAllJson() {
		writeJson();
		writeAllData();
	}

	public static void loadAllData() {
		readAllData();
		for (User user : fileUsers) {
			try {
				readJsonUserTask(user.getUsername());
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}

	private static String cutJson(String s) {
		return s.substring(1, s.length() - 1);
	}
}
