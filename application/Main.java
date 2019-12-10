package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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
	private static Stage mainStage;

	/**
	 * 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
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

	/**
	 * Exits the program and prompts the user on if they'd like to save
	 */
	public static void exit() {
		Pane root = new Pane();
		root.setPrefSize(300, 300);

		Label label = new Label(
				"Would you like to save? Input the file name below!");
		label.setWrapText(true);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setFont(medFont);
		label.setMaxWidth(300);
		root.getChildren().add(label);

		TextField textBox = new TextField("log.txt");
		textBox.relocate(50, 100);
		textBox.setPrefWidth(200);
		root.getChildren().add(textBox);

		Label status = new Label();
		status.setMinWidth(300);
		status.setAlignment(Pos.CENTER);
		status.relocate(0, 60);
		status.setFont(bigFont);

		Button save = new Button("Save");
		save.relocate(30, 220);
		save.setPrefSize(100, 50);
		root.getChildren().add(status);
		save.setOnAction(event -> {
			Stage confirmation = new Stage();
			Pane confirm = new Pane();
			confirm.setPrefSize(200, 200);

			Label confirmText = new Label("Are you sure you want to save?");
			confirmText.setMaxWidth(200);
			confirmText.setFont(medFont);
			confirmText.setWrapText(true);
			confirmText.setTextAlignment(TextAlignment.CENTER);
			confirm.getChildren().add(confirmText);

			Button yes = new Button("Yes");
			yes.setPrefSize(80, 40);
			yes.relocate(15, 140);
			yes.setOnAction(subEvent -> {
				if (externalInteractor.saveLog(textBox.getText())) {
					status.setText("Success! File saved!");
				} else {
					status.setText("An error occurred with the file name.");
				}
				confirmation.close();
			});
			confirm.getChildren().add(yes);

			Button no = new Button("No");
			no.setPrefSize(80, 40);
			no.relocate(105, 140);
			no.setOnAction(subEvent -> {
				confirmation.close();
			});
			confirm.getChildren().add(no);

			confirmation.setScene(new Scene(confirm, 200, 200));
			confirmation.show();
		});
		root.getChildren().add(save);

		Button noSave = new Button("Don't Save");
		noSave.relocate(170, 220);
		noSave.setPrefSize(100, 50);
		noSave.setOnAction(event -> {
			Stage confirmation = new Stage();
			Pane confirm = new Pane();
			confirm.setPrefSize(200, 200);

			Label confirmText = new Label("Are you sure you don't want to save?");
			confirmText.setMaxWidth(200);
			confirmText.setFont(medFont);
			confirmText.setWrapText(true);
			confirmText.setTextAlignment(TextAlignment.CENTER);
			confirm.getChildren().add(confirmText);

			Button yes = new Button("Yes");
			yes.setPrefSize(80, 40);
			yes.relocate(15, 140);
			yes.setOnAction(subEvent -> {
				confirmation.close();
				mainStage.close();
				externalInteractor.saveLog(null);
			});
			confirm.getChildren().add(yes);

			Button no = new Button("No");
			no.setPrefSize(80, 40);
			no.relocate(105, 140);
			no.setOnAction(subEvent -> {
				confirmation.close();
			});
			confirm.getChildren().add(no);

			confirmation.setScene(new Scene(confirm, 200, 200));
			confirmation.show();
		});
		root.getChildren().add(noSave);

		mainStage.setScene(new Scene(root, 300, 300));
	}

	/**
	 * Launches the display
	 * 
	 * @param args - unused
	 */
	public static void main(String[] args) {
		try {
			//externalInteractor.load("datafiles/example2.txt");
		} catch (Exception e) {
			System.out.println("An error was thrown while loading a file");
		}
		launch(args);
	}

}
