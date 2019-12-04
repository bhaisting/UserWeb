package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;

public class PerspectiveDisplay {
	private UserNetwork network;
	private Button add_user,add_friend,mutual_friends,remove_friend,remove_self;

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
	}
	
	public Pane getPerspectiveScreen() {
		Pane root = new Pane();
		/*Label title = new Label("General Perspective");
		title.relocate(400,50);
		title.setFont(new Font("Arial",32));
		root.getChildren().add(title);*/
		root.setPrefSize(800,600);
		
		add_user.relocate(50,530);
		add_user.setPrefSize(100,50);
		root.getChildren().add(add_user);
		
		add_friend.relocate(200,530);
		add_friend.setPrefSize(100,50);
		root.getChildren().add(add_friend);
		
		mutual_friends.relocate(350,530);
		mutual_friends.setPrefSize(100,50);
		root.getChildren().add(mutual_friends);
		
		remove_friend.relocate(500,530);
		remove_friend.setPrefSize(100,50);
		root.getChildren().add(remove_friend);
		
		remove_self.relocate(650,530);
		remove_self.setPrefSize(100,50);
		root.getChildren().add(remove_self);
		
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
		mutual_friends.setOnAction(event -> {
			System.out.println("Success!");
		});
		remove_friend.setOnAction(event -> {
			System.out.println("Success!");
		});
		remove_self.setOnAction(event -> {
			System.out.println("Success!");
		});
	}
}