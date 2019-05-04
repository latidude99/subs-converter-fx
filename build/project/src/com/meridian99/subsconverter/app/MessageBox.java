/**Copyright (C) 2017  Piotr Czapik.
*  @author Piotr Czapik
*  @version 2.0
*  
*  This file is part of Subs Converter.
*  Subs Converter is free software: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, either version 3 of the License, or
*  (at your option) any later version.
* 
*  Subs Converter is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
* 
*  You should have received a copy of the GNU General Public License
*  along with Subs Converter.  If not, see <http://www.gnu.org/licenses/>
*  or write to: latitude1001101@gmail.com
*/

package com.meridian99.subsconverter.app;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class MessageBox {
    public static void show(String message, String title) {
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(250);
        
        Label lbl = new Label();
        lbl.setText(message);
        
        Button btnOK = new Button();
        btnOK.setText("OK");
        btnOK.setMinWidth(80);
        btnOK.setOnAction(e -> stage.close());
        btnOK.setOnKeyPressed(e -> stage.close());
        
        VBox pane = new VBox(30);
        pane.getChildren().addAll(lbl, btnOK);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20, 20, 20, 20));
        
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.showAndWait();
        }
}
