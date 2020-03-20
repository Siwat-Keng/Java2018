package main;

import java.time.LocalDate;
import java.util.ArrayList;

import Interface.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import logic.DataCollector;
import people.User;

public class TakeLeaveScreenController {
	private Pair<Double, Double> dragDelta;

	private ObservableList<User> employeeList = FXCollections.observableArrayList();
	private ObservableList<String> positionList = FXCollections.observableArrayList();
	private ArrayList<ObservableList<String>> position = new ArrayList<ObservableList<String>>();

	private ObservableList<String> timeList = FXCollections.observableArrayList("01:00", "02:00", "03:00", "04:00",
			"05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
			"17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00");

	@FXML
	private DatePicker workDate;

	@FXML
	private ComboBox<String> nameBox;

	@FXML
	private ComboBox<String> positionBox;

	@FXML
	private ComboBox<String> fromBox;

	@FXML
	private Label fromDateLabel;

	@FXML
	private Label toDateLabel;

	@FXML
	private ComboBox<String> toBox;

	@FXML
	private ImageView imageOk;

	@FXML
	private ImageView imageCancel;

	@FXML
	private void initialize() {
		for (User user : Main.users) {
			if (user instanceof Member) {
				employeeList.add(user);
				if (!positionList.contains(user.getJob())) {
					positionList.add(user.getJob());
					position.add(FXCollections.observableArrayList(user.getUsername()));
				} else {
					position.get(positionList.lastIndexOf(user.getJob())).add(user.getUsername());
				}
			}
		}
		positionBox.setPromptText("Select Position");
		positionBox.setItems(positionList);
		workDate.setValue(LocalDate.now());
		nameBox.setPromptText("Select Position First");
		nameBox.setDisable(true);
		fromBox.setItems(timeList);
		toBox.setItems(timeList);
		fromBox.setValue("01:00");
		toBox.setValue("24:00");
		fromDateLabel.setText(workDate.getValue().toString());
		toDateLabel.setText(workDate.getValue().toString());
		loadPicture();
	}

	@FXML
	private void dateSelected() {
		fromDateLabel.setText(workDate.getValue().toString());
		if (toBox.getValue().compareTo(fromBox.getValue()) < 0) {
			toDateLabel.setText(workDate.getValue().plusDays(1).toString());
		} else {
			toDateLabel.setText(workDate.getValue().toString());
		}
	}

	@FXML
	private void setToDateLabel() {
		if (toBox.getValue().compareTo(fromBox.getValue()) == 0) {
			toDateLabel.setText(workDate.getValue().plusDays(1).toString());
		} else if (toBox.getValue().compareTo(fromBox.getValue()) < 0) {
			toDateLabel.setText(workDate.getValue().plusDays(1).toString());
		} else {
			toDateLabel.setText(workDate.getValue().toString());
		}
	}

	@FXML
	private void showNameList() {
		if (!positionBox.getValue().equals("")) {
			nameBox.setItems(position.get(positionList.indexOf(positionBox.getValue())));
			nameBox.setDisable(false);
			nameBox.setPromptText("Select Employee");
		}
	}

	@FXML
	public void handleMousePressed(MouseEvent mouseEvent) {
		Stage stage = (Stage) imageOk.getScene().getWindow();
		dragDelta = new Pair<Double, Double>(stage.getX() - mouseEvent.getScreenX(),
				stage.getY() - mouseEvent.getScreenY());
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
		String name = nameBox.getValue();
		if(name == null) {
			Alert alert = new Alert(AlertType.ERROR,"Please Fill In All Information");
			alert.setTitle("Take Leave Error");
			alert.showAndWait();
			return;
		}
		else {
			int start = Integer.parseInt(fromBox.getValue().substring(0, 2));
			int end = Integer.parseInt(toBox.getValue().substring(0, 2));
			LocalDate first = LocalDate.parse(fromDateLabel.getText());
			LocalDate last = LocalDate.parse(toDateLabel.getText());
			if(DataCollector.canTakeleave(name,first,last,start,end)) {
				DataCollector.addTakeleave(name,first,last,start,end);
				closeProgram();
			}
		}
	}
	
	private void loadPicture() {
		String image_path0 = ClassLoader.getSystemResource("cancelicon.png").toString();
		imageCancel.setImage(new Image(image_path0));
		String image_path1 = ClassLoader.getSystemResource("okicon.png").toString();
		imageOk.setImage(new Image(image_path1));
	}
}
