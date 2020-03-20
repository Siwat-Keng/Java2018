package main;

import java.io.IOException;

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
import people.Manager;
import people.Room;

public class RoomPaneController {
	
	private ObservableList<Room> roomList;
	
	@FXML
	private TableView<Room> roomTable;
	
	@FXML
	private TableView<Room> scheduleTable;
	
	@FXML
	private TableColumn<Room, String> roomColumn;
	
	@FXML
	private TableColumn<Room, String> label1Column;
	@FXML
	private TableColumn<Room, String> label2Column;
	@FXML
	private TableColumn<Room, String> label3Column;
	@FXML
	private TableColumn<Room, String> label4Column;
	@FXML
	private TableColumn<Room, String> label5Column;
	@FXML
	private TableColumn<Room, String> label6Column;
	@FXML
	private TableColumn<Room, String> label7Column;
	@FXML
	private TableColumn<Room, String> label8Column;
	@FXML
	private TableColumn<Room, String> label9Column;
	@FXML
	private TableColumn<Room, String> label10Column;
	@FXML
	private TableColumn<Room, String> label11Column;
	@FXML
	private TableColumn<Room, String> label12Column;
	@FXML
	private TableColumn<Room, String> label13Column;
	@FXML
	private TableColumn<Room, String> label14Column;
	@FXML
	private TableColumn<Room, String> label15Column;
	@FXML
	private TableColumn<Room, String> label16Column;
	@FXML
	private TableColumn<Room, String> label17Column;
	@FXML
	private TableColumn<Room, String> label18Column;
	@FXML
	private TableColumn<Room, String> label19Column;
	@FXML
	private TableColumn<Room, String> label20Column;
	@FXML
	private TableColumn<Room, String> label21Column;
	@FXML
	private TableColumn<Room, String> label22Column;
	@FXML
	private TableColumn<Room, String> label23Column;
	@FXML
	private TableColumn<Room, String> label24Column;
	
	@FXML
	private ImageView imageEditroom;
	
	@FXML
	private Label editLabel;
	
	@FXML
	private void initialize() {
		
		roomList = FXCollections.observableArrayList();
		for( Room room : Main.rooms ) {
			roomList.add(room);
		}
		roomTable.setItems(roomList);
		scheduleTable.setItems(roomList);
		roomTable.setFixedCellSize(40);
		scheduleTable.setFixedCellSize(40);
		
		roomColumn.setCellValueFactory(new PropertyValueFactory<Room,String>("roomName"));
		roomColumn.setStyle("-fx-alignment: CENTER;");
		
		label1Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label1") );
		label2Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label2") );
		label3Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label3") );
		label4Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label4") );
		label5Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label5") );
		label6Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label6") );
		label7Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label7") );
		label8Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label8") );
		label9Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label9") );
		label10Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label10") );
		label11Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label11") );
		label12Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label12") );
		label13Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label13") );
		label14Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label14") );
		label15Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label15") );
		label16Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label16") );
		label17Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label17") );
		label18Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label18") );
		label19Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label19") );
		label20Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label20") );
		label21Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label21") );
		label22Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label22") );
		label23Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label23") );
		label24Column.setCellValueFactory(
				new PropertyValueFactory<Room,String>("label24") );
		loadPicture();
	}
	
	@FXML
	private void handleEditBtn() {
		if(Main.currentuser instanceof Manager) {
			try {
				Main.showEditRoom();
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("UnknownError");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				return;
			}
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Permission");
			alert.setContentText("You don't have this permission.");
			alert.showAndWait();
			return;
		}
	}
	
	@FXML
	private void mouseEnterEdit() {
		String image_path = ClassLoader.getSystemResource("editroommouse.png").toString();
		imageEditroom.setImage(new Image(image_path));
		editLabel.setTextFill(Color.web("#ff9900"));
	}
	
	@FXML
	private void mouseOutEdit() {
		String image_path = ClassLoader.getSystemResource("editroomicon.png").toString();
		imageEditroom.setImage(new Image(image_path));
		editLabel.setTextFill(Color.web("#d3d3d3"));
	}
	
	private void loadPicture() {
		String image_path = ClassLoader.getSystemResource("editroomicon.png").toString();
		imageEditroom.setImage(new Image(image_path));
		editLabel.setTextFill(Color.web("#d3d3d3"));
	}
	
}
