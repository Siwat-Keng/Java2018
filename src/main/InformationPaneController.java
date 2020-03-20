package main;

import Interface.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import people.Patient;
import people.Room;
import people.User;

public class InformationPaneController {

	private ObservableList<User> employeeList;
	private ObservableList<Room> showRoomList;
	private ObservableList<Patient> patientList;

	@FXML
	private TableView<User> employeeInformationTable;
	@FXML
	private TableView<Room> roomInformationTable;
	@FXML
	private TableView<Patient> patientInformationTable;

	@FXML
	private TableColumn<User, String> positionColumn;
	@FXML
	private TableColumn<User, String> nameColumn;
	@FXML
	private TableColumn<Room, String> roomColumn;
	@FXML
	private TableColumn<Room, String> statusColumn;
	@FXML
	private TableColumn<Patient, String> patientNameColumn;
	@FXML
	private TableColumn<Patient, String> fromDateColumn;
	@FXML
	private TableColumn<Patient, String> toDateColumn;

	@FXML
	private void initialize() {
		
		patientList = FXCollections.observableArrayList();
		showRoomList = FXCollections.observableArrayList();
		employeeList = FXCollections.observableArrayList();
		
		for (Room room : Main.rooms) {
			showRoomList.add(room);
		}
		for (User user : Main.users) {
			if (user instanceof Member)
				employeeList.add(user);
		}
		for (Patient patient : Main.patients) {
			patientList.add(patient);
		}
		employeeInformationTable.setItems(employeeList);
		roomInformationTable.setItems(showRoomList);
		patientInformationTable.setItems(patientList);

		positionColumn.setCellValueFactory(new PropertyValueFactory<User, String>("job"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		roomColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("roomName"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("Status"));
		patientNameColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("name"));
		fromDateColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("from"));
		toDateColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("to"));

		statusColumn.setStyle("-fx-alignment: CENTER;");
		roomColumn.setStyle("-fx-alignment: CENTER;");

		positionColumn.setStyle("-fx-alignment: CENTER;");
		nameColumn.setStyle("-fx-alignment: CENTER;");

		patientNameColumn.setStyle("-fx-alignment: CENTER;");
		fromDateColumn.setStyle("-fx-alignment: CENTER;");
		toDateColumn.setStyle("-fx-alignment: CENTER;");
	}

}
