package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

public class GeneralDisplay {
	private UserNetwork network;
	private Button test_button;

	public GeneralDisplay(UserNetwork net) {
		network = net;
		test_button=new Button("Test");
	}
	
	public Pane getGeneralScreen() {
		Pane root = new Pane();
		test_button.relocate(5,5);
		test_button.setPrefSize(200,100);
		root.getChildren().add(test_button);
		buttonHandler();
		return root;
	}
	
	public void buttonHandler() {
		test_button.setOnAction(event -> {
			System.out.println("Success!");
		});
	}
}
