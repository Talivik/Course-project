package com.example.yuniavia.Client.ClientHandlerClasses;

import com.example.yuniavia.Client.Client;
import com.example.yuniavia.Client.Main;
import com.example.yuniavia.Server.Constants.Configs;
import com.example.yuniavia.Server.Constants.Requests;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//import java.sql.ResultSet;
//import java.sql.SQLException;


public class AuthorizationScene {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text enterAllFields;
    @FXML
    private Text accountNotFound;
    @FXML
    private ImageView image;


    static Account authorizated_account=new Account();


    public static void setRedBorderColorForTextFields(TextField textField){
        textField.setStyle(
                "-fx-border-color: red;" +
                "-fx-border-radius: 5;"+
                "-fx-background-color: #F8F8FF");
    }
    public static void setDefaultBorderColorForTextFields(TextField textField){
        textField.setStyle(
                "-fx-background-color: #F8F8FF;" +
                "-fx-border-radius: 5");
    }
    public AuthorizationScene() {
    }

    private boolean validationOnSignUp(){

        if (loginField.getText().trim().equals("")||passwordField.getText().trim().equals("")){
            enterAllFields.setOpacity(1);
            setRedBorderColorForTextFields(loginField);
            setRedBorderColorForTextFields(passwordField);
            return false;
        }
        else {
            return true;
        }

    }

    private void sendAuthorizationDataToServer() throws SQLException {
        Account account = new Account();
        account.setPhone_number(loginField.getText().trim());
        account.setPassword(passwordField.getText().trim());
        List<Object> list=List.of(Requests.REQUEST_LOGIN, account);
        try {
            Client.sendToServer(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void signInClicked (ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {


        setDefaultBorderColorForTextFields(loginField);
        setDefaultBorderColorForTextFields(passwordField);
        enterAllFields.setOpacity(0);
        accountNotFound.setOpacity(0);
        if (validationOnSignUp())
        {
            sendAuthorizationDataToServer();
            List<Object> list=Client.getFromServer();
            String answer= String.valueOf(list.get(0));
            if (answer.equals(Configs.UNSUCCESS_ACTION)){
                DialogScene.openDialogScene("Аккаунт с введёнными данными не найден!", Main.root_stage);
            }
            else
            {
                setAccountInAuthorizatedAccount((Account) list.get(1));
                DialogScene.openDialogScene("Вы успешно авторизовались!", Main.root_stage);
                Main.setSceneOnRootStage("centralWorkScene.fxml");

            }

            }
        }

    private void setAccountInAuthorizatedAccount(Account account) {
        authorizated_account.setPhone_number(account.getPhone_number());
        authorizated_account.setPassword(account.getPassword());
        authorizated_account.setFirst_name(account.getFirst_name());
        authorizated_account.setSecond_name(account.getSecond_name());
        authorizated_account.setRole(account.getRole());

    }


    public void regInClicked(ActionEvent actionEvent) throws IOException {
        Main.setSceneOnRootStage("registrationScene.fxml");
        // Main.setSceneOnRootStage("dispatcherWorkScene.fxml");


    }

}