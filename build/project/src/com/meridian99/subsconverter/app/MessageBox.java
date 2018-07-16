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
