package com.example.yuniavia.Client.ClientHandlerClasses;

import com.example.yuniavia.Client.Client;
import com.example.yuniavia.Client.Main;
import com.example.yuniavia.Server.Constants.Configs;
import com.example.yuniavia.Server.Constants.Requests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminEditingAccounts {
    @FXML
    TableView<Account> AccountsTable;
    @FXML
    TableColumn<Account,String> FirstNameColumn;
    @FXML
    TableColumn<Account,String> SecondNameColumn;
    @FXML
    TableColumn<Account,String> PhoneNumberColumn;
    @FXML
    TableColumn<Account,String> RoleColumn;
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;
    @FXML
    private Button cancelEditingButton;
    @FXML
    private Group editGroup;
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
    @FXML
    private SplitMenuButton roleMenu;
    @FXML
    private MenuItem dispatcherInMenu;
    @FXML
    private MenuItem adminInMenu;

    ObservableList<Account> accounts_list = FXCollections.observableArrayList();
    int table_selected_index=-1;


    public void initialize() throws IOException {
        setPropertiesForTable();
        enterAccountsInTable();
    }
    private void enterAccountsInTable() throws IOException {
        List<Object> list=List.of(Requests.REQUEST_SHOW_ACCOUNTS);
        try {
            Client.sendToServer(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        list=Client.getFromServer();
        ArrayList<Account> accounts= (ArrayList<Account>) list.get(0);
        if(accounts_list.size()!=0){
            accounts_list.clear();
        }
        for (Account account:accounts){
            switch (account.getRole()){
                case "1":account.setRole_in_string("Диспетчер");
                    break;
                case "2":account.setRole_in_string("Администратор");
                    break;
            }
            accounts_list.add(account);
        }
        AccountsTable.setItems(accounts_list);
    }
    private void setPropertiesForTable() {
        FirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        SecondNameColumn.setCellValueFactory(new PropertyValueFactory<>("second_name"));
        PhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        RoleColumn.setCellValueFactory(new PropertyValueFactory<>("role_in_string"));

    }
    public void tableClicked(MouseEvent mouseEvent) {
        if(AccountsTable.getSelectionModel().getSelectedItem()!=null &&
                !accounts_list.get(AccountsTable.getSelectionModel().getSelectedIndex()).getPhone_number().equals(AuthorizationScene.authorizated_account.getPhone_number())) {
            table_selected_index=AccountsTable.getSelectionModel().getSelectedIndex();
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }
        else {
            editButton.setDisable(true);
            deleteButton.setDisable(true);
        }
    }



    public void editButtonClicked(ActionEvent actionEvent) {
        editButton.setDisable(true);
        editButton.setOpacity(0);
        deleteButton.setDisable(true);
        deleteButton.setOpacity(0);
        cancelEditingButton.setOpacity(1);
        cancelEditingButton.setDisable(false);
        AccountsTable.setDisable(true);
        AccountsTable.setOpacity(0);
        editGroup.setOpacity(1);
        editGroup.setDisable(false);

        enterDataInFields();
    }

    private void enterDataInFields(){
        Account account=accounts_list.get(AccountsTable.getSelectionModel().getSelectedIndex());
        firstNameField.setText(account.getFirst_name());
        secondNameField.setText(account.getSecond_name());
        phoneNumberField.setText(account.getPhone_number());
        phoneNumberField.setEditable(false);
        createPasswordField.setText(account.getPassword());
        repeatPasswordField.setText(account.getPassword());
        roleMenu.setText(AccountsTable.getSelectionModel().getSelectedItem().getRole_in_string());
    }

    public void deleteButtonClicked(ActionEvent actionEvent) throws IOException {
        if(AccountsTable.getSelectionModel().getSelectedItem()!=null){
            DialogSceneWithConfirmation.openDialogSceneWithConfirmation();
            DialogSceneWithConfirmation.dialog_stage_with_confirmation.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if(DialogSceneWithConfirmation.flag_of_solution) {

                        try {
                            sendDeleteAccountDataToServer();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    else {
                        try {
                            DialogScene.openDialogScene("Аккаунт не был удалён", Main.root_stage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
    private void sendDeleteAccountDataToServer() throws IOException {
        List<Object> list=List.of(Requests.REQUEST_DELETE_ACCOUNT, accounts_list.get(table_selected_index));
        try {
            Client.sendToServer(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String answer= String.valueOf(Client.getFromServer().get(0));
        if (answer.equals(Configs.SUCCESS_ACTION)){
            DialogScene.openDialogScene("Аккаунт успешно удалён!", Main.root_stage);
            accounts_list.remove(table_selected_index);
            AccountsTable.setItems(accounts_list);
            AccountsTable.refresh();
        }
        else
        {
            DialogScene.openDialogScene("Аккаунт не был удалён! Ошибка базы данных!", Main.root_stage);


        }
    }


    public void saveEditings(ActionEvent actionEvent) throws IOException {
        if (validationOnSignUp()) {
            Account new_account = accounts_list.get(table_selected_index);
            DialogSceneWithConfirmation.openDialogSceneWithConfirmation();
            DialogSceneWithConfirmation.dialog_stage_with_confirmation.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<Event>() {
                @Override
                public void handle(Event event) {

                    if (DialogSceneWithConfirmation.flag_of_solution) {
                        new_account.setFirst_name(firstNameField.getText().trim());
                        new_account.setSecond_name(secondNameField.getText().trim());
                        new_account.setPhone_number(phoneNumberField.getText().trim());
                        new_account.setRole(getRoleFromMenu());
                        new_account.setRole_in_string(roleMenu.getText());
                        new_account.setPassword(createPasswordField.getText().trim());

                        try {
                            sendUpdateAccountDataToServer(new_account,accounts_list.get(table_selected_index));
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
            accounts_list.set(table_selected_index, new_account);
            cancelEditingButtonClicked(new ActionEvent());
            AccountsTable.setItems(accounts_list);
            AccountsTable.refresh();
        }
        else
        {
            DialogScene.openDialogScene("Изменения не были применены", Main.root_stage);


        }
    }

    private String getRoleFromMenu() {
        switch (roleMenu.getText()){
            case "Диспетчер":return "1";
            case "Администратор":return "2";
        }
        return "1";
    }
    private boolean validationOnSignUp() {
        AuthorizationScene.setDefaultBorderColorForTextFields(createPasswordField);
        AuthorizationScene.setDefaultBorderColorForTextFields(repeatPasswordField);
        AuthorizationScene.setDefaultBorderColorForTextFields(phoneNumberField);
        invalidPasswordLength.setOpacity(0);
        passwordsNotEquals.setOpacity(0);
        enterAllFields.setOpacity(0);
        if ((allFieldsFillValidation())&&(passwordsEqualsValidation())&&
                (passwordsLengthValidation())){
            return true;
        }
        else return false;
    }
    private boolean allFieldsFillValidation(){
        if (firstNameField.getText().trim().equals("") || secondNameField.getText().trim().equals("")||
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


    public void sceneClicked(MouseEvent mouseEvent) {
        AccountsTable.getSelectionModel().clearSelection();
        editButton.setDisable(true);
        deleteButton.setDisable(true);
    }
    public void dispatcherClicked(ActionEvent actionEvent) {
        roleMenu.setText("Диспетчер");

    }

    public void adminClicked(ActionEvent actionEvent) {
        roleMenu.setText("Администратор");

    }

    public void cancelEditingButtonClicked(ActionEvent actionEvent) {
        editButton.setOpacity(1);
        deleteButton.setOpacity(1);
        cancelEditingButton.setOpacity(0);
        cancelEditingButton.setDisable(true);
        AccountsTable.setDisable(false);
        AccountsTable.setOpacity(1);
        editGroup.setOpacity(0);
        editGroup.setDisable(true);
        invalidPhoneNumber.setOpacity(0);
        enterAllFields.setOpacity(0);
        invalidPasswordLength.setOpacity(0);
        passwordsNotEquals.setOpacity(0);

    }

    public void returnBackButtonClicked(ActionEvent actionEvent) throws IOException {
        Main.setSceneOnRootStage("centralWorkScene.fxml");
    }
}
