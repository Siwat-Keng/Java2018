package main;

import java.util.Optional;

import Exception.MyInvalidException;
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
import people.User;

public class RemoveScreenController {

	private Pair<Double, Double> dragDelta;

	private ObservableList<User> userList;
	private ObservableList<String> showUserList;

	@FXML
	private ComboBox<String> nameBox;

	@FXML
	private ImageView imageOk;

	@FXML
	private ImageView imageCancel;

	@FXML
	private void initialize() {

		showUserList = FXCollections.observableArrayList();
		userList = FXCollections.observableArrayList();

		for (User user : Main.users) {
			userList.add(user);
		}

		for (User u : userList) {
			showUserList.add(u.getUsername());
		}
		nameBox.setItems(showUserList);
		nameBox.setPromptText("Select User");
		loadPicture();
	}

	private void loadPicture() {
		String image_path0 = ClassLoader.getSystemResource("okicon.png").toString();
		imageOk.setImage(new Image(image_path0));
		String image_path1 = ClassLoader.getSystemResource("cancelicon.png").toString();
		imageCancel.setImage(new Image(image_path1));
	}

	@FXML
	void closeProgram(MouseEvent event) {
		Stage stage = (Stage) imageCancel.getScene().getWindow();
		stage.close();
	}

	@FXML
	void handleMouseDragged(MouseEvent event) {
		Stage stage = (Stage) imageOk.getScene().getWindow();
		stage.setX(event.getScreenX() + dragDelta.getKey());
		stage.setY(event.getScreenY() + dragDelta.getValue());
	}

	@FXML
	void handleMousePressed(MouseEvent event) {
		Stage stage = (Stage) imageOk.getScene().getWindow();
		dragDelta = new Pair<Double, Double>(stage.getX() - event.getScreenX(), stage.getY() - event.getScreenY());
	}

	@FXML
	void handleOkBtn(MouseEvent event) {
		Alert conf = new Alert(AlertType.CONFIRMATION);
		conf.setTitle("Remove User");
		conf.setHeaderText("Confirmation");
		conf.setContentText("Do you want to remove ?");
		if (nameBox.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR, "Please Select User");
			alert.showAndWait();
			return;
		}
		Optional<ButtonType> result = conf.showAndWait();
		if (result.get() == ButtonType.OK) {
			String name = nameBox.getValue();
			for (User user : Main.users) {
				if (user.getUsername().equals(name)) {
					try {
						DataCollector.remove(user);
					} catch (MyInvalidException e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Remove User");
						alert.setContentText(e.getMessage());
						alert.showAndWait();
						return;
					}
					Main.users.remove(user);
					break;
				}
			}
			Stage stage = (Stage) imageCancel.getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	void mouseOnCanceltBtn(MouseEvent event) {
		String image_path = ClassLoader.getSystemResource("cancelmouse.png").toString();
		imageCancel.setImage(new Image(image_path));
	}

	@FXML
	void mouseOnOkBtn(MouseEvent event) {
		String image_path = ClassLoader.getSystemResource("okmouse.png").toString();
		imageOk.setImage(new Image(image_path));
	}

	@FXML
	void mouseOutCancelBtn(MouseEvent event) {
		String image_path = ClassLoader.getSystemResource("cancelicon.png").toString();
		imageCancel.setImage(new Image(image_path));
	}

	@FXML
	void mouseOutOkBtn(MouseEvent event) {
		String image_path = ClassLoader.getSystemResource("okicon.png").toString();
		imageOk.setImage(new Image(image_path));
	}

}
