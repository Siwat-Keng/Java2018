package people;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.control.Button;
import logic.DataCollector;
import main.Main;

public abstract class User {

	protected String username;
	protected String password;
	protected String job;
	protected ArrayList<Button> task;
	protected ArrayList<String> room;

	public User(String username, String password, String job) {
		setAccount(username, password);
		setTask();
		this.job = job;
	}

	public void setTask() {
		this.task = new ArrayList<Button>();
		this.room = new ArrayList<String>();
		for (int i = 0; i < 24; ++i) {
			task.add((new Button()));
			task.get(i).setStyle("-fx-background-color:#00ff00;");
			room.add(Main.idle);
		}
	}

	public String getTask(int time) {
		return room.get(time);
	}

	public void addTask(int time, String name) {
		task.get(time).setStyle("-fx-background-color:#FF8C00;");
		this.room.set(time, name);
	}

	public boolean equals(User in) {
		if (this.username.equals(in.username))
			return true;
		return false;
	}

	public boolean equals(String username) {
		if (this.username.equals(username))
			return true;
		return false;
	}

	private void setAccount(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getJob() {
		return job;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean canLogin(String username, String password) {
		if (this.username.equals(username) && this.password.equals(password))
			return true;
		return false;
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

	public String toString;

	public String getPassword() {
		return password;
	}

	public void addAllTask(String string, LocalDate date) {
		String newString = string.substring(1, string.length() - 1);
		String[] myString = new String[24];
		myString = newString.split(",");
		for (int i = 0; i < 24; ++i) {
			if (!myString[i].substring(1, myString[i].length() - 1).equals(Main.idle)) {
				this.addTask(i, myString[i].substring(1, myString[i].length() - 1));
				if (!myString[i].substring(1, myString[i].length() - 1).equals(Main.takeLeave)) {
					DataCollector.addRoomTask(myString[i].substring(1, myString[i].length() - 1), date, date, i, i,
							this.username);
				}
			}
		}
	}
}
