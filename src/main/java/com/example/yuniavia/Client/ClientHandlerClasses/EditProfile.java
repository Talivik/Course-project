package com.example.yuniavia.Client.ClientHandlerClasses;

import com.example.yuniavia.Client.Client;
import com.example.yuniavia.Client.Main;
import com.example.yuniavia.Server.Constants.Configs;
import com.example.yuniavia.Server.Constants.Requests;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;

public class EditProfile {
    @FXML
    private Text nameText;
    @FXML
    private Text phoneNumberText;
    @FXML
    private Text surnameText;
    @FXML
    private Button changePasswordButton;
    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private Text invalidPasswordLength;
    @FXML
    private Text passwordsNotEquals;
    @FXML
    private Button savePasswordEditionsButton;
    @FXML
    private Button hideChangePasswordButton;
    @FXML
    private Text oldPasswordNotCorrect;
    public void returnBackButtonClicked(ActionEvent actionEvent) throws IOException {
        Main.setSceneOnRootStage("centralWorkScene.fxml");

    }

    public void initialize() {
        nameText.setText(AuthorizationScene.authorizated_account.getFirst_name());
        phoneNumberText.setText(AuthorizationScene.authorizated_account.getPhone_number());
        surnameText.setText(AuthorizationScene.authorizated_account.getSecond_name());

    }

    public void changePassword(ActionEvent actionEvent) {
        changePasswordButton.setDisable(true);
        changePasswordButton.setOpacity(0);
        oldPasswordField.setOpacity(1);
        newPasswordField.setOpacity(1);
        repeatPasswordField.setOpacity(1);
        oldPasswordField.setDisable(false);
        newPasswordField.setDisable(false);
        repeatPasswordField.setDisable(false);
        savePasswordEditionsButton.setOpacity(1);
        savePasswordEditionsButton.setDisable(false);
        hideChangePasswordButton.setDisable(false);
        hideChangePasswordButton.setOpacity(1);

    }

    public void savePasswordEditions(ActionEvent actionEvent) throws IOException {
        passwordsNotEquals.setOpacity(0);
        AuthorizationScene.setDefaultBorderColorForTextFields(oldPasswordField);
        AuthorizationScene.setDefaultBorderColorForTextFields(newPasswordField);
        AuthorizationScene.setDefaultBorderColorForTextFields(repeatPasswordField);
        invalidPasswordLength.setOpacity(0);
        passwordsNotEquals.setOpacity(0);
        oldPasswordNotCorrect.setOpacity(0);
        if (allFieldsFillValidation() && passwordsEqualsValidation() && passwordsLengthValidation()) {


            DialogSceneWithConfirmation.openDialogSceneWithConfirmation();
            DialogSceneWithConfirmation.dialog_stage_with_confirmation.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<Event>() {
                @Override
                public void handle(Event event) {

                    if (DialogSceneWithConfirmation.flag_of_solution) {
                        AuthorizationScene.authorizated_account.setPassword(newPasswordField.getText().trim());

                        try {
                            sendUpdateAccountDataToServer(AuthorizationScene.authorizated_account, AuthorizationScene.authorizated_account);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    };
                }
            });




        }
    }
    private void sendUpdateAccountDataToServer(Account new_account, Account old_account) throws IOException {
        List<Object> list=List.of(Requests.REQUEST_UPDATE_ACCOUNT, new_account, old_account);
        try {
            Client.sendToServer(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String answer= String.valueOf(Client.getFromServer().get(0));
        if (answer.equals(Configs.SUCCESS_ACTION)){
            DialogScene.openDialogScene("Изменения успешно применены!", Main.root_stage);
            hideChangePassword(new ActionEvent());

        }
        else
        {
            DialogScene.openDialogScene("Изменения не были применены", Main.root_stage);


        }
    }



    public void hideChangePassword(ActionEvent actionEvent) {
        passwordsNotEquals.setOpacity(0);
        invalidPasswordLength.setOpacity(0);
        oldPasswordNotCorrect.setOpacity(0);
        changePasswordButton.setDisable(false);
        changePasswordButton.setOpacity(1);
        oldPasswordField.setOpacity(0);
        newPasswordField.setOpacity(0);
        repeatPasswordField.setOpacity(0);
        oldPasswordField.setDisable(true);
        newPasswordField.setDisable(true);
        repeatPasswordField.setDisable(true);
        savePasswordEditionsButton.setOpacity(0);
        savePasswordEditionsButton.setDisable(true);
        oldPasswordField.clear();
        newPasswordField.clear();
        repeatPasswordField.clear();
        hideChangePasswordButton.setDisable(true);
        hideChangePasswordButton.setOpacity(1);
    }
    private boolean allFieldsFillValidation() {
        if (oldPasswordField.getText().trim().equals("") || newPasswordField.getText().trim().equals("") ||
                repeatPasswordField.getText().trim().equals("")) {
            AuthorizationScene.setRedBorderColorForTextFields(oldPasswordField);
            AuthorizationScene.setRedBorderColorForTextFields(newPasswordField);
            AuthorizationScene.setRedBorderColorForTextFields(repeatPasswordField);
            return false;
        } else return true;
    }

    private boolean passwordsEqualsValidation() {
        if (!newPasswordField.getText().trim().equals(repeatPasswordField.getText().trim())) {
            passwordsNotEquals.setOpacity(1);
            AuthorizationScene.setRedBorderColorForTextFields(newPasswordField);
            AuthorizationScene.setRedBorderColorForTextFields(repeatPasswordField);
            return false;
        }
        if (!oldPasswordField.getText().trim().equals(AuthorizationScene.authorizated_account.getPassword())) {
            oldPasswordNotCorrect.setOpacity(1);
            AuthorizationScene.setRedBorderColorForTextFields(oldPasswordField);
            return false;
        } else return true;
    }

    private boolean passwordsLengthValidation() {
        if (newPasswordField.getText().trim().length() < 6 || newPasswordField.getText().trim().length() > 15) {
            AuthorizationScene.setRedBorderColorForTextFields(newPasswordField);
            AuthorizationScene.setRedBorderColorForTextFields(repeatPasswordField);
            invalidPasswordLength.setOpacity(1);
            return false;
        } else return true;
    }
}
