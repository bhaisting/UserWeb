package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;

public class GeneralDisplay {
	private UserNetwork network;
	private Button data_entry, add_user, add_friend, visualize_network,
			shortest_path, remove_friend, remove_user;

	public GeneralDisplay(UserNetwork net) {
		network = net;
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
	}

	public Pane getGeneralScreen() {
		Pane root = new Pane();

		Label title = new Label("General Perspective");
		title.relocate(320, 20);
		title.setFont(new Font("Arial", 20));
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
		userLabel.setFont(new Font("Arial",14));
		users.getChildren().add(userLabel);
		for (UserNode node : network.getUserList()) {
			users.getChildren().add(new Label(node.getUsername()));
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
			TextField name = new TextField("Input name");
			name.relocate(300, 300);
			root.getChildren().add(name);
			System.out.println("Success!");
		});
		
		add_friend.setOnAction(event -> {
			TextField name1 = new TextField("Input name 1");
			name1.relocate(300, 250);
			TextField name2 = new TextField("Input name 2");
			name2.relocate(300, 350);
			root.getChildren().add(name1);
			root.getChildren().add(name2);
			System.out.println("Success!");
		});

		visualize_network.setOnAction(event -> {
			String tempVisual = "";
			for (UserNode i : network.getUserList()) {
				tempVisual += i.getUsername() + " -> ";
				for (UserNode j : i.getFriendList()) {
					tempVisual += j.getUsername()+", ";
				}
				tempVisual+="\n";
			}
			Label visual = new Label(tempVisual);
			visual.setFont(new Font("Arial",16));
			visual.relocate(300,150);
			root.getChildren().add(visual);
			System.out.println("Success!");
		});
		
		shortest_path.setOnAction(event -> {
			TextField name1 = new TextField("Input name 1");
			name1.relocate(300, 250);
			TextField name2 = new TextField("Input name 2");
			name2.relocate(300, 350);
			root.getChildren().add(name1);
			root.getChildren().add(name2);
		});
		remove_friend.setOnAction(event -> {
			TextField name1 = new TextField("Input name 1");
			name1.relocate(300, 250);
			TextField name2 = new TextField("Input name 2");
			name2.relocate(300, 350);
			root.getChildren().add(name1);
			root.getChildren().add(name2);
		});
		remove_user.setOnAction(event -> {
			TextField name = new TextField("Input name");
			name.relocate(300, 300);
			root.getChildren().add(name);
		});
		data_entry.setOnAction(event -> {
			TextField dataEntry = new TextField("Input filename here");
			dataEntry.relocate(300,300);
			root.getChildren().add(dataEntry);
		});
	}
}
