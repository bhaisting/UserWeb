package application;

import javafx.application.Application;
import javafx.scene.control.Button;
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
	private Button add_user,add_friend,mutual_friends,remove_friend,remove_self,back_to_general;

	public PerspectiveDisplay(UserNetwork net) {
		network = net;
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
		Label title = new Label(Main.perspectivePerson.getUsername()+"'s Perspective");
		title.relocate(300, 20);
		title.setFont(new Font("Arial", 20));
		root.getChildren().add(title);
		root.setPrefSize(800,600);
		
		add_user.relocate(50,530);
		add_user.setPrefSize(100,50);
		root.getChildren().add(add_user);
		
		add_friend.relocate(170,530);
		add_friend.setPrefSize(100,50);
		root.getChildren().add(add_friend);
		
		mutual_friends.relocate(290,530);
		mutual_friends.setPrefSize(100,50);
		root.getChildren().add(mutual_friends);
		
		remove_friend.relocate(410,530);
		remove_friend.setPrefSize(100,50);
		root.getChildren().add(remove_friend);
		
		remove_self.relocate(530,530);
		remove_self.setPrefSize(100,50);
		root.getChildren().add(remove_self);
		
		back_to_general.relocate(650,530);
		back_to_general.setPrefSize(100,50);
		root.getChildren().add(back_to_general);
		
		ScrollPane allUsers = new ScrollPane();
		allUsers.setPrefSize(110, 500);
		VBox users = new VBox();
		for (UserNode node : network.getUserList()) {
			users.getChildren().add(new Label(node.getUsername()));
		}
		allUsers.setContent(users);
		allUsers.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		allUsers.setPannable(true);
		root.getChildren().add(allUsers);
		
		ScrollPane allFriends = new ScrollPane();
		allFriends.setPrefSize(110, 500);
		allFriends.relocate(690,0);

		VBox friends = new VBox();
		for (UserNode node : Main.perspectivePerson.getFriendList()) {
			friends.getChildren().add(new Label(node.getUsername()));
		}
		allFriends.setContent(friends);
		allFriends.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		allFriends.setPannable(true);

		root.getChildren().add(allFriends);
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
		mutual_friends.setOnAction(event -> {
			System.out.println("Success!");
		});
		remove_friend.setOnAction(event -> {
			System.out.println("Success!");
		});
		remove_self.setOnAction(event -> {
			System.out.println("Success!");
		});
		back_to_general.setOnAction(event -> {
			System.out.println("Back to general!");
			Main.perspective=false;
		});
	}
}