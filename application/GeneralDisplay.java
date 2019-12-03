package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;

public class GeneralDisplay {
	private UserNetwork network;
	private Button add_user,add_friend,visualize_network,shortest_path,remove_friend,remove_user;

	public GeneralDisplay(UserNetwork net) {
		network = net;
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
		/*Label title = new Label("General Perspective");
		title.relocate(400,50);
		title.setFont(new Font("Arial",32));
		root.getChildren().add(title);*/
		root.setPrefSize(800,600);
		add_user.relocate(50,530);
		add_user.setPrefSize(100,50);
		root.getChildren().add(add_user);
		add_friend.relocate(170,530);
		add_friend.setPrefSize(100,50);
		root.getChildren().add(add_friend);	
		visualize_network.relocate(290,530);
		visualize_network.setPrefSize(100,50);
		root.getChildren().add(visualize_network);
		shortest_path.relocate(410,530);
		shortest_path.setPrefSize(100,50);
		root.getChildren().add(shortest_path);
		remove_friend.relocate(530,530);
		remove_friend.setPrefSize(100,50);
		root.getChildren().add(remove_friend);
		remove_user.relocate(650,530);
		remove_user.setPrefSize(100,50);
		root.getChildren().add(remove_user);
		ScrollPane allUsers = new ScrollPane();
		buttonHandler(root);
		return root;
	}
	
	public void buttonHandler(Pane root) {
		add_user.setOnAction(event -> {
			TextField name = new TextField("Input name");
			root.getChildren().add(name);
			System.out.println("Success!");
		});
		add_friend.setOnAction(event -> {
			System.out.println("Success!");
		});
		visualize_network.setOnAction(event -> {
			System.out.println("Success!");
		});
		shortest_path.setOnAction(event -> {
			System.out.println("Success!");
		});
		remove_friend.setOnAction(event -> {
			System.out.println("Success!");
		});
		remove_user.setOnAction(event -> {
			System.out.println("Success!");
		});
	}
}
