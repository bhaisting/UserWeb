///////////////////////////////////////////////////////////////////////////////
// Assignment				 UserWeb
// Title:            Main.java
// Semester:         CS400 Fall 2019
//
// Author:           Ben Haisting, Kennedy Soehren, Yatharth Bindal, 
//									 Robert Bourguignon, Luke Vandenheuvel
// Email:            bhaisting@wisc.edu, ksoehren@wisc.edu, ybindal@wisc.edu, 
//									 bourguignon@wisc.edu, lvandenheuve@wisc.edu
// CS Login:         haisting, soehren, yatharth, bourguignon, vandenheuvel
// Lecturer's Name:  Debra Deppeler
//
// Description: Main class starts the JavaFX program and carries the global 
//							variables so that any other class can use them. It also contains
//							the exit menu method as well as a method for validating input.
//
//////////////////////////// 80 columns wide //////////////////////////////////
package application;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Runner class for the UserWeb program. Initializes JavaFX along with global
 * variables that are used by other methods.
 */
public class Main extends Application {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	private static final String APP_TITLE = "UserWeb";
	public static boolean perspective = false;
	public static UserNode perspectivePerson = new UserNode("");
	public static UserNetwork userNetwork = new UserNetwork();
	public static GeneralDisplay generalDisplay;
	public static PerspectiveDisplay perspectiveDisplay;
	public static ExternalInteractor externalInteractor = new ExternalInteractor(
			userNetwork);
	public static final Font bigFont = new Font("Arial", 24);
	public static final Font medFont = new Font("Arial", 16);
	private static Stage mainStage;
	private static final String validChars = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,"
			+ "r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,"
			+ "0,1,2,3,4,5,6,7,8,9, ,_,'"; // all valid characters
	private static ArrayList<String> validCharList = new ArrayList<String>(
			Arrays.asList(validChars.split(",")));

	/**
	 * 
	 * 
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		generalDisplay = new GeneralDisplay(primaryStage, userNetwork);
		perspectiveDisplay = new PerspectiveDisplay(primaryStage, userNetwork);
		Pane root; // Contains the current image as well as button responses
		if (!perspective) { // General Display
			root = generalDisplay.getGeneralScreen();
		} else { // Perspective Display
			root = perspectiveDisplay.getPerspectiveScreen();
		}
		Scene display = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(display);
		primaryStage.show();
	}

	/**
	 * Exits the program and prompts the user on if they'd like to save
	 */
	public static void exit() {
		Pane root = new Pane();
		root.setPrefSize(300, 300);

		// title label, prompts user if they'd like to save
		Label label = new Label(
				"Would you like to save? Input the file name below!");
		label.setWrapText(true);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setFont(medFont);
		label.setMaxWidth(300);
		root.getChildren().add(label);

		// input location for text file
		TextField textBox = new TextField("log.txt");
		textBox.relocate(50, 100);
		textBox.setPrefWidth(200);
		root.getChildren().add(textBox);

		// will be used for a notification on if file saving worked
		Label status = new Label();
		status.setMinWidth(300);
		status.setAlignment(Pos.CENTER);
		status.relocate(0, 60);
		status.setFont(bigFont);

		Button save = new Button("Save");
		save.relocate(30, 220);
		save.setPrefSize(100, 50);
		root.getChildren().add(status);
		save.setOnAction(event -> { // if the user chooses to save
			Stage confirmation = new Stage(); // popup menu for confirmation
			Pane confirm = new Pane();
			confirm.setPrefSize(200, 200);

			Label confirmText = new Label("Are you sure you want to save?");
			confirmText.setMaxWidth(200);
			confirmText.setFont(medFont);
			confirmText.setWrapText(true);
			confirmText.setTextAlignment(TextAlignment.CENTER);
			confirm.getChildren().add(confirmText);

			Button yes = new Button("Yes");
			yes.setPrefSize(80, 40);
			yes.relocate(15, 140);
			yes.setOnAction(subEvent -> { // yes, I would like to save case
				if (externalInteractor.saveLog(textBox.getText())) {// checks that save
																														// was successful
					status.setText("Success! File saved!");
				} else {
					status.setText("An error occurred with the file name.");
				}
				confirmation.close();
			});
			confirm.getChildren().add(yes);

			Button no = new Button("No");
			no.setPrefSize(80, 40);
			no.relocate(105, 140);
			no.setOnAction(subEvent -> { // no I would not like to save case
				confirmation.close();
			});
			confirm.getChildren().add(no);

			confirmation.setScene(new Scene(confirm, 200, 200));
			confirmation.show();
		});
		root.getChildren().add(save);

		Button noSave = new Button("Don't Save");
		noSave.relocate(170, 220);
		noSave.setPrefSize(100, 50);
		noSave.setOnAction(event -> { // if the user chooses not to save
			Stage confirmation = new Stage(); // popup window
			Pane confirm = new Pane();
			confirm.setPrefSize(200, 200);

			Label confirmText = new Label("Are you sure you don't want to save?");
			confirmText.setMaxWidth(200);
			confirmText.setFont(medFont);
			confirmText.setWrapText(true);
			confirmText.setTextAlignment(TextAlignment.CENTER);
			confirm.getChildren().add(confirmText);

			Button yes = new Button("Yes");
			yes.setPrefSize(80, 40);
			yes.relocate(15, 140);
			yes.setOnAction(subEvent -> { // yes, I don't want to save case
				confirmation.close();
				mainStage.close();
				externalInteractor.saveLog(null); // needed to close the printwriter for
																					// log.txt
			});
			confirm.getChildren().add(yes);

			Button no = new Button("No");
			no.setPrefSize(80, 40);
			no.relocate(105, 140);
			no.setOnAction(subEvent -> { // no, I want to save case
				confirmation.close();
			});
			confirm.getChildren().add(no);

			confirmation.setScene(new Scene(confirm, 200, 200));
			confirmation.show();
		});
		root.getChildren().add(noSave);

		mainStage.setScene(new Scene(root, 300, 300));
	}

	/**
	 * Verifies that a string is only made of valid input
	 * 
	 * @param input - String being tested
	 * @return true if input is valid, false if input is NOT valid
	 */
	public static boolean validateInput(String input) {
		ArrayList<String> userInput = new ArrayList<String>(
				Arrays.asList(input.split("")));
		if (validCharList.containsAll(userInput)) {
			return true;
		}
		externalInteractor.status = "Invalid input";
		return false;
	}

	/**
	 * Launches the display
	 * 
	 * @param args - unused
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
