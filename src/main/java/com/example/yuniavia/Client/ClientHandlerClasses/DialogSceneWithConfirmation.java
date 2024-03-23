package com.example.yuniavia.Client.ClientHandlerClasses;

import com.example.yuniavia.Client.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DialogSceneWithConfirmation {

    public static Stage dialog_stage_with_confirmation;

    public static boolean flag_of_solution;
    public void noButton(ActionEvent actionEvent) {
        flag_of_solution=false;
        dialog_stage_with_confirmation.close();


    }

    public void yesButton(ActionEvent actionEvent) {
        flag_of_solution=true;
        dialog_stage_with_confirmation.close();
    }
    public static void openDialogSceneWithConfirmation() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("dialogSceneWithConfirmation.fxml"));
        Scene dialog_scene = new Scene(fxmlLoader.load(), 400, 300);
        flag_of_solution=false;


        dialog_stage_with_confirmation =new Stage();
        dialog_stage_with_confirmation.setTitle("YuniAvia");
        dialog_stage_with_confirmation.setScene(dialog_scene);
        dialog_stage_with_confirmation.show();
        dialog_stage_with_confirmation.setResizable(false);
        dialog_stage_with_confirmation.show();
        dialog_stage_with_confirmation.setAlwaysOnTop(true);
        dialog_stage_with_confirmation.setOnCloseRequest(windowEvent -> {
            dialog_stage_with_confirmation.close();
        });
    }

}
