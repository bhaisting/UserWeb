package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 800;
	private static final String APP_TITLE = "UserWeb";
	public static boolean perspective = false;

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setCenter(new VBox());
		root.setTop(new Label("Hi"));
		Scene display = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		if(perspective) {
			
		}else {
			
		}
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(display);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
