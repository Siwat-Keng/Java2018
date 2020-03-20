package logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Exception.MyInvalidException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import main.Main;
import people.Employee;
import people.Manager;
import people.Patient;
import people.Room;
import people.User;

public class DataCollector {

	protected static Map<String, ArrayList<Room>> roomCollector = new HashMap<String, ArrayList<Room>>();
	protected static Map<String, ArrayList<User>> userCollector = new HashMap<String, ArrayList<User>>();
	protected static Map<String, ArrayList<Patient>> patientCollector = new HashMap<String, ArrayList<Patient>>();

	public static boolean checkAvialable(String room, String fromDate, String toDate) {
		LocalDate counter = LocalDate.parse(fromDate);
		while (!counter.isAfter(LocalDate.parse(toDate))) {
			if (roomCollector.containsKey(counter.toString())) {
				for (Room r : roomCollector.get(counter.toString())) {
					if (r.getRoomName().trim().toLowerCase().equals(room.trim().toLowerCase())) {
						if (!r.checkRoomFree())
							return false;
						break;
					}
				}
			}
			counter = counter.plusDays(1);
		}
		return true;
	}

	public static boolean checkAvialable(String room, LocalDate first, LocalDate last, int start, int end) {
		LocalDate counter = first;
		if (first.equals(last)) {
			while (!counter.isAfter(last)) {
				if (roomCollector.containsKey(counter.toString())) {
					for (Room r : roomCollector.get(counter.toString())) {
						if (r.getRoomName().equals(room)) {
							for (int i = start - 1; i < end; ++i) {
								if (!r.getTask(i).equals(""))
									return false;
							}
						}
					}
				}
				counter = counter.plusDays(1);
			}
		} else {
			if (roomCollector.containsKey(counter.toString())) {
				for (Room r : roomCollector.get(counter.toString())) {
					if (r.getRoomName().equals(room)) {
						for (int i = start - 1; i < 24; ++i) {
							if (!r.getTask(i).equals(""))
								return false;
						}
					}
				}
			}
			counter = counter.plusDays(1);
			if (roomCollector.containsKey(counter.toString())) {
				for (Room r : roomCollector.get(counter.toString())) {
					if (r.getRoomName().equals(room)) {
						for (int i = 0; i < end; ++i) {
							if (!r.getTask(i).equals(""))
								return false;
						}
					}
				}
			}
		}
		return true;
	}

	public static void write() {
		if (userCollector.containsKey(Main.selectedDate.toString())) {
			for (User user : Main.users) {
				User add = null;
				for (User u : userCollector.get(Main.selectedDate.toString())) {
					add = user;
					if (u.getUsername().trim().toLowerCase().equals(user.getUsername().trim().toLowerCase())) {
						add = null;
						break;
					}
				}
				if (add != null)
					if (add instanceof Manager) {
						userCollector.get(Main.selectedDate.toString()).add(new Manager(add));
					} else {
						userCollector.get(Main.selectedDate.toString()).add(new Employee(add));
					}

			}
		} else {
			userCollector.put(Main.selectedDate.toString(), Main.users);
		}
		if (roomCollector.containsKey(Main.selectedDate.toString())) {
			for (Room room : Main.rooms) {
				Room add = null;
				for (Room r : roomCollector.get(Main.selectedDate.toString())) {
					add = r;
					if (r.getRoomName().equals(room.getRoomName())) {
						add = null;
						break;
					}
				}
				if (add != null)
					roomCollector.get(Main.selectedDate.toString()).add(new Room(add));
			}
		} else {
			roomCollector.put(Main.selectedDate.toString(), Main.rooms);
		}
		if (patientCollector.containsKey(Main.selectedDate.toString())) {
			for (Patient patient : Main.patients) {
				if (!patientCollector.get(Main.selectedDate.toString()).contains(patient))
					patientCollector.get(Main.selectedDate.toString()).add(patient);
			}
		} else {
			patientCollector.put(Main.selectedDate.toString(), Main.patients);
		}
		FileManagement.write();
	}

	public static void read() {
		if (userCollector.containsKey(Main.selectedDate.toString())) {
			for (User user : userCollector.get(Main.selectedDate.toString())) {
				User us = user;
				for (User u : Main.users) {
					us = u;
					if (u.getUsername().equals(user.getUsername())) {
						us = null;
						break;
					}
				}
				if (us != null) {
					if (us instanceof Manager) {
						Main.users.add(new Manager(us));

					} else {
						Main.users.add(new Employee(us));
					}
				}
			}
		} else {
			userCollector.put(Main.selectedDate.toString(), new ArrayList<User>());
			for (User user : FileManagement.fileUsers) {
				if (user instanceof Manager)
					userCollector.get(Main.selectedDate.toString()).add(new Manager(user));
				if (user instanceof Employee)
					userCollector.get(Main.selectedDate.toString()).add(new Employee(user));
			}
			for (User u : Main.users) {
				User us = null;
				for (User user : userCollector.get(Main.selectedDate.toString())) {
					us = u;
					if (u.getUsername().equals(user.getUsername())) {
						us = null;
						break;
					}
				}
				if (us != null) {
					if (us instanceof Manager) {
						Main.users.add(new Manager(us));
					} else {
						Main.users.add(new Employee(us));
					}
				}
			}
		}

		if (patientCollector.containsKey(Main.selectedDate.toString())) {
			for (Patient patient : patientCollector.get(Main.selectedDate.toString())) {
				Patient pa = null;
				for (Patient p : Main.patients) {
					pa = p;
					if (p.getName().equals(patient.getName())) {
						pa = null;
						break;
					}
					if (pa != null) {
						try {
							Main.patients.add(new Patient(pa));
						} catch (MyInvalidException e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Update Patient");
							alert.setContentText("Cannot update Patient : " + e.getMessage());
							alert.showAndWait();
							return;
						}
					}
				}
			}
		}

		if (roomCollector.containsKey(Main.selectedDate.toString())) {
			for (Room room : roomCollector.get(Main.selectedDate.toString())) {
				Room rm = null;
				for (Room r : Main.rooms) {
					rm = r;
					if (r.getRoomName().equals(room.getRoomName())) {
						rm = null;
						break;
					}
				}
				if (rm != null) {
					Main.rooms.add(new Room(rm));
				}
			}
		}

	}

	public static void setData() {
		if (userCollector.containsKey(Main.selectedDate.toString())) {
			for (User user : FileManagement.getUserData()) {
				User add = user;
				for (User u : userCollector.get(Main.selectedDate.toString())) {
					if (user.getUsername().equals(u.getUsername())) {
						add = null;
						break;
					}
				}
				if (add != null) {
					if (add instanceof Manager)
						userCollector.get(Main.selectedDate.toString()).add(new Manager(add));
					if (add instanceof Employee)
						userCollector.get(Main.selectedDate.toString()).add(new Employee(add));
				}
			}
			Main.users = userCollector.get(Main.selectedDate.toString());
		} else {
			userCollector.put(Main.selectedDate.toString(), FileManagement.getUserData());
		}
		if (roomCollector.containsKey(Main.selectedDate.toString())) {
			for (Room room : FileManagement.getRoomData()) {
				Room add = room;
				for (Room r : roomCollector.get(Main.selectedDate.toString())) {
					if (room.getRoomName().equals(r.getRoomName())) {
						add = null;
						break;
					}
				}
				if (add != null) {
					roomCollector.get(Main.selectedDate.toString()).add(add);
				}
			}
			Main.rooms = roomCollector.get(Main.selectedDate.toString());
		} else {
			roomCollector.put(Main.selectedDate.toString(), FileManagement.getRoomData());
		}
		if(patientCollector.containsKey(Main.selectedDate.toString())) {
			for (Patient patient : FileManagement.getPatient()) {
				Patient add = patient;
				for (Patient r : patientCollector.get(Main.selectedDate.toString())) {
					if (patient.getName().equals(r.getName())) {
						add = null;
						break;
					}
				}
				if (add != null) {
					patientCollector.get(Main.selectedDate.toString()).add(add);
				}
			}
			Main.patients = patientCollector.get(Main.selectedDate.toString());
		}
		else {
			patientCollector.put(Main.selectedDate.toString(), FileManagement.getPatient());
		}
	}

	public static void setRoomTask(String room, LocalDate from, LocalDate to, String name) {
		LocalDate counter = from;
		while (!counter.isAfter(to)) {
			addRoomTask(room, counter, counter, 1, 24, name);
			counter = counter.plusDays(1);
		}
		Main.rooms = roomCollector.get(Main.selectedDate.toString());
	}

	public static void addRoomTask(String room, LocalDate first, LocalDate last, int start, int end, String name) {
		LocalDate counter = first;
		if (!first.equals(last)) {
			if (!roomCollector.containsKey(counter.toString())) {
				roomCollector.put(counter.toString(), FileManagement.getRoom());
				for (Room r : roomCollector.get(counter.toString())) {
					if (r.getRoomName().equals(room)) {
						for (int i = start - 1; i < 24; ++i) {
							r.addTask(i, name);
						}
						break;
					}
				}
			} else {
				for (Room r : roomCollector.get(counter.toString())) {
					if (r.getRoomName().equals(room)) {
						for (int i = start - 1; i < 24; ++i) {
							r.addTask(i, name);
						}
						break;
					}
				}
			}
			counter = counter.plusDays(1);
			if (!roomCollector.containsKey(counter.toString())) {
				roomCollector.put(counter.toString(), FileManagement.getRoom());
				for (Room r : roomCollector.get(counter.toString())) {
					if (r.getRoomName().equals(room)) {
						for (int i = 0; i < end; ++i) {
							r.addTask(i, name);
						}
						break;
					}
				}
			} else {
				for (Room r : roomCollector.get(counter.toString())) {
					if (r.getRoomName().equals(room)) {
						for (int i = 0; i < end; ++i) {
							r.addTask(i, name);
						}
						break;
					}
				}
			}
		} else {
			while (!counter.isAfter(last)) {
				if (!roomCollector.containsKey(counter.toString())) {
					roomCollector.put(counter.toString(), FileManagement.getRoom());
					for (Room r : roomCollector.get(counter.toString())) {
						if (r.getRoomName().equals(room)) {
							for (int i = start - 1; i < end; ++i) {
								r.addTask(i, name);
							}
							break;
						}
					}
				} else {
					for (Room r : roomCollector.get(counter.toString())) {
						if (r.getRoomName().equals(room)) {
							for (int i = start - 1; i < end; ++i) {
								r.addTask(i, name);
							}
							break;
						}
					}
				}
				counter = counter.plusDays(1);
			}
		}
		Main.rooms = roomCollector.get(Main.selectedDate.toString());
	}

	public static void remove(Patient patient) {
		FileManagement.remove(patient);
		for (ArrayList<Patient> ps : patientCollector.values()) {
			for (Patient p : ps) {
				if (p.getName().trim().toLowerCase().equals(patient.getName().trim().toLowerCase())) {
					ps.remove(p);
					break;
				}
			}
		}
		for (ArrayList<Room> rs : roomCollector.values()) {
			for (Room r : rs) {
				for (int i = 0; i < 24; ++i) {
					if (r.getUser()[i].trim().toLowerCase().equals(patient.getName().trim().toLowerCase())) {
						r.getUser()[i] = "";
						r.removeTask(i);
					}
				}
			}
		}
	}

	public static void remove(Room room) throws MyInvalidException {
		if (!room.checkRoomFree()) {
			throw new MyInvalidException("This Room is using.");
		} else {
			FileManagement.remove(room);
			for (ArrayList<Room> rs : roomCollector.values()) {
				for (Room r : rs) {
					if (r.getRoomName().trim().toLowerCase().equals(room.getRoomName().trim().toLowerCase())) {
						rs.remove(r);
						break;
					}
				}
			}
		}
	}

	public static void remove(User user) throws MyInvalidException {
		if (Main.currentuser.equals(user)) {
			throw new MyInvalidException("Cannot Remove this user.");
		} else {
			FileManagement.remove(user);
			for (ArrayList<User> us : userCollector.values()) {
				for (User u : us) {
					if (u.getUsername().trim().toLowerCase().equals(user.getUsername().trim().toLowerCase())) {
						us.remove(u);
						break;
					}
				}
			}
			for (ArrayList<Room> rs : roomCollector.values()) {
				for (Room r : rs) {
					for (int i = 0; i < 24; ++i) {
						if (r.getUser()[i].trim().toLowerCase().equals(user.getUsername().trim().toLowerCase())) {
							r.getUser()[i] = "";
							r.removeTask(i);
						}
					}
				}
			}
		}
	}

	public static boolean canTakeleave(String name, LocalDate first, LocalDate last, int start, int end) {
		LocalDate counter = first;
		if (first.equals(last)) {
			while (!counter.isAfter(last)) {
				if (userCollector.containsKey(counter.toString())) {
					for (User u : userCollector.get(counter.toString())) {
						if (u.getUsername().equals(name)) {
							for (int i = start - 1; i < end; ++i) {
								if (!u.getTask(i).equals(Main.idle))
									return false;
							}
						}
					}
				}
				counter = counter.plusDays(1);
			}
		} else {
			if (userCollector.containsKey(counter.toString())) {
				for (User u : userCollector.get(counter.toString())) {
					if (u.getUsername().equals(name)) {
						for (int i = start - 1; i < 24; ++i) {
							if (!u.getTask(i).equals(Main.idle))
								return false;
						}
					}
				}
			}
			counter = counter.plusDays(1);
			if (userCollector.containsKey(counter.toString())) {
				for (User u : userCollector.get(counter.toString())) {
					if (u.getUsername().equals(name)) {
						for (int i = 0; i < end; ++i) {
							if (!u.getTask(i).equals(Main.idle))
								return false;
						}
					}
				}
			}
		}
		return true;
	}

	public static void addTakeleave(String name, LocalDate first, LocalDate last, int start, int end) {
		LocalDate counter = first;
		if (!first.equals(last)) {
			if (!userCollector.containsKey(counter.toString())) {
				userCollector.put(counter.toString(), FileManagement.getUserData());
				for (User u : userCollector.get(counter.toString())) {
					if (u.getUsername().equals(name)) {
						for (int i = start - 1; i < 24; ++i) {
							u.addTask(i, Main.takeLeave);
						}
						break;
					}
				}
			} else {
				for (User u : userCollector.get(counter.toString())) {
					if (u.getUsername().equals(name)) {
						for (int i = start - 1; i < 24; ++i) {
							u.addTask(i, Main.takeLeave);
						}
						break;
					}
				}
			}
			counter = counter.plusDays(1);
			if (!userCollector.containsKey(counter.toString())) {
				userCollector.put(counter.toString(), FileManagement.getUser());
				for (User u : userCollector.get(counter.toString())) {
					if (u.getUsername().equals(name)) {
						for (int i = 0; i < end; ++i) {
							u.addTask(i, Main.takeLeave);
						}
						break;
					}
				}
			} else {
				for (User u : userCollector.get(counter.toString())) {
					if (u.getUsername().equals(name)) {
						for (int i = 0; i < end; ++i) {
							u.addTask(i, Main.takeLeave);
						}
						break;
					}
				}
			}
		} else {
			while (!counter.isAfter(last)) {
				if (!userCollector.containsKey(counter.toString())) {
					userCollector.put(counter.toString(), FileManagement.getUser());
					for (User u : userCollector.get(counter.toString())) {
						if (u.getUsername().equals(name)) {
							for (int i = start - 1; i < end; ++i) {
								u.addTask(i, Main.takeLeave);
							}
							break;
						}
					}
				} else {
					for (User u : userCollector.get(counter.toString())) {
						if (u.getUsername().equals(name)) {
							for (int i = start - 1; i < end; ++i) {
								u.addTask(i, Main.takeLeave);
							}
							break;
						}
					}
				}
				counter = counter.plusDays(1);
			}
		}
		Main.users = userCollector.get(Main.selectedDate.toString());
	}

	public static void addShift(String name, LocalDate first, LocalDate last, int start, int end, String room) {
		LocalDate counter = first;
		if (!first.equals(last)) {
			if (!userCollector.containsKey(counter.toString())) {
				userCollector.put(counter.toString(), FileManagement.getUserData());
				for (User u : userCollector.get(counter.toString())) {
					if (u.getUsername().equals(name)) {
						for (int i = start - 1; i < 24; ++i) {
							u.addTask(i, room);
						}
						break;
					}
				}
			} else {
				for (User u : userCollector.get(counter.toString())) {
					if (u.getUsername().equals(name)) {
						for (int i = start - 1; i < 24; ++i) {
							u.addTask(i, room);
						}
						break;
					}
				}
			}
			counter = counter.plusDays(1);
			if (!userCollector.containsKey(counter.toString())) {
				userCollector.put(counter.toString(), FileManagement.getUser());
				for (User u : userCollector.get(counter.toString())) {
					if (u.getUsername().equals(name)) {
						for (int i = 0; i < end; ++i) {
							u.addTask(i, room);
						}
						break;
					}
				}
			} else {
				for (User u : userCollector.get(counter.toString())) {
					if (u.getUsername().equals(name)) {
						for (int i = 0; i < end; ++i) {
							u.addTask(i, room);
						}
						break;
					}
				}
			}
		} else {
			while (!counter.isAfter(last)) {
				if (!userCollector.containsKey(counter.toString())) {
					userCollector.put(counter.toString(), FileManagement.getUser());
					for (User u : userCollector.get(counter.toString())) {
						if (u.getUsername().equals(name)) {
							for (int i = start - 1; i < end; ++i) {
								u.addTask(i, room);
							}
							break;
						}
					}
				} else {
					for (User u : userCollector.get(counter.toString())) {
						if (u.getUsername().equals(name)) {
							for (int i = start - 1; i < end; ++i) {
								u.addTask(i, room);
							}
							break;
						}
					}
				}
				counter = counter.plusDays(1);
			}
		}
		Main.users = userCollector.get(Main.selectedDate.toString());
	}

	public static User getUser(String name, String date) {
		if (!userCollector.containsKey(date))
			return null;
		for (User user : userCollector.get(date)) {
			if (user.getUsername().equals(name))
				return user;
		}
		return null;
	}

	public static Room getRoom(String name, String date) {
		for (Room room : roomCollector.get(date)) {
			if (room.getRoomName().equals(name))
				return room;
		}
		return null;
	}

}
