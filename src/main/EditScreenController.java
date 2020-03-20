package main;

import Exception.MyInvalidException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import logic.DataCollector;
import people.Room;

public class EditScreenController {

	private Pair<Double, Double> dragDelta;
	
    @FXML
    private ImageView imageOk;

    @FXML
    private ImageView imageCancel;

    @FXML
    private TextField nameField;

    @FXML
    private RadioButton addBtn;

    @FXML
    private ToggleGroup Mode;

    @FXML
    private RadioButton removeBtn;

    @FXML
	private void initialize() {
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
		dragDelta = new Pair<Double, Double>(stage.getX() - event.getScreenX(),
				stage.getY() - event.getScreenY());
    }

    @FXML
    void handleOkBtn(MouseEvent event) {
    	if(addBtn.isSelected()) {
    		for( Room room : Main.rooms) {
    			if(room.getRoomName().toLowerCase().equals(nameField.getText().trim().toLowerCase())) {
    				Alert alert = new Alert(AlertType.WARNING);
    				alert.setTitle("Add Room");
    				alert.setContentText("This name is not avialable.");
    				alert.showAndWait();
    				return;
    			}
    		}
    		Main.rooms.add(new Room(nameField.getText().trim().toLowerCase()));
    		closeProgram(event);
    	}
    	else {
    		for( Room room : Main.rooms) {
    			if(room.getRoomName().toLowerCase().equals(nameField.getText().trim().toLowerCase())) {
    				try {
						DataCollector.remove(room);
					} catch (MyInvalidException e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Remove Error");
						alert.setContentText(e.getMessage());
						alert.showAndWait();
						return;
					}
    				closeProgram(event);
    				return;
    			}
    		}
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
