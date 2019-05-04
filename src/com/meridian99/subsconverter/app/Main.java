/**Copyright (C) 2017  Piotr Czapik.
* @author Piotr Czapik
* @version 1.0
* 
*  This file is part of SubsConverterFX.
*  SubsConverterFX is free software: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, either version 3 of the License, or
*  (at your option) any later version.
* 
*  SubsConverterFX is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
* 
*  You should have received a copy of the GNU General Public License
*  along with SubsConverterFX.  If not, see <http://www.gnu.org/licenses/>
*  or write to: latidude99@gmail.com
*/

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
		final String appName = "Subtitles Converter v1.0";
		
			Parent parent = (Parent) FXMLLoader.load(getClass().getResource("/com/meridian99/subsconverter/app/MainPane.fxml"));
			Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
			primaryStage.setTitle(appName);
			primaryStage.setMinWidth(720);
			primaryStage.setMinHeight(630);
			primaryStage.show();
		
	}
	
}
