package main;

import java.io.IOException;

import Interface.Editable;
import Interface.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import people.User;

public class EmployeePaneController {
	
	private static ObservableList<User> UserList;

	@FXML
	private Label addLabel;

	@FXML
	private Label removeLabel;

	@FXML
	private Label editLabel;

	@FXML
	private ImageView imageRemove;

	@FXML
	private TableView<User> employeeTable;
	@FXML
	private TableView<User> scheduleTable;
	@FXML
	private TableColumn<User, String> positionColumn;
	@FXML
	private TableColumn<User, String> nameColumn;
	@FXML
	private TableColumn<User, String> label1Column;
	@FXML
	private TableColumn<User, String> label2Column;
	@FXML
	private TableColumn<User, String> label3Column;
	@FXML
	private TableColumn<User, String> label4Column;
	@FXML
	private TableColumn<User, String> label5Column;
	@FXML
	private TableColumn<User, String> label6Column;
	@FXML
	private TableColumn<User, String> label7Column;
	@FXML
	private TableColumn<User, String> label8Column;
	@FXML
	private TableColumn<User, String> label9Column;
	@FXML
	private TableColumn<User, String> label10Column;
	@FXML
	private TableColumn<User, String> label11Column;
	@FXML
	private TableColumn<User, String> label12Column;
	@FXML
	private TableColumn<User, String> label13Column;
	@FXML
	private TableColumn<User, String> label14Column;
	@FXML
	private TableColumn<User, String> label15Column;
	@FXML
	private TableColumn<User, String> label16Column;
	@FXML
	private TableColumn<User, String> label17Column;
	@FXML
	private TableColumn<User, String> label18Column;
	@FXML
	private TableColumn<User, String> label19Column;
	@FXML
	private TableColumn<User, String> label20Column;
	@FXML
	private TableColumn<User, String> label21Column;
	@FXML
	private TableColumn<User, String> label22Column;
	@FXML
	private TableColumn<User, String> label23Column;
	@FXML
	private TableColumn<User, String> label24Column;

	@FXML
	private void initialize() {
		UserList = FXCollections.observableArrayList();
		for (User user : Main.users) {
			if (user instanceof Member)
				UserList.add(user);
		}
		employeeTable.setItems(UserList);
		scheduleTable.setItems(UserList);
		employeeTable.setFixedCellSize(40);
		scheduleTable.setFixedCellSize(40);
		positionColumn.setCellValueFactory(new PropertyValueFactory<User, String>("job"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));	
		positionColumn.setStyle("-fx-alignment: CENTER;");
		nameColumn.setStyle("-fx-alignment: CENTER;");
		label1Column.setCellValueFactory(new PropertyValueFactory<User, String>("label1"));
		label2Column.setCellValueFactory(new PropertyValueFactory<User, String>("label2"));
		label3Column.setCellValueFactory(new PropertyValueFactory<User, String>("label3"));
		label4Column.setCellValueFactory(new PropertyValueFactory<User, String>("label4"));
		label5Column.setCellValueFactory(new PropertyValueFactory<User, String>("label5"));
		label6Column.setCellValueFactory(new PropertyValueFactory<User, String>("label6"));
		label7Column.setCellValueFactory(new PropertyValueFactory<User, String>("label7"));
		label8Column.setCellValueFactory(new PropertyValueFactory<User, String>("label8"));
		label9Column.setCellValueFactory(new PropertyValueFactory<User, String>("label9"));
		label10Column.setCellValueFactory(new PropertyValueFactory<User, String>("label10"));
		label11Column.setCellValueFactory(new PropertyValueFactory<User, String>("label11"));
		label12Column.setCellValueFactory(new PropertyValueFactory<User, String>("label12"));
		label13Column.setCellValueFactory(new PropertyValueFactory<User, String>("label13"));
		label14Column.setCellValueFactory(new PropertyValueFactory<User, String>("label14"));
		label15Column.setCellValueFactory(new PropertyValueFactory<User, String>("label15"));
		label16Column.setCellValueFactory(new PropertyValueFactory<User, String>("label16"));
		label17Column.setCellValueFactory(new PropertyValueFactory<User, String>("label17"));
		label18Column.setCellValueFactory(new PropertyValueFactory<User, String>("label18"));
		label19Column.setCellValueFactory(new PropertyValueFactory<User, String>("label19"));
		label20Column.setCellValueFactory(new PropertyValueFactory<User, String>("label20"));
		label21Column.setCellValueFactory(new PropertyValueFactory<User, String>("label21"));
		label22Column.setCellValueFactory(new PropertyValueFactory<User, String>("label22"));
		label23Column.setCellValueFactory(new PropertyValueFactory<User, String>("label23"));
		label24Column.setCellValueFactory(new PropertyValueFactory<User, String>("label24"));
		loadPicture();
	}

	@FXML
	private void mouseOnRemoveBtn() {
		String image_path = ClassLoader.getSystemResource("removemouse.png").toString();
		imageRemove.setImage(new Image(image_path));
		removeLabel.setTextFill(Color.web("#ff9900"));
	}

	@FXML
	private void mouseOutRemoveBtn() {
		String image_path = ClassLoader.getSystemResource("removeicon.png").toString();
		imageRemove.setImage(new Image(image_path));
		removeLabel.setTextFill(Color.web("#d3d3d3"));
	}

	@FXML
	private void removeBtn() {
		if(!(Main.currentuser instanceof Editable)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Permission");
			alert.setContentText("You don't have this permission");
			alert.showAndWait();
			return;
		}
		try {
			Main.showRemoveScreen();
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Unknown Error");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			return;
		}
	}
	
	private void loadPicture() {
		String image_path = ClassLoader.getSystemResource("removeicon.png").toString();
		imageRemove.setImage(new Image(image_path));
		removeLabel.setTextFill(Color.web("#d3d3d3"));
	}

}