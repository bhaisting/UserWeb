package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;

public class GeneralDisplay {
    private UserNetwork network;
    private Button data_entry, add_user, add_friend, visualize_network, shortest_path,
        remove_friend, remove_user;

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


        VBox test = new VBox();
        test.getChildren().addAll(new Label("user1"), new Label("user2"), new Label("user3"),
            new Label("user4"), new Label("user5"), new Label("user6"), new Label("user7"));
        allUsers.setContent(test);
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
        data_entry.setOnAction(event -> {
            System.out.println("Success!");
        });
    }
}
