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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GeneralDisplay {
	private UserNetwork network;
	private Stage mainStage;
	private Stage popupStage;
	private Button data_entry, add_user, add_friend, visualize_network, shortest_path, remove_friend, remove_user,
			exit_button, clear_network;

	public GeneralDisplay(Stage stage, UserNetwork net) {
		network = net;
		mainStage = stage;
		// popupStage = new Stage();
		data_entry = new Button("Import Data File");
		data_entry.setWrapText(true);
		data_entry.setTextAlignment(TextAlignment.CENTER);
		add_user = new Button("Add User");
		add_friend = new Button("Add Friendship");
		add_friend.setWrapText(true);
		add_friend.setTextAlignment(TextAlignment.CENTER);
		visualize_network = new Button("Visualize Network");
		visualize_network.setWrapText(true);
		visualize_network.setTextAlignment(TextAlignment.CENTER);
		shortest_path = new Button("Shortest Path");
		shortest_path.setWrapText(true);
		shortest_path.setTextAlignment(TextAlignment.CENTER);
		remove_friend = new Button("Remove Friendship");
		remove_friend.setWrapText(true);
		remove_friend.setTextAlignment(TextAlignment.CENTER);
		remove_user = new Button("Remove User");
		exit_button = new Button("Exit");
		clear_network = new Button("Clear Network");
		clear_network.setWrapText(true);
		clear_network.setTextAlignment(TextAlignment.CENTER);
	}

	public Pane getGeneralScreen() {
		Pane root = new Pane();
		root.setPrefSize(800, 600);

		// Creates the title label
		Label title = new Label("General Perspective");
		title.relocate(320, 20);
		title.setFont(Main.bigFont);
		root.getChildren().add(title);

		// Creates the labels for number of groups
		Label numGroups = new Label("Number of groups:");
		numGroups.setFont(Main.medFont);
		numGroups.relocate(140, 20);
		root.getChildren().add(numGroups);

		Label number = new Label(Integer.toString(network.getNumGroups()));
		number.setMinWidth(100);
		number.setAlignment(Pos.CENTER);
		number.setFont(Main.bigFont);
		number.relocate(155, 40);
		root.getChildren().add(number);

		// All buttons are placed, given their size, and added to the pane
		clear_network.relocate(570, 20);
		clear_network.setPrefSize(100, 50);
		root.getChildren().add(clear_network);

		exit_button.relocate(680, 20);
		exit_button.setPrefSize(100, 50);
		root.getChildren().add(exit_button);

		add_user.relocate(20, 530);
		add_user.setPrefSize(100, 50);
		root.getChildren().add(add_user);

		add_friend.relocate(130, 530);
		add_friend.setPrefSize(100, 50);
		root.getChildren().add(add_friend);

		visualize_network.relocate(240, 530);
		visualize_network.setPrefSize(100, 50);
		root.getChildren().add(visualize_network);

		shortest_path.relocate(350, 530);
		shortest_path.setPrefSize(100, 50);
		root.getChildren().add(shortest_path);

		remove_friend.relocate(460, 530);
		remove_friend.setPrefSize(100, 50);
		root.getChildren().add(remove_friend);

		remove_user.relocate(570, 530);
		remove_user.setPrefSize(100, 50);
		root.getChildren().add(remove_user);

		data_entry.relocate(680, 530);
		data_entry.setPrefSize(100, 50);
		root.getChildren().add(data_entry);

		ScrollPane allUsers = new ScrollPane();
		allUsers.setPrefSize(110, 500);
		VBox users = new VBox();
		Label userLabel = new Label("All Users");
		userLabel.setFont(Main.medFont);
		users.getChildren().add(userLabel);
		Label numUsers = new Label("Count: " + network.getUserList().size());
		numUsers.setFont(Main.medFont);
		users.getChildren().add(numUsers);
		for (UserNode node : network.getUserList()) {
			Hyperlink link = new Hyperlink(node.getUsername());
			link.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					Main.perspective = true;
					Main.perspectivePerson = network.getUser(node.getUsername());
					Main.externalInteractor.updateLog("s " + Main.perspectivePerson.getUsername());
					mainStage.setScene(new Scene(Main.perspectiveDisplay.getPerspectiveScreen(), Main.WINDOW_WIDTH,
							Main.WINDOW_HEIGHT));
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
			createVisual();
		});

		shortest_path.setOnAction(event -> {
			get2ArgPane("Input the name of the two users, then press enter", 1);
		});

		remove_friend.setOnAction(event -> {
			get2ArgPane("Input the name of the two (now) enemies, then press enter", 2);
		});

		remove_user.setOnAction(event -> {
			get1ArgPane("Input the name of user to be removed, then press enter", 1);
		});

		data_entry.setOnAction(event -> {
			get1ArgPane("Input the file name here, then press enter", 2);
		});

		exit_button.setOnAction(event -> {
			Main.exit();
		});

		clear_network.setOnAction(event -> {
			network.getUserList().clear();
			Main.externalInteractor.clearNetwork();
			mainStage.setScene(new Scene(getGeneralScreen(), Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
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
				Main.externalInteractor.updateLog("a " + textBox.getText());
				mainStage.setScene(new Scene(getGeneralScreen(), Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
				break;
			case 1: // Remove user case
				if (network.deleteUser(textBox.getText())) {
					Main.externalInteractor.updateLog("r " + textBox.getText());
				}
				mainStage.setScene(new Scene(getGeneralScreen(), Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
				break;
			case 2: // Data entry case
				// if the command to change perspectives is given, change perspectives
				if (Main.externalInteractor.load(textBox.getText()) != null) {
					mainStage.setScene(new Scene(Main.perspectiveDisplay.getPerspectiveScreen(), Main.WINDOW_WIDTH,
							Main.WINDOW_HEIGHT));
				} else {
					mainStage.setScene(new Scene(getGeneralScreen(), Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
				}
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
				Main.externalInteractor.updateLog("a " + textBox1.getText() + " " + textBox2.getText());
				break;
			case 1: // Shortest path case
				// STILL NEEDS TO BE IMPLEMENTED
				break;
			case 2: // Remove friend case
				if (network.deleteFriend(textBox1.getText(), textBox2.getText())) {
					Main.externalInteractor.updateLog("r " + textBox1.getText() + " " + textBox2.getText());
				}
				break;
			}

			popupStage.close();
			mainStage.setScene(new Scene(getGeneralScreen(), Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
		});

		root.getChildren().add(label);
		root.getChildren().add(textBox1);
		root.getChildren().add(textBox2);
		root.getChildren().add(button);
		popupStage.setScene(new Scene(root, 200, 200));
		popupStage.show();
	}

	/**
	 * Works with up to 20-25 people with the current system. Creates an oval of
	 * nodes and then connects them with line segments when they are friends
	 */
	private void createVisual() {
		Pane root = getGeneralScreen();
		LinkedList<UserNode> net = network.getUserList();
		LinkedList<Circle> circles = new LinkedList<Circle>();
		LinkedList<Label> labels = new LinkedList<Label>();
		for (int i = 0; i < net.size(); i++) {
			UserNode user = net.get(i);
			double frac = (double) i / net.size();
			int xpos = (int) (450 + 300 * Math.cos(frac * 2 * Math.PI));
			int ypos = (int) (290 + 200 * Math.sin(frac * 2 * Math.PI));
			Circle circle = new Circle(xpos, ypos, 25);
			circle.setFill(Color.CYAN);
			circles.add(circle);
			Label label = new Label(user.getUsername());
			label.setMinWidth(50);
			label.relocate(xpos - 25, ypos - 10);
			label.setAlignment(Pos.CENTER);
			labels.add(label);
			for (int j = i + 1; j < net.size(); j++) {
				UserNode friend = net.get(j);
				if (user.getFriendList().contains(friend)) {
					double frac2 = (double) j / net.size();
					int xpos2 = (int) (450 + 300 * Math.cos(frac2 * 2 * Math.PI));
					int ypos2 = (int) (290 + 200 * Math.sin(frac2 * 2 * Math.PI));
					Line line = new Line(xpos, ypos, xpos2, ypos2);
					root.getChildren().add(line);
				}
			}
		}
		root.getChildren().addAll(circles);
		root.getChildren().addAll(labels);
		mainStage.setScene(new Scene(root, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
	}
}
