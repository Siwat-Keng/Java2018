package people;

import java.util.ArrayList;

import javafx.scene.control.Button;

public class Room {

	private String roomName;
	private boolean canUse;
	private String Status;
	private ArrayList<Button> task;
	private String[] user;

	public Room(String name) {
		this.user = new String[24];
		for(int i =0;i<24;++i) {
			user[i] = "";
		}
		this.roomName = name;
		this.canUse = true;
		setStatus();
		setTask();
	}

	public Room(Room room) {
		this.user = new String[24];
		for(int i =0;i<24;++i) {
			user[i] = "";
		}
		this.roomName = room.getRoomName();
		this.canUse = true;
		setStatus();
		setTask();
	}

	public void setTask() {
		this.task = new ArrayList<Button>();
		for (int i = 0; i < 24; ++i) {
			task.add((new Button()));
			task.get(i).setStyle("-fx-background-color:#00ff00;");
		}
	}

	public String[] getUser() {
		return user;
	}

	public String getTask(int time) {
		return user[time];
	}

	public void addTask(int time, String name) {
		task.get(time).setStyle("-fx-background-color:#FF8C00;");
		user[time] = name;
		checkRoomAvialable();
	}

	public void addTask(String name) {
		for (int time = 0; time < 24; ++time) {
			user[time] = name;
			task.get(time).setStyle("-fx-background-color:#FF8C00;");
		}
		canUse = false;
		setStatus();
	}

	public void setStatus() {
		if (canUse)
			this.Status = "Avialable";
		else
			this.Status = "Not Avialable";
	}

	public String getRoomName() {
		return roomName;
	}

	public boolean checkRoomAvialable() {
		for (Button button : task) {
			if (button.getStyle().equals("-fx-background-color:#00ff00;"))
				return true;
		}
		return false;
	}

	public boolean checkRoomFree() {
		for (Button button : task) {
			if (button.getStyle().equals("-fx-background-color:#FF8C00;"))
				return false;
		}
		return true;
	}

	public String getStatus() {
		return this.Status;
	}

	public Button getLabel1() {
		return task.get(0);
	}

	public Button getLabel2() {
		return task.get(1);
	}

	public Button getLabel3() {
		return task.get(2);
	}

	public Button getLabel4() {
		return task.get(3);
	}

	public Button getLabel5() {
		return task.get(4);
	}

	public Button getLabel6() {
		return task.get(5);
	}

	public Button getLabel7() {
		return task.get(6);
	}

	public Button getLabel8() {
		return task.get(7);
	}

	public Button getLabel9() {
		return task.get(8);
	}

	public Button getLabel10() {
		return task.get(9);
	}

	public Button getLabel11() {
		return task.get(10);
	}

	public Button getLabel12() {
		return task.get(11);
	}

	public Button getLabel13() {
		return task.get(12);
	}

	public Button getLabel14() {
		return task.get(13);
	}

	public Button getLabel15() {
		return task.get(14);
	}

	public Button getLabel16() {
		return task.get(15);
	}

	public Button getLabel17() {
		return task.get(16);
	}

	public Button getLabel18() {
		return task.get(17);
	}

	public Button getLabel19() {
		return task.get(18);
	}

	public Button getLabel20() {
		return task.get(19);
	}

	public Button getLabel21() {
		return task.get(20);
	}

	public Button getLabel22() {
		return task.get(21);
	}

	public Button getLabel23() {
		return task.get(22);
	}

	public Button getLabel24() {
		return task.get(23);
	}

	public void removeTask(int i) {
		task.get(i).setStyle("-fx-background-color:#00ff00;");
		user[i] = "";
		checkRoomAvialable();
	}

	public void addAllTask(String string , String name) {
		String newString = string.substring(1, string.length()-1);
		String[] myString = new String[24];
		myString = newString.split(",");
		for(int i=0;i<24;++i) {
			if(myString[i].substring(1, myString[i].length()-1).equals("Not Avialable")) {
				this.addTask(i,name);
			}	
		}
	}
}
