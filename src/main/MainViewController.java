package main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import Interface.Member;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import people.Patient;
import people.Room;
import people.User;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class MainViewController {

	private Pair<Double, Double> dragDelta;

	@FXML
	private Label takeLeaveLabel;

	@FXML
	private ImageView imageTakeLeave;

	@FXML
	private Label removePatientLabel;

	@FXML
	private ImageView imageRemovePatient;

	@FXML
	private ImageView imageCalender;

	@FXML
	private Label patientLabel;

	@FXML
	private ImageView imagePatient;

	@FXML
	private Label shiftLabel;

	@FXML
	private ImageView imageShift;

	@FXML
	private Label homeLabel;

	@FXML
	private Label informationLabel;

	@FXML
	private ImageView imageHome;

	@FXML
	private ImageView imageClose;

	@FXML
	private ImageView imageInformation;

	@FXML
	private Label currentuserLabel;

	@FXML
	private Button logoutBtn;

	@FXML
	public void initialize() {
		if (Main.currentuser == null) {
			this.currentuserLabel.setText("None");
			return;
		}
		this.currentuserLabel.setText(Main.currentuser.getUsername());
		loadPicture();
	}

	@FXML
	public void handlelogoutBtn() {
		Main.currentuser = null;
		Main.selectedDate = LocalDate.now();
		Main.users = new ArrayList<User>();
		Main.rooms = new ArrayList<Room>();
		Main.patients = new ArrayList<Patient>();
		Main.showLogin();
	}

	@FXML
	public void handleMousePressed(MouseEvent mouseEvent) {
		this.dragDelta = new Pair<Double, Double>(Main.primaryStage.getX() - mouseEvent.getScreenX(),
				Main.primaryStage.getY() - mouseEvent.getScreenY());
	}

	@FXML
	public void handleMouseDragged(MouseEvent mouseEvent) {
		Main.primaryStage.setX(mouseEvent.getScreenX() + dragDelta.getKey());
		Main.primaryStage.setY(mouseEvent.getScreenY() + dragDelta.getValue());
	}

	@FXML
	private void goHome() throws IOException {
		Main.showHomePane();
	}

	@FXML
	private void goInformation() throws IOException {
		Main.showInformationPane();
	}

	@FXML
	private void showRemovePatientScreen() throws IOException {
		if (!(Main.currentuser instanceof Member)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Permission");
			alert.setContentText("You don't have this permission.");
			alert.showAndWait();
			return;
		}
		Main.showRemovePatientScreen();
	}

	@FXML
	private void showTakeLeaveScreen() throws IOException {
		if (!(Main.currentuser instanceof Member)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Permission");
			alert.setContentText("You don't have this permission.");
			alert.showAndWait();
			return;
		}
		Main.showTakeLeaveScreen();
	}

	@FXML
	private void closeProgram() {
		Alert conf = new Alert(AlertType.CONFIRMATION);
		conf.setTitle("Close Program");
		conf.setHeaderText("Confirmation");
		conf.setContentText("Do you want to close this program ?");
		Optional<ButtonType> result = conf.showAndWait();
		if (result.get() == ButtonType.OK)
			Main.threadRunning = false;
			Platform.exit();
	}

	@FXML
	private void addShiftBtn() throws IOException {
		if (!(Main.currentuser instanceof Member)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Permission");
			alert.setContentText("You don't have this permission.");
			alert.showAndWait();
			return;
		}
		Main.showAddShift();
	}

	@FXML
	private void addPatientBtn() throws IOException {
		if (!(Main.currentuser instanceof Member)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Permission");
			alert.setContentText("You don't have this permission.");
			alert.showAndWait();
			return;
		}
		Main.showAddPatient();
	}

	@FXML
	private void mouseOnTakeLeaveBtn() {
		takeLeaveLabel.setTextFill(Color.web("#ff9900"));
		String image_path = ClassLoader.getSystemResource("takeleavemouse.png").toString();
		imageTakeLeave.setImage(new Image(image_path));
	}

	@FXML
	private void mouseOutTakeLeaveBtn() {
		String image_path = ClassLoader.getSystemResource("takeleaveicon.png").toString();
		imageTakeLeave.setImage(new Image(image_path));
		takeLeaveLabel.setTextFill(Color.web("#d3d3d3"));
	}

	@FXML
	private void mouseOnRemovePatientBtn() {
		removePatientLabel.setTextFill(Color.web("#ff9900"));
		String image_path = ClassLoader.getSystemResource("removepatientmouse.png").toString();
		imageRemovePatient.setImage(new Image(image_path));
	}

	@FXML
	private void mouseOutRemovePatientBtn() {
		String image_path = ClassLoader.getSystemResource("removepatienticon.png").toString();
		imageRemovePatient.setImage(new Image(image_path));
		removePatientLabel.setTextFill(Color.web("#d3d3d3"));
	}

	@FXML
	private void mouseOnPatientBtn() {
		String image_path = ClassLoader.getSystemResource("patientmouse.png").toString();
		imagePatient.setImage(new Image(image_path));
		patientLabel.setTextFill(Color.web("#ff9900"));
	}

	@FXML
	private void mouseOutPatientBtn() {
		String image_path = ClassLoader.getSystemResource("patienticon.png").toString();
		imagePatient.setImage(new Image(image_path));
		patientLabel.setTextFill(Color.web("#d3d3d3"));
	}

	@FXML
	private void mouseOnInformationBtn() {
		String image_path = ClassLoader.getSystemResource("informationmouse.png").toString();
		imageInformation.setImage(new Image(image_path));
		informationLabel.setTextFill(Color.web("#ff9900"));
	}

	@FXML
	private void mouseOutInformationBtn() {
		String image_path = ClassLoader.getSystemResource("informationicon.png").toString();
		imageInformation.setImage(new Image(image_path));
		informationLabel.setTextFill(Color.web("#d3d3d3"));
	}

	@FXML
	private void mouseOnShiftBtn() {
		String image_path = ClassLoader.getSystemResource("shiftmouse.png").toString();
		imageShift.setImage(new Image(image_path));
		shiftLabel.setTextFill(Color.web("#ff9900"));
	}

	@FXML
	private void mouseOutShiftBtn() {
		String image_path = ClassLoader.getSystemResource("shifticon.png").toString();
		imageShift.setImage(new Image(image_path));
		shiftLabel.setTextFill(Color.web("#d3d3d3"));
	}

	@FXML
	private void mouseOnHomeBtn() {
		String image_path = ClassLoader.getSystemResource("homemouse.png").toString();
		imageHome.setImage(new Image(image_path));
		homeLabel.setTextFill(Color.web("#ff9900"));
	}

	@FXML
	private void mouseOutHomeBtn() {
		String image_path = ClassLoader.getSystemResource("homeicon.png").toString();
		imageHome.setImage(new Image(image_path));
		homeLabel.setTextFill(Color.web("#d3d3d3"));
	}

	@FXML
	private void mouseOnCloseBtn() {
		String image_path = ClassLoader.getSystemResource("closemouse.png").toString();
		imageClose.setImage(new Image(image_path));
	}

	@FXML
	private void mouseOutCloseBtn() {
		String image_path = ClassLoader.getSystemResource("closeicon.png").toString();
		imageClose.setImage(new Image(image_path));
	}

	private void loadPicture() {
		mouseOutTakeLeaveBtn();
		mouseOutRemovePatientBtn();
		mouseOutPatientBtn();
		mouseOutInformationBtn();
		mouseOutShiftBtn();
		mouseOutHomeBtn();
		mouseOutCloseBtn();
	}
}
