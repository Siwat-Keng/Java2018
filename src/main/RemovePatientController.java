package main;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import logic.DataCollector;
import people.Patient;

public class RemovePatientController {

	private Pair<Double, Double> dragDelta;

	private ObservableList<Patient> patientList;
	private ObservableList<String> showPatientList;

	@FXML
	private ComboBox<String> nameBox;

	@FXML
	private ImageView imageOk;

	@FXML
	private ImageView imageCancel;

	@FXML
	private void initialize() {
		
		showPatientList = FXCollections.observableArrayList();
		patientList = FXCollections.observableArrayList();
		
		for( Patient patient : Main.patients ) {
			patientList.add(patient);
		}
		if (patientList.isEmpty()) {
			nameBox.setDisable(true);
			nameBox.setPromptText("No Patient");
		} else {
			for (Patient p : patientList) {
				showPatientList.add(p.getName());
			}
			nameBox.setItems(showPatientList);
			nameBox.setPromptText("Select Patient");
		}
		loadPicture();
	}

	private void loadPicture() {
		String image_path0 = ClassLoader.getSystemResource("okicon.png").toString();
		imageOk.setImage(new Image(image_path0));
		String image_path1 = ClassLoader.getSystemResource("cancelicon.png").toString();
		imageCancel.setImage(new Image(image_path1));
	}

	@FXML
	public void handleMousePressed(MouseEvent mouseEvent) {
		Stage stage = (Stage) imageOk.getScene().getWindow();
		dragDelta = new Pair<Double, Double>(stage.getX() - mouseEvent.getScreenX(), stage.getY() - mouseEvent.getScreenY());
	}

	@FXML
	public void handleMouseDragged(MouseEvent mouseEvent) {
		Stage stage = (Stage) imageOk.getScene().getWindow();
		stage.setX(mouseEvent.getScreenX() + dragDelta.getKey());
		stage.setY(mouseEvent.getScreenY() + dragDelta.getValue());
	}

	@FXML
	public void mouseOnOkBtn() {
		String image_path = ClassLoader.getSystemResource("okmouse.png").toString();
		imageOk.setImage(new Image(image_path));
	}

	@FXML
	public void mouseOutOkBtn() {
		String image_path = ClassLoader.getSystemResource("okicon.png").toString();
		imageOk.setImage(new Image(image_path));
	}

	@FXML
	public void mouseOnCanceltBtn() {
		String image_path = ClassLoader.getSystemResource("cancelmouse.png").toString();
		imageCancel.setImage(new Image(image_path));
	}

	@FXML
	public void mouseOutCancelBtn() {
		String image_path = ClassLoader.getSystemResource("cancelicon.png").toString();
		imageCancel.setImage(new Image(image_path));
	}

	@FXML
	public void closeProgram() {
		Stage stage = (Stage) imageCancel.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void handleOkBtn() {
		Alert conf = new Alert(AlertType.CONFIRMATION);
		conf.setTitle("Remove Patient");
		conf.setHeaderText("Confirmation");
		conf.setContentText("Do you want to remove ?");
		if (nameBox.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR, "Please Select Patient");
			alert.showAndWait();
			return;
		}
		Optional<ButtonType> result = conf.showAndWait();
		if (result.get() == ButtonType.OK) {
			String name = nameBox.getValue();
			for( Patient patient : Main.patients ) {
				if(patient.getName().equals(name)) {
					Main.patients.remove(patient);
					DataCollector.remove(patient);
					break;
				}
			}
			Stage stage = (Stage) imageCancel.getScene().getWindow();
			stage.close();
		} 
	}
}
