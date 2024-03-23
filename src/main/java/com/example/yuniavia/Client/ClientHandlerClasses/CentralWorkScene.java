package com.example.yuniavia.Client.ClientHandlerClasses;

import com.example.yuniavia.Client.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;


public class CentralWorkScene {
    @FXML
    private Button targetButton;
    public void initialize(){
        System.out.println(AuthorizationScene.authorizated_account.getRole());
        switch (AuthorizationScene.authorizated_account.getRole()){
            case "1":targetButton.setText("Управление ВПП");
            break;
            case "2":targetButton.setText("Редактирование аккаунтов");
                break;
            case "5":targetButton.setText("Редактирование аккаунтов");
                break;
        }
    }



    public void returnBackClicked(ActionEvent actionEvent) throws IOException {
        Main.setSceneOnRootStage("authorizationScene.fxml");
    }


    public void editProfileClicked(ActionEvent actionEvent) throws IOException {
        Main.setSceneOnRootStage("editProfile.fxml");
    }



    public void targetButtonClicked(ActionEvent actionEvent) throws IOException {
        switch (AuthorizationScene.authorizated_account.getRole()){
            case "1":Main.setSceneOnRootStage("dispatcherWorkScene.fxml");
                break;
            case "2": {
                Main.setSceneOnRootStage("adminEditingAccounts.fxml");
                break;
            }
            case "5":{
                System.out.println("пятая роль");
                //Main.setSceneOnRootStage("editingAccountsScene.fxml");
                break;}
    }
}
}


