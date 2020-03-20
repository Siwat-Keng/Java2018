package main;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class HomePaneController {
	
	@FXML
	private DatePicker selectedDate;
	
	@FXML
	private Label employeeLabel;
	
	@FXML
	private Label roomLabel;
	
	@FXML
	private ImageView imageEmployee;
	
	@FXML
	private ImageView imageRoom;
	
	@FXML
	private ImageView imageLeft;
	
	@FXML
	private ImageView imageRight;
	
	@FXML
	private ImageView imageCalender;
	
	@FXML
	private void initialize() {
		selectedDate.setValue(LocalDate.now());
		Main.selectedDate = selectedDate.getValue();
		loadPicture();
	}

	@FXML
	private void goNextDay() {
		selectedDate.setValue(selectedDate.getValue().plusDays(1));
		Main.selectedDate = selectedDate.getValue();
	}
	@FXML
	private void goPrevDay() {
		selectedDate.setValue(selectedDate.getValue().minusDays(1));
		Main.selectedDate = selectedDate.getValue();
	}
	
	@FXML
	private void setSelectedDate() {
		Main.selectedDate = selectedDate.getValue();
	}
	
	@FXML
	private void goEmployeeSchedule() throws IOException {
		Main.showEmployeeSchedule();
	}
	
	@FXML
	private void goRoomSchedule() throws IOException {
		Main.showRoomSchedule();
	}
	
	@FXML
	private void mouseOnLeftBtn() {
		String image_path = ClassLoader.getSystemResource("leftmouse.png").toString();
		imageLeft.setImage(new Image(image_path));
	}
	
	@FXML
	private void mouseOutLeftBtn() {
		String image_path = ClassLoader.getSystemResource("lefticon.png").toString();
		imageLeft.setImage(new Image(image_path));
	}
	
	@FXML
	private void mouseOnRightBtn() {
		String image_path = ClassLoader.getSystemResource("rightmouse.png").toString();
		imageRight.setImage(new Image(image_path));
	}
	
	@FXML
	private void mouseOutRightBtn() {
		String image_path = ClassLoader.getSystemResource("righticon.png").toString();
		imageRight.setImage(new Image(image_path));
	}
	
	@FXML
	private void mouseOnEmployeeBtn() {
		String image_path = ClassLoader.getSystemResource("employeemouse.png").toString();
		imageEmployee.setImage(new Image(image_path));
		employeeLabel.setTextFill(Color.web("#ff9900"));
	}
	
	@FXML
	private void mouseOutEmployeeBtn() {
		String image_path = ClassLoader.getSystemResource("employeeicon.png").toString();
		imageEmployee.setImage(new Image(image_path));
		employeeLabel.setTextFill(Color.web("#d3d3d3"));
	}
	
	@FXML
	private void mouseOnRoomBtn() {
		String image_path = ClassLoader.getSystemResource("roommouse.png").toString();
		imageRoom.setImage(new Image(image_path));
		roomLabel.setTextFill(Color.web("#ff9900"));
	}
	
	@FXML
	private void mouseOutRoomBtn() {
		String image_path = ClassLoader.getSystemResource("roomicon.png").toString();
		imageRoom.setImage(new Image(image_path));
		roomLabel.setTextFill(Color.web("#d3d3d3"));
	}	
	
	private void loadPicture() {
		String image_path = ClassLoader.getSystemResource("scheduleicon.png").toString();
		imageCalender.setImage(new Image(image_path));
		mouseOutEmployeeBtn();
		mouseOutLeftBtn();
		mouseOutRightBtn();
		mouseOutRoomBtn();
	}
}