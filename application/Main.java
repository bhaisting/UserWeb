package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	private static final String APP_TITLE = "UserWeb";
	public static boolean perspective = false;
	public static UserNode perspectivePerson = new UserNode("");
	public static UserNetwork userNetwork = new UserNetwork();
	public static GeneralDisplay generalDisplay;
	public static PerspectiveDisplay perspectiveDisplay;
	public static ExternalInteractor externalInteractor = new ExternalInteractor(
			userNetwork);
	public static final Font bigFont = new Font("Arial", 24);
	public static final Font medFont = new Font("Arial", 16);

	@Override
	public void start(Stage primaryStage) throws Exception {
		generalDisplay = new GeneralDisplay(primaryStage, userNetwork);
		perspectiveDisplay = new PerspectiveDisplay(primaryStage, userNetwork);
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
		try {
			externalInteractor.load("datafiles/example2.txt");
		} catch (Exception e) {
			System.out.println("An error was thrown while loading a file");
		}
		launch(args);
	}

}
