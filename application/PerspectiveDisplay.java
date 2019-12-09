package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PerspectiveDisplay {
	private UserNetwork network;
	private Stage mainStage;
	private Stage popupStage;
	private Button add_user, add_friend, mutual_friends, remove_friend,
			remove_self, back_to_general, exit_button;

	public PerspectiveDisplay(Stage stage, UserNetwork net) {
		network = net;
		mainStage = stage;
		add_user = new Button("Add User");
		add_friend = new Button("Add Friendship");
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
	}

	public Pane getPerspectiveScreen() {
		Pane root = new Pane();
		Label title = new Label(
				Main.perspectivePerson.getUsername() + "'s Perspective");
		title.relocate(300, 20);
		title.setFont(Main.bigFont);
		root.getChildren().add(title);
		root.setPrefSize(800, 600);

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

		ScrollPane allUsers = new ScrollPane();
		allUsers.setPrefSize(110, 500);

		VBox users = new VBox();
		Label userLabel = new Label("All Users");
		userLabel.setFont(new Font("Arial", 14));
		users.getChildren().add(userLabel);
		for (UserNode node : network.getUserList()) {
			Hyperlink link = new Hyperlink(node.getUsername());
			link.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					Main.perspective = true;
					Main.perspectivePerson = network.getUser(node.getUsername());
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

		ScrollPane allFriends = new ScrollPane();
		allFriends.setPrefSize(110, 500);
		allFriends.relocate(690, 0);

		VBox friends = new VBox();
		Label friendLabel = new Label("Friends");
		friendLabel.setFont(Main.medFont);
		friends.getChildren().add(friendLabel);
		for (UserNode node : Main.perspectivePerson.getFriendList()) {
			Hyperlink link = new Hyperlink(node.getUsername());
			link.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					Main.perspective = true;
					Main.perspectivePerson = network.getUser(node.getUsername());
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
		buttonHandler(root);
		return root;
	}

	/**
	 * 
	 * @param root
	 */
	public void buttonHandler(Pane root) {
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
		remove_self.setOnAction(event -> {// TROUBLESHOOT THIS, IT WORKS BUT I DON'T
																			// KNOW WHY
			System.out.println("Success!");
			try {
				network.deleteUser(Main.perspectivePerson.getUsername());
			} catch (Exception e) {
				network.deleteUser(Main.perspectivePerson.getUsername());
			}
			Main.perspective = false;
			mainStage.setScene(new Scene(Main.generalDisplay.getGeneralScreen(),
					Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));

		});
		back_to_general.setOnAction(event -> {
			System.out.println("Back to general!");
			Main.perspective = false;
			mainStage.setScene(new Scene(Main.generalDisplay.getGeneralScreen(),
					Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
		});
	}

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
				network.createUser(textBox.getText());
				break;
			case 1: // Add friend case
				network.setFriend(Main.perspectivePerson.getUsername(),
						textBox.getText());
				break;
			case 2: // Mutual friend case NEEDS TO BE IMPLEMENTED
				break;
			case 3: // Remove friend case
				network.deleteFriend(Main.perspectivePerson.getUsername(),
						textBox.getText());
				break;
			}
			popupStage.close();
			mainStage.setScene(new Scene(getPerspectiveScreen(), Main.WINDOW_WIDTH,
					Main.WINDOW_HEIGHT));
		});

		root.getChildren().add(label);
		root.getChildren().add(textBox);
		root.getChildren().add(button);
		popupStage.setScene(new Scene(root, 200, 200));
		popupStage.show();
	}
}