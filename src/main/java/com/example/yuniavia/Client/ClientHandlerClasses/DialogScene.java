package com.example.yuniavia.Client.ClientHandlerClasses;

import com.example.yuniavia.Client.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DialogScene {

    public static Stage dialog_stage;
    public static void openDialogScene(String user_string, Stage root_stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("dialogScene.fxml"));
        Scene dialog_scene = new Scene(fxmlLoader.load(), 400, 300);

        Text user_text=new Text();
        user_text.setStyle("-fx-fill: #1616a4;");
        user_text.setText(user_string);
        user_text.setLayoutY(100);
        AnchorPane anchorPane=(AnchorPane) dialog_scene.getRoot();
        anchorPane.getChildren().add(user_text);
        user_text.setWrappingWidth(150);
        //text_about_success_registration.setStroke(Color.web("#868615"));
        Font font=new Font("Artifakt Element Heavy Italic",15.0);
        user_text.setFont(font);
        user_text.setTextAlignment(TextAlignment.CENTER);
        anchorPane.setLeftAnchor(user_text,125.0);

        dialog_stage=new Stage();
        dialog_stage.setTitle("YuniAvia");
        dialog_stage.setScene(dialog_scene);

        dialog_stage.setResizable(false);


        dialog_stage.initModality(Modality.WINDOW_MODAL); // Установка модального режима окна
        dialog_stage.initOwner(root_stage); // Установка основного окна (владельца) для блокировки
        dialog_stage.showAndWait();
    }


    public void continueButton () {
        dialog_stage.close();
    }



}