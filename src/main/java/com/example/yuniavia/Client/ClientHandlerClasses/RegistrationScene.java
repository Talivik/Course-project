package com.example.yuniavia.Client.ClientHandlerClasses;

import com.example.yuniavia.Client.Client;
import com.example.yuniavia.Client.Main;
import com.example.yuniavia.Server.Constants.Requests;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;
import com.example.yuniavia.Server.Constants.Configs;

public class RegistrationScene {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField secondNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField createPasswordField;
    @FXML
    private TextField repeatPasswordField;

    @FXML
    private Text enterAllFields;
    @FXML
    private Text passwordsNotEquals;
    @FXML
    private Text invalidPhoneNumber;

    @FXML
    private Text invalidPasswordLength;
    @FXML
    private Text accountExist;

    private boolean phoneNumberValidation(){
        if ((phoneNumberField.getText().length()==13)&&phoneNumberField.getText().contains("+375")&&
                phoneNumberField.getText().charAt(0)=='+'){
            return true;
        }
        else{invalidPhoneNumber.setOpacity(1);
            AuthorizationScene.setRedBorderColorForTextFields(phoneNumberField);
            return false;}
    }
    private boolean allFieldsFillValidation(){
        if (firstNameField.getText().trim().equals("") || secondNameField.getText().trim().equals("")||
                phoneNumberField.getText().trim().equals("") ||
                createPasswordField.getText().trim().equals("") || repeatPasswordField.getText().trim().equals(""))
        {
            enterAllFields.setOpacity(1);
            return false;
        }
        else return true;
    }
    private boolean passwordsEqualsValidation(){
        if (!createPasswordField.getText().trim().equals(repeatPasswordField.getText().trim())) {
            passwordsNotEquals.setOpacity(1);
            AuthorizationScene.setRedBorderColorForTextFields(createPasswordField);
            AuthorizationScene.setRedBorderColorForTextFields(repeatPasswordField);
            return false;
        }
        else return true;
    }
    private boolean passwordsLengthValidation(){
        if (createPasswordField.getText().trim().length()<6 || createPasswordField.getText().trim().length()>15)
        {
            AuthorizationScene.setRedBorderColorForTextFields(createPasswordField);
            AuthorizationScene.setRedBorderColorForTextFields(repeatPasswordField);
            invalidPasswordLength.setOpacity(1);
            return false;
        }
        else return true;
    }

    private boolean validationOnSignUp() {
        AuthorizationScene.setDefaultBorderColorForTextFields(phoneNumberField);
        AuthorizationScene.setDefaultBorderColorForTextFields(createPasswordField);
        AuthorizationScene.setDefaultBorderColorForTextFields(repeatPasswordField);
        AuthorizationScene.setDefaultBorderColorForTextFields(phoneNumberField);
        invalidPasswordLength.setOpacity(0);
        invalidPhoneNumber.setOpacity(0);
        passwordsNotEquals.setOpacity(0);
        enterAllFields.setOpacity(0);
        accountExist.setOpacity(0);
        if ((allFieldsFillValidation())&&(passwordsEqualsValidation())&&
                (phoneNumberValidation())&&(passwordsLengthValidation())){
            return true;
        }
        else return false;
    }
    private void sendRegistrationDataToServer(){

        Account account = new Account(firstNameField.getText().trim(),secondNameField.getText().trim(),
                phoneNumberField.getText().trim(),
                createPasswordField.getText().trim(), "1");
        List<Object> list=List.of(Requests.REQUEST_REGISTRATION, account);
        try {
            Client.sendToServer(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void signUpButton() throws IOException {
            if(validationOnSignUp())
            {
            sendRegistrationDataToServer();

            String answer= String.valueOf(Client.getFromServer().get(0));
                if (answer.equals(Configs.UNSUCCESS_ACTION)){
                    DialogScene.openDialogScene("Аккаунт на данный номер уже зарегистрирован!", Main.root_stage);
                }
                else
                {
                    DialogScene.openDialogScene("Вы успешно зарегистрировались!", Main.root_stage);
                    Main.setSceneOnRootStage("authorizationScene.fxml");

                }


        }

    }


    public void returnBack(ActionEvent actionEvent) throws IOException {
        Main.setSceneOnRootStage("authorizationScene.fxml");
    }

}