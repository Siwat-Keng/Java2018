package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import people.Employee;
import people.Manager;
import people.User;
import people.Viewer;

public class LoginController {

	
	@FXML
	private Label loginLabel;
	
	@FXML
	private Label usernameLabel;
	
	@FXML
	private Label passwordLabel;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField passwordField;
	
	@FXML
	private Button loginBtn;
	
	@FXML
	private Button registerBtn;
	
	@FXML
	private Button viewBtn;
	
	@FXML
	private CheckBox isManager;
	
	@FXML ComboBox<String> jobBox;
	
	@FXML
	private void initialize() {
		ObservableList<String> positionList = 
				FXCollections.observableArrayList("Doctor","Nurse");
		jobBox.setItems(positionList);
	}
	
	@FXML
	private void handleLoginBtn() {
		String username = usernameField.getText().trim();
		String password = passwordField.getText().trim();
		for(int i = 0; i < Main.users.size() ; ++i ){
			if(Main.users.get(i).canLogin(username, password)) {
				Main.currentuser = Main.users.get(i);
				Main.showMainView();
				return;
			}
		}
		Alert alert = new Alert(AlertType.WARNING);
		alert.setContentText("Invalid username or password");
		alert.setHeaderText("Error");
		alert.showAndWait();
		return;
	}
	
	@FXML
	private void handleRegisterBtn() {
		if(jobBox.getValue() == null || jobBox.getValue().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Job can not be empty.");
			alert.setHeaderText("Error");
			alert.showAndWait();
			return;
		}
		if( this.isManager.isSelected() ) {
			String username = usernameField.getText().trim();
			String password = passwordField.getText().trim();
			if(username.equals("") || password.equals("") ) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Username and Password can not empty.");
				alert.setHeaderText("Error");
				alert.showAndWait();
				return;
			}
			if (username.length() < 6 || username.length() > 12 || password.length() < 6 || password.length() > 12) {
				Alert alert = new Alert(AlertType.ERROR, "Username and Password lenght must be 6-12 characters.");
				alert.setTitle("Register Error");
				alert.showAndWait();
				return;
			}
			for( User user : Main.users ) {
				if(user.equals(username)) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("This username is not available.");
					alert.setHeaderText("Error");
					alert.showAndWait();
					return;
				}
			}
			User user = new Manager(username, password, jobBox.getValue().trim());
			Main.users.add(user);		
			handleLoginBtn();
		}
		else {
			String username = usernameField.getText().trim();
			String password = passwordField.getText().trim();
			if(username.equals("") || password.equals("") ) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Username and Password can not empty.");
				alert.setHeaderText("Error");
				alert.showAndWait();
				return;
			}
			if (username.length() < 6 || username.length() > 12 || password.length() < 6 || password.length() > 12) {
				Alert alert = new Alert(AlertType.ERROR, "Username and Password lenght must be 6-12 characters.");
				alert.setTitle("Register Error");
				alert.showAndWait();
				return;
			}
			for( User user : Main.users ) {
				if(user.equals(username)) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("This username is not available.");
					alert.setHeaderText("Error");
					alert.showAndWait();
					return;
				}
			}
			User user = new Employee(username, password, jobBox.getValue().trim());
			Main.users.add(user);
			handleLoginBtn();
		}
	}
	
	@FXML
	private void handleViewBtn() {
		Main.currentuser = new Viewer("View");
		Main.showMainView();
	}
	
}
