package gui;

import javafx.application.Application;
import javafx.stage.Stage;
import gui.kunde.KundeControl;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		new KundeControl(primaryStage);
	}	
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}