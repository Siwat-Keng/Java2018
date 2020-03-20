package main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.DataCollector;
import logic.FileManagement;
import people.Patient;
import people.Room;
import people.User;

public class Main extends Application {
	protected static Stage primaryStage;
	protected static BorderPane mainLayout;
	protected static BorderPane loginLayout;
	public static ArrayList<User> users;
	public static ArrayList<Room> rooms;
	public static ArrayList<Patient> patients;
	public static User currentuser;
	public static LocalDate selectedDate;
	public static final String takeLeave = "TAKELEAVE";
	public static final String idle = "IDLE";
	public static boolean threadRunning = true;

	@Override
	public void start(Stage primaryStage) throws IOException {
		users = new ArrayList<User>();
		rooms = new ArrayList<Room>();
		patients = new ArrayList<Patient>();
		selectedDate = LocalDate.now();
		currentuser = null;
		FileManagement.loadAllData();
		threadInitialize();
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("Schedule App");
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		showMainView();
	}

	public static void showLogin() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("LoginScreen.fxml"));
		try {
			loginLayout = loader.load();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Login Error");
			alert.setContentText(e.getCause().toString());
			alert.showAndWait();
			return;
		}
		Scene scene = new Scene(loginLayout);
		scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
		if(primaryStage.isShowing()) {
			primaryStage.close();
		}
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void showMainView() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("MainView.fxml"));
		try {
			mainLayout = loader.load();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Main Error");
			alert.setContentText(e.getCause().toString());
			alert.showAndWait();
			System.out.print(e.getMessage());
			return;
		}
		Scene scene = new Scene(mainLayout);
		scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
		if(primaryStage.isShowing()) {
			primaryStage.close();
		}
		primaryStage.setScene(scene);
		primaryStage.show();
		showHomePane();
		if(currentuser == null) {
			showLogin();
		}
	}

	public static void showHomePane() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("HomePane.fxml"));
		BorderPane home = null;
		try {
			home = loader.load();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Home Error");
			alert.setContentText(e.getCause().toString());
			alert.showAndWait();
			return;
		}
		mainLayout.setCenter(home);
	}

	public static void showEmployeeSchedule() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("EmployeePane.fxml"));
		BorderPane employeeSchedule = loader.load();
		mainLayout.setCenter(employeeSchedule);
	}

	public static void showInformationPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("InformationPane.fxml"));
		BorderPane employeeSchedule = loader.load();
		mainLayout.setCenter(employeeSchedule);
	}

	public static void showRoomSchedule() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("RoomPane.fxml"));
		BorderPane roomSchedule = loader.load();
		mainLayout.setCenter(roomSchedule);
	}

	public static void showRemovePatientScreen() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("RemovePatientScreen.fxml"));
		BorderPane removeScreen = loader.load();
		Stage removeStage = new Stage();
		removeStage.initStyle(StageStyle.TRANSPARENT);
		removeStage.setTitle("Remove Patient");
		removeStage.initModality(Modality.WINDOW_MODAL);
		removeStage.initOwner(primaryStage);
		Scene scene = new Scene(removeScreen);
		scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
		removeStage.setScene(scene);
		removeStage.showAndWait();
	}

	public static void showTakeLeaveScreen() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("TakeLeaveScreen.fxml"));
		BorderPane leaveScreen = loader.load();
		Stage leaveStage = new Stage();
		leaveStage.initStyle(StageStyle.TRANSPARENT);
		leaveStage.setTitle("Take Leave");
		leaveStage.initModality(Modality.WINDOW_MODAL);
		leaveStage.initOwner(primaryStage);
		Scene scene = new Scene(leaveScreen);
		scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
		leaveStage.setScene(scene);
		leaveStage.showAndWait();
	}

	public static void showAddShift() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("AddShift.fxml"));
		BorderPane addScreen = loader.load();
		Stage addStage = new Stage();
		addStage.initStyle(StageStyle.TRANSPARENT);
		addStage.setTitle("Add New Shift for Employee");
		addStage.initModality(Modality.WINDOW_MODAL);
		addStage.initOwner(primaryStage);
		Scene scene = new Scene(addScreen);
		scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
		addStage.setScene(scene);
		addStage.showAndWait();
	}

	public static void showAddPatient() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("AddPatient.fxml"));
		BorderPane addScreen = loader.load();
		Stage addStage = new Stage();
		addStage.initStyle(StageStyle.TRANSPARENT);
		addStage.setTitle("Add New Patient");
		addStage.initModality(Modality.WINDOW_MODAL);
		addStage.initOwner(primaryStage);
		Scene scene = new Scene(addScreen);
		scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
		addStage.setScene(scene);
		addStage.showAndWait();
	}
	
	public static void showRemoveScreen() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("RemoveScreen.fxml"));
		BorderPane RemoveScreen = loader.load();
		Stage removeStage = new Stage();
		removeStage.initStyle(StageStyle.TRANSPARENT);
		removeStage.setTitle("Remove User");
		removeStage.initModality(Modality.WINDOW_MODAL);
		removeStage.initOwner(primaryStage);
		Scene scene = new Scene(RemoveScreen);
		scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
		removeStage.setScene(scene);
		removeStage.showAndWait();
	}
	
	public static void showEditRoom() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("EditRoomScreen.fxml"));
		BorderPane EditScreen = loader.load();
		Stage EditStage = new Stage();
		EditStage.initStyle(StageStyle.TRANSPARENT);
		EditStage.setTitle("EditRoom");
		EditStage.initModality(Modality.WINDOW_MODAL);
		EditStage.initOwner(primaryStage);
		Scene scene = new Scene(EditScreen);
		scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
		EditStage.setScene(scene);
		EditStage.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void threadInitialize() {
		Thread updateThread = new Thread(() -> {
			while (threadRunning) {
				try {
					Thread.sleep(100);
					FileManagement.Update();
					DataCollector.setData();
				} catch (InterruptedException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Thread Error");
					alert.setContentText("Thread is interrupted");
					alert.showAndWait();
					return;
				}
			}
		});
		Thread writerThread = new Thread(() -> {
			while (threadRunning) {
				try {
					Thread.sleep(1000);
					FileManagement.writeAllJson();
				} catch (InterruptedException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Thread Error");
					alert.setContentText("Thread is interrupted");
					alert.showAndWait();
					return;
				}
			}
		});
//		Thread checker = new Thread(() -> {
//			while (threadRunning) {
//				try {
//					Thread.sleep(1000);
//					System.out.println("Thread working");
//					System.out.print("Main.users.size()");
//					System.out.println(Main.users.size());
//					System.out.print("Main.rooms.size()");
//					System.out.println(Main.rooms.size());
//					System.out.print("Main.patients.size()");
//					System.out.println(Main.patients.size());
//					System.out.print("users.size()");
//					System.out.println(FileManagement.userSize());
//					System.out.print("rooms.size()");
//					System.out.println(FileManagement.roomSize());
//					System.out.print("patients.size()");
//					System.out.println(FileManagement.patientSize());
//				} catch (InterruptedException e) {
//					Alert alert = new Alert(AlertType.ERROR);
//					alert.setTitle("Thread Error");
//					alert.setContentText("Thread is interrupted");
//					alert.showAndWait();
//					return;
//				}
//			}
//		});
		updateThread.start();
		writerThread.start();
//		checker.start();

	}
}