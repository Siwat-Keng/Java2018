package main;

import java.time.LocalDate;

import Exception.MyInvalidException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import logic.DataCollector;
import people.Patient;
import people.Room;

public class AddPatientController {

	private Pair<Double, Double> dragDelta;

	private ObservableList<Room> roomList;
	private ObservableList<String> showRoomList;

	protected static ObservableList<Patient> patientList = FXCollections.observableArrayList();

	@FXML
	private ComboBox<String> roomBox;

	@FXML
	private TextField nameField;
	@FXML
	private DatePicker admitFromDate;
	@FXML
	private DatePicker admitToDate;

	@FXML
	private ImageView imageOk;
	@FXML
	private ImageView imageCancel;

	private final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
		@Override
		public DateCell call(final DatePicker datePicker) {
			return new DateCell() {
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);

					if (item.isBefore(admitFromDate.getValue().plusDays(1))) {
						setDisable(true);
						setStyle("-fx-background-color: #ffc0cb;");
					}

				}
			};
		}
	};

	@FXML
	private void initialize() {
		roomList = FXCollections.observableArrayList();
		showRoomList = FXCollections.observableArrayList();
		for (Room room : Main.rooms) {
			roomList.add(room);
		}

		for (Room room : roomList) {
			if (room.getStatus().equals("Avialable")) {
				showRoomList.add(room.getRoomName());
			}
		}
		if (showRoomList.isEmpty()) {
			roomBox.setDisable(true);
			roomBox.setPromptText("No room available");
		} else {
			roomBox.setItems(showRoomList);
			roomBox.setPromptText("Select Room");
		}
		admitFromDate.setValue(LocalDate.now());
		admitToDate.setValue(LocalDate.now().plusDays(1));

		admitToDate.setDayCellFactory(dayCellFactory);
		loadPicture();

	}

	@FXML
	private void handleMousePressed(MouseEvent mouseEvent) {
		Stage stage = (Stage) imageOk.getScene().getWindow();
		dragDelta = new Pair<Double, Double>(stage.getX() - mouseEvent.getScreenX(),
				stage.getY() - mouseEvent.getScreenY());
	}

	@FXML
	private void handleMouseDragged(MouseEvent mouseEvent) {
		Stage stage = (Stage) imageOk.getScene().getWindow();
		stage.setX(mouseEvent.getScreenX() + dragDelta.getKey());
		stage.setY(mouseEvent.getScreenY() + dragDelta.getValue());
	}

	@FXML
	private void mouseOnOkBtn() {
		String image_path = ClassLoader.getSystemResource("okmouse.png").toString();
		imageOk.setImage(new Image(image_path));
	}

	@FXML
	private void mouseOutOkBtn() {
		String image_path = ClassLoader.getSystemResource("okicon.png").toString();
		imageOk.setImage(new Image(image_path));
	}

	@FXML
	private void mouseOnCanceltBtn() {
		String image_path = ClassLoader.getSystemResource("cancelmouse.png").toString();
		imageCancel.setImage(new Image(image_path));
	}

	@FXML
	private void mouseOutCancelBtn() {
		String image_path = ClassLoader.getSystemResource("cancelicon.png").toString();
		imageCancel.setImage(new Image(image_path));
	}

	@FXML
	private void closeProgram() {
		Stage stage = (Stage) imageCancel.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void handleOkBtn() {
		String name = nameField.getText().trim();
		String room = roomBox.getValue();
		String fromDate = admitFromDate.getValue().toString();
		String toDate = admitToDate.getValue().toString();
		if(room == null || name.contentEquals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("Room and Name can not be empty.");
			alert.showAndWait();
			return;
		}
		if (DataCollector.checkAvialable(room, fromDate, toDate)) {
				try {
					if(Main.patients.contains(new Patient(name))) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setContentText("This name is not avialable.");
						alert.showAndWait();
						return;
					}
				} catch (MyInvalidException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Can not check patient's name.");
					alert.showAndWait();
					return;
				}
				try {
					Main.patients.add(new Patient(name, LocalDate.parse(fromDate), LocalDate.parse(toDate) , room));
					DataCollector.setRoomTask(room,LocalDate.parse(fromDate),LocalDate.parse(toDate),name);
					Stage stage = (Stage) imageOk.getScene().getWindow();
					stage.close();
				} catch (MyInvalidException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText(e.getMessage());
					alert.showAndWait();
					return;
				}
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("This room is not avialable at this time.");
			alert.showAndWait();
			return;
		}
	}

	private void loadPicture() {
		String image_path0 = ClassLoader.getSystemResource("okicon.png").toString();
		imageOk.setImage(new Image(image_path0));
		String image_path1 = ClassLoader.getSystemResource("cancelicon.png").toString();
		imageCancel.setImage(new Image(image_path1));
	}
}
