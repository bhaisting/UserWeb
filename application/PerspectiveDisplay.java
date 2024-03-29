///////////////////////////////////////////////////////////////////////////////
// Assignment				 UserWeb
// Title:            GeneralDisplay.java
// Semester:         CS400 Fall 2019
//
// Author:           Ben Haisting, Kennedy Soehren, Yatharth Bindal, 
//									 Robert Bourguignon, Luke Vandenheuvel
// Email:            bhaisting@wisc.edu, ksoehren@wisc.edu, ybindal@wisc.edu, 
//									 bourguignon@wisc.edu, lvandenheuve@wisc.edu
// CS Login:         haisting, soehren, yatharth, bourguignon, vandenheuvel
// Lecturer's Name:  Debra Deppeler
//
// Description: PerspectiveDisplay shows the display from a focused perspective
//							of one of the users in the UserWeb. It allows users to add 
//							friends and remove friends for that user, check mutual friends,
//							and other functions.
//
//////////////////////////// 80 columns wide //////////////////////////////////
package application;

import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Display for the focused perspective. Edits the mainStage to create an
 * interactable user interface
 */
public class PerspectiveDisplay {
	private UserNetwork network;
	private Stage mainStage;
	private Stage popupStage;
	private Button add_user, add_friend, mutual_friends, remove_friend,
			remove_self, back_to_general, exit_button;

	/**
	 * Constructor, initializes all variables and sets up buttons with text
	 * 
	 * @param stage - the mainStage
	 * @param net   - the network
	 */
	public PerspectiveDisplay(Stage stage, UserNetwork net) {
		network = net;
		mainStage = stage;
		add_user = new Button("Add User");
		add_friend = new Button("Add Friendship");
		add_friend.setWrapText(true);
		add_friend.setTextAlignment(TextAlignment.CENTER);
		mutual_friends = new Button("Mutual Friends");
		mutual_friends.setWrapText(true);
		mutual_friends.setTextAlignment(TextAlignment.CENTER);
		remove_friend = new Button("Remove Friendship");
		remove_friend.setWrapText(true);
		remove_friend.setTextAlignment(TextAlignment.CENTER);
		remove_self = new Button("Remove Self");
		back_to_general = new Button("Back to General");
		back_to_general.setWrapText(true);
		back_to_general.setTextAlignment(TextAlignment.CENTER);
		exit_button = new Button("Exit");
	}

	/**
	 * gets the perspective screen pane with all items that will always be on the
	 * screen for a perspective display
	 * 
	 * @return pane containing the perspective screen
	 */
	public Pane getPerspectiveScreen() {
		Pane root = new Pane();
		root.setPrefSize(800, 600);

		// Creates the title label
		Label title = new Label(
				Main.perspectivePerson.getUsername() + "'s Perspective");
		title.relocate(300, 20);
		title.setFont(Main.bigFont);
		root.getChildren().add(title);

		// Creates the label for status
		Label status = new Label("Status: " + Main.externalInteractor.status);
		status.setMinWidth(400);
		status.setAlignment(Pos.CENTER);
		status.setFont(Main.medFont);
		status.relocate(500, 500);
		root.getChildren().add(status);

		// All buttons are placed, given their size, and added to the pane
		exit_button.relocate(650, 20);
		exit_button.setPrefSize(125, 50);
		root.getChildren().add(exit_button);

		add_user.relocate(50, 530);
		add_user.setPrefSize(100, 50);
		root.getChildren().add(add_user);

		add_friend.relocate(170, 530);
		add_friend.setPrefSize(100, 50);
		root.getChildren().add(add_friend);

		mutual_friends.relocate(290, 530);
		mutual_friends.setPrefSize(100, 50);
		root.getChildren().add(mutual_friends);

		remove_friend.relocate(410, 530);
		remove_friend.setPrefSize(100, 50);
		root.getChildren().add(remove_friend);

		remove_self.relocate(530, 530);
		remove_self.setPrefSize(100, 50);
		root.getChildren().add(remove_self);

		back_to_general.relocate(650, 530);
		back_to_general.setPrefSize(100, 50);
		root.getChildren().add(back_to_general);

		// display the scrollpane of all users with hyperlinks allowing the user to
		// click to a certain perspective
		ScrollPane allUsers = new ScrollPane();
		allUsers.setPrefSize(110, 500);
		VBox users = new VBox();
		Label userLabel = new Label("All Users");
		userLabel.setFont(new Font("Arial", 14));
		users.getChildren().add(userLabel);
		Label numUsers = new Label("Count: " + network.getUserList().size());
		numUsers.setFont(Main.medFont);
		users.getChildren().add(numUsers);
		for (UserNode node : network.getUserList()) {
			Hyperlink link = new Hyperlink(node.getUsername());
			link.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) { // if clicked, change perspectives
					Main.perspective = true;
					Main.perspectivePerson = network.getUser(node.getUsername());
					Main.externalInteractor
							.updateLog("s " + Main.perspectivePerson.getUsername());
					mainStage.setScene(
							new Scene(Main.perspectiveDisplay.getPerspectiveScreen(),
									Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
				}
			});
			users.getChildren().add(link);
		}
		allUsers.setContent(users);
		allUsers.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		allUsers.setPannable(true);
		root.getChildren().add(allUsers);

		// display the scrollpane of all friends with hyperlinks allowing the user
		// to click to a certain perspective
		ScrollPane allFriends = new ScrollPane();
		allFriends.setPrefSize(110, 400);
		allFriends.relocate(690, 90);
		VBox friends = new VBox();
		Label friendLabel = new Label("Friends");
		friendLabel.setFont(Main.medFont);
		friends.getChildren().add(friendLabel);
		Label numFriends = new Label(
				"Count: " + Main.perspectivePerson.getFriendList().size());
		numFriends.setFont(Main.medFont);
		friends.getChildren().add(numFriends);
		for (UserNode node : Main.perspectivePerson.getFriendList()) {
			Hyperlink link = new Hyperlink(node.getUsername());
			link.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) { // if clicked, change perspectives
					Main.perspective = true;
					Main.perspectivePerson = network.getUser(node.getUsername());
					Main.externalInteractor
							.updateLog("s " + Main.perspectivePerson.getUsername());
					mainStage.setScene(
							new Scene(Main.perspectiveDisplay.getPerspectiveScreen(),
									Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
				}
			});
			friends.getChildren().add(link);
		}
		allFriends.setContent(friends);
		allFriends.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		allFriends.setPannable(true);

		root.getChildren().add(allFriends);
		buttonHandler();
		return root;
	}

	/**
	 * Handles all cases where buttons are pressed
	 */
	public void buttonHandler() {
		add_user.setOnAction(event -> {
			get1ArgPane("Input the name of the new user, then press enter", 0);
		});

		add_friend.setOnAction(event -> {
			get1ArgPane("Input the name of your new friend, then press enter", 1);
		});

		mutual_friends.setOnAction(event -> {
			get1ArgPane("Input the name of the user, then press enter", 2);
		});

		remove_friend.setOnAction(event -> {
			get1ArgPane("Input the name of your (now) enemy, then press enter", 3);
		});

		remove_self.setOnAction(event -> {
			try { // throws an error, but fixes itself if the error is just caught
				network.deleteUser(Main.perspectivePerson.getUsername());
			} catch (Exception e) {
			}
			Main.perspective = false;
			mainStage.setScene(new Scene(Main.generalDisplay.getGeneralScreen(),
					Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));

		});

		back_to_general.setOnAction(event -> { // switches to general perspective
			Main.perspective = false;
			mainStage.setScene(new Scene(Main.generalDisplay.getGeneralScreen(),
					Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
		});

		exit_button.setOnAction(event -> {
			Main.externalInteractor
					.updateLog("r " + Main.perspectivePerson.getUsername());
			Main.exit();
		});
	}

	/**
	 * Opens up a popup window with one textfield, then handles what to do with
	 * the input given the commandType
	 * 
	 * @param labelText   - Text to be displayed on the title label
	 * @param commandType - Different based on what button was pressed
	 */
	private void get1ArgPane(String labelText, int commandType) {
		popupStage = new Stage();
		Pane root = new Pane();
		root.setPrefSize(200, 200);

		Label label = new Label(labelText);
		label.setFont(Main.medFont);
		label.setWrapText(true);
		label.setPrefWidth(200);
		label.setTextAlignment(TextAlignment.CENTER);

		TextField textBox = new TextField();
		textBox.relocate(25, 100);
		textBox.setPrefWidth(150);

		Button button = new Button("Enter");
		button.relocate(50, 150);
		button.setPrefSize(100, 25);
		button.setOnAction(event -> {
			switch (commandType) {

			case 0: // Add user case
				if (Main.validateInput(textBox.getText())) { // check input
					network.createUser(textBox.getText());
					Main.externalInteractor.updateLog("a " + textBox.getText());
					mainStage.setScene(new Scene(getPerspectiveScreen(),
							Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
				}
				break;

			case 1: // Add friend case
				if (Main.validateInput(textBox.getText())) { // check input
					network.setFriend(Main.perspectivePerson.getUsername(),
							textBox.getText());
					Main.externalInteractor.updateLog("a "
							+ Main.perspectivePerson.getUsername() + " " + textBox.getText());
					mainStage.setScene(new Scene(getPerspectiveScreen(),
							Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
				}
				break;

			case 2: // Mutual friend case
				if (Main.validateInput(textBox.getText())) { // check input
					Pane mainRoot = getPerspectiveScreen();
					LinkedList<UserNode> list = network
							.getMutualFriends(Main.perspectivePerson, textBox.getText());
					Label text = new Label();
					text.setAlignment(Pos.CENTER);
					text.setMinWidth(200);
					text.setFont(Main.medFont);
					text.setStyle("-fx-font-weight: bold");
					mainRoot.getChildren().add(text);

					if (list == null) { // Name not found case
						text.setText("The name you entered does not exist in the network");
						text.relocate(210, 150);
					} else if (list.size() == 0) { // No mutual friends case
						text.setText("No mutual friends found");
						text.relocate(300, 150);
					} else { // Mutual friends found case
						String s = "";
						for (UserNode i : list) {
							s += i.getUsername() + "\n";
						}
						text.setText("Mutual friends found:\n" + s);
						text.relocate(290, 150);
					}
					mainStage.setScene(
							new Scene(mainRoot, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
				} else {
					mainStage.setScene(new Scene(getPerspectiveScreen(),
							Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
				}
				break;

			case 3: // Remove friend case
				if (network.deleteFriend(Main.perspectivePerson.getUsername(),
						textBox.getText())) { // if friend is actually removed
					Main.externalInteractor.updateLog("r "
							+ Main.perspectivePerson.getUsername() + " " + textBox.getText());
				}
				mainStage.setScene(new Scene(getPerspectiveScreen(), Main.WINDOW_WIDTH,
						Main.WINDOW_HEIGHT));
				break;
			}
			popupStage.close();
		});

		root.getChildren().add(label);
		root.getChildren().add(textBox);
		root.getChildren().add(button);
		popupStage.setScene(new Scene(root, 200, 200));
		popupStage.show();
	}
}