package gui;

import gui.kunde.KundeControl;
import javafx.application.Application;
import javafx.stage.Stage;

//--module-path C:\MacBookEyad\eclipse-workspace1\javafx-sdk-23\lib --add-modules=javafx.controls,javafx.fxml
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		new KundeControl(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}