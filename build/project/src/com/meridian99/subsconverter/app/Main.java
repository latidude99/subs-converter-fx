package com.meridian99.subsconverter.app;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		final String appName = "Subtitles Converter v2.0";
		
			Parent parent = (Parent) FXMLLoader.load(getClass().getResource("/com/meridian99/subsconverter/app/MainPane.fxml"));
			Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
			primaryStage.setTitle(appName);
			primaryStage.setMinWidth(720);
			primaryStage.setMinHeight(630);
			primaryStage.show();
		
	}
	
}
