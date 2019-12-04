package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	private static final String APP_TITLE = "UserWeb";
	public static boolean perspective = false;
	public static UserNode perspectivePerson = new UserNode("");
	private static UserNetwork userNetwork = new UserNetwork();
	private static GeneralDisplay generalDisplay = new GeneralDisplay(
			userNetwork);
	private static PerspectiveDisplay perspectiveDisplay = new PerspectiveDisplay(
			userNetwork);
	public static ExternalInteractor externalInteractor = new ExternalInteractor(
			userNetwork);

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root; // Contains the current image as well as button responses
		if (!perspective) { // General Display
			root = generalDisplay.getGeneralScreen();
		} else { // Perspective Display
			root = perspectiveDisplay.getPerspectiveScreen();
		}
		Scene display = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(display);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
