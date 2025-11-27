package gui;

import gui.kunde.KundeControl;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	// Eigene Branch Test1
	@Override
	public void start(Stage primaryStage) {
		new KundeControl(primaryStage);
	} 	
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}