package application;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GeneralDisplay {
	private UserNetwork network;
	private Stage mainStage;
	private Stage popupStage;
	private Button data_entry, add_user, add_friend, visualize_network,
			shortest_path, remove_friend, remove_user, exit_button;

	public GeneralDisplay(Stage stage, UserNetwork net) {
		network = net;
		mainStage = stage;
		// popupStage = new Stage();
		data_entry = new Button("Import Data File");
		add_user = new Button("Add User");
		add_friend = new Button("Add Friendship");
		visualize_network = new Button("Visualize Network");
		visualize_network.setWrapText(true);
		visualize_network.setTextAlignment(TextAlignment.CENTER);
		shortest_path = new Button("Shortest Path");
		remove_friend = new Button("Remove Friendship");
		remove_friend.setWrapText(true);
		remove_friend.setTextAlignment(TextAlignment.CENTER);
		remove_user = new Button("Remove User");
		exit_button = new Button("Exit");
	}

	public Pane getGeneralScreen() {
		Pane root = new Pane();

		Label title = new Label("General Perspective");
		title.relocate(320, 20);
		title.setFont(Main.bigFont);
		root.getChildren().add(title);

		root.setPrefSize(800, 600);
		root.getChildren().add(data_entry);
		data_entry.relocate(650, 20);
		data_entry.setPrefSize(125, 50);
		add_user.relocate(50, 530);
		add_user.setPrefSize(100, 50);
		root.getChildren().add(add_user);
		add_friend.relocate(170, 530);
		add_friend.setPrefSize(100, 50);
		root.getChildren().add(add_friend);
		visualize_network.relocate(290, 530);
		visualize_network.setPrefSize(100, 50);
		root.getChildren().add(visualize_network);
		shortest_path.relocate(410, 530);
		shortest_path.setPrefSize(100, 50);
		root.getChildren().add(shortest_path);
		remove_friend.relocate(530, 530);
		remove_friend.setPrefSize(100, 50);
		root.getChildren().add(remove_friend);
		remove_user.relocate(650, 530);
		remove_user.setPrefSize(100, 50);
		root.getChildren().add(remove_user);
		ScrollPane allUsers = new ScrollPane();
		allUsers.setPrefSize(110, 500);

		VBox users = new VBox();
		Label userLabel = new Label("All Users");
		userLabel.setFont(Main.medFont);
		users.getChildren().add(userLabel);
		for (UserNode node : network.getUserList()) {
			Hyperlink link = new Hyperlink(node.getUsername());
			link.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					Main.perspective=true;
					Main.perspectivePerson = network.getUser(node.getUsername());
					mainStage.setScene(new Scene(Main.perspectiveDisplay.getPerspectiveScreen(),Main.WINDOW_WIDTH,Main.WINDOW_HEIGHT));
				}
			});
			users.getChildren().add(link);
		}
		allUsers.setContent(users);
		allUsers.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		allUsers.setPannable(true);

		root.getChildren().add(allUsers);
		buttonHandler(root);
		return root;
	}

	public void buttonHandler(Pane root) {
		add_user.setOnAction(event -> {
			get1ArgPane("Input the name of the new user, then press enter", 0);
		});

		add_friend.setOnAction(event -> {
			get2ArgPane("Input the name of the friends, then press enter", 0);
		});

		visualize_network.setOnAction(event -> {
			String tempVisual = "";
			for (UserNode i : network.getUserList()) {
				tempVisual += i.getUsername() + " -> ";
				for (UserNode j : i.getFriendList()) {
					tempVisual += j.getUsername() + ", ";
				}
				tempVisual += "\n";
			}
			Label visual = new Label(tempVisual);
			visual.setFont(new Font("Arial", 16));
			visual.relocate(300, 150);
			root.getChildren().add(visual);
			System.out.println("Success!");
		});

		shortest_path.setOnAction(event -> {
			get2ArgPane("Input the name of the two users, then press enter", 1);
		});
		remove_friend.setOnAction(event -> {
			get2ArgPane("Input the name of the two (now) enemies, then press enter",
					2);
		});
		remove_user.setOnAction(event -> {
			get1ArgPane("Input the name of user to be removed, then press enter", 1);
		});
		data_entry.setOnAction(event -> {
			get1ArgPane("Input the file name here, then press enter", 2);
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
			case 1: // Remove user case
				network.deleteUser(textBox.getText());
				break;
			case 2: // Data entry case
				Main.externalInteractor.load(textBox.getText());
				break;
			}
			popupStage.close();
			mainStage.setScene(
					new Scene(getGeneralScreen(), Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
		});

		root.getChildren().add(label);
		root.getChildren().add(textBox);
		root.getChildren().add(button);
		popupStage.setScene(new Scene(root, 200, 200));
		popupStage.show();
	}

	private void get2ArgPane(String labelText, int commandType) {
		popupStage = new Stage();
		Pane root = new Pane();
		root.setPrefSize(200, 200);

		Label label = new Label(labelText);
		label.setFont(Main.medFont);
		label.setWrapText(true);
		label.setPrefWidth(200);
		label.setTextAlignment(TextAlignment.CENTER);

		TextField textBox1 = new TextField();
		textBox1.relocate(25, 60);
		textBox1.setPrefWidth(150);

		TextField textBox2 = new TextField();
		textBox2.relocate(25, 110);
		textBox2.setPrefWidth(150);

		Button button = new Button("Enter");
		button.relocate(50, 150);
		button.setPrefSize(100, 25);
		button.setOnAction(event -> {
			switch (commandType) {
			case 0: // Add friend case
				network.setFriend(textBox1.getText(), textBox2.getText());
				break;
			case 1: // Shortest path case
				// STILL NEEDS TO BE IMPLEMENTED
				break;
			case 2: // Remove friend case
				network.deleteFriend(textBox1.getText(), textBox2.getText());
				break;
			}

			popupStage.close();
			mainStage.setScene(
					new Scene(getGeneralScreen(), Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
		});

		root.getChildren().add(label);
		root.getChildren().add(textBox1);
		root.getChildren().add(textBox2);
		root.getChildren().add(button);
		popupStage.setScene(new Scene(root, 200, 200));
		popupStage.show();
	}
}
