package com.example.yuniavia.Client.ClientHandlerClasses;

import com.example.yuniavia.Client.Client;
import com.example.yuniavia.Client.Main;
import com.example.yuniavia.Server.Constants.Configs;
import com.example.yuniavia.Server.Constants.Requests;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DispatcherWorkScene {

    @FXML
    AnchorPane anchorPane;
    @FXML
    GridPane gridPaneTemplate;
    @FXML
    TableView<Runway> RunwaysTable;
    @FXML
    TableColumn<Runway, String> RunwayNumberColumn;
    @FXML
    TableColumn<Runway, String> StatusColumn;
    @FXML
    TableColumn<Runway, String> OccupationColumn;
    @FXML
    ImageView ImageViewTemplate;
    @FXML
    Text StateTextTemplate;
    @FXML
    Button StateButtonTemplate;
    @FXML
    Text OccupationTextTemplate;
    @FXML
    Button OccupationButtonTemplate;
    @FXML
    Text FlightNumberTextTemplate;
    @FXML
    Text DepartureTimeTextTemplate;
    @FXML
    Text DepartureTimeFormTextTemplate;
    @FXML
    Rectangle Indicator;


    ObservableList<Runway> runways_list = FXCollections.observableArrayList();


    private void enterDataInTable() throws IOException {
        ObservableList<Runway> runways = getRunways();
        RunwaysTable.setItems(runways);
    }


    public void initialize() throws IOException {
        setPropertiesForTable();
        enterDataInTable();


    }

    public void indicator(Boolean flag) {
        if (flag) {
            Indicator.setFill(Color.GREEN);

        } else {
            Indicator.setFill(Color.RED);
        }
        Indicator.setOpacity(1);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> Indicator.setOpacity(0)) // Через 5 секунд изменить Opacity на 0
        );
        timeline.play();

    }

    public void StateButtonClicked(ActionEvent actionEvent) throws IOException {

        if (StateButtonTemplate.getText().equals(Configs.ACTIVE_STATE_RUNWAY)) {
            runways_list.get(RunwaysTable.getSelectionModel().getSelectedIndex()).setState(Configs.ACTIVE_STATE_RUNWAY);
            StateButtonTemplate.setText(Configs.NON_ACTIVE_STATE_RUNWAY);
        } else {
            if (StateButtonTemplate.getText().equals(Configs.NON_ACTIVE_STATE_RUNWAY)) {
                runways_list.get(RunwaysTable.getSelectionModel().getSelectedIndex()).setState(Configs.NON_ACTIVE_STATE_RUNWAY);
                StateButtonTemplate.setText(Configs.ACTIVE_STATE_RUNWAY);
            }
        }

        editGridPaneTemplate(runways_list.get(RunwaysTable.getSelectionModel().getSelectedIndex()));
        updateStateOfRunwayInDB(runways_list.get(RunwaysTable.getSelectionModel().getSelectedIndex()));
        enterDataInTable();
        RunwaysTable.refresh();
        gridPaneTemplate.setOpacity(0);
        gridPaneTemplate.setDisable(true);

    }

    private void updateStateOfRunwayInDB(Runway runway) {
        List<Object> list = List.of(Requests.REQUEST_UPDATE_RUNWAY_STATE, runway);

        try {
            Client.sendToServer(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String answer = null;
        try {
            answer = String.valueOf(Client.getFromServer().get(0));
            System.out.println(answer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (answer.equals(Configs.UNSUCCESS_ACTION)){
            indicator(false);
        }
        else
        {
            indicator(true);

        }


    }

    public void OccupationButtonClicked(ActionEvent actionEvent) throws IOException {
        int selected_item_index=RunwaysTable.getSelectionModel().getSelectedIndex();;
        if (OccupationButtonTemplate.getText().equals(Configs.OCCUPATED_RUNWAY)) {
            runways_list.get(RunwaysTable.getSelectionModel().getSelectedIndex()).setOccupation(Configs.OCCUPATED_RUNWAY);

            FlightsSceneForDispatcher.openStageWithFlights(Main.root_stage);
            if(FlightsSceneForDispatcher.selected_flight!=null){

                Runway runway=runways_list.get(RunwaysTable.getSelectionModel().getSelectedIndex());
                RunwayWithFlightInfo runwayWithFlightInfo=new RunwayWithFlightInfo(FlightsSceneForDispatcher.selected_flight.getFlight_id(),runway.getState(),
                        runway.getPicture_path(),runway.getNumber_of_runway(),FlightsSceneForDispatcher.selected_flight.getFlight_number(),
                        FlightsSceneForDispatcher.selected_flight.getDeparture_time());
                runwayWithFlightInfo.setOccupation(Configs.OCCUPATED_RUNWAY);
                runways_list.set(RunwaysTable.getSelectionModel().getSelectedIndex(),runwayWithFlightInfo);

                OccupationButtonTemplate.setText(Configs.NOT_OCCUPATED_RUNWAY);
            }
        } else {
            if (OccupationButtonTemplate.getText().equals(Configs.NOT_OCCUPATED_RUNWAY)) {
                runways_list.get(RunwaysTable.getSelectionModel().getSelectedIndex()).setOccupation(Configs.NOT_OCCUPATED_RUNWAY);
                OccupationButtonTemplate.setText(Configs.OCCUPATED_RUNWAY);
            }
        }
        RunwaysTable.getSelectionModel().select(selected_item_index);
        editGridPaneTemplate(runways_list.get(RunwaysTable.getSelectionModel().getSelectedIndex()));
        updateOccupationOfRunwayInDB(runways_list.get(selected_item_index));

        //enterDataInTable();
        RunwaysTable.setItems(runways_list);
        RunwaysTable.refresh();
        gridPaneTemplate.setOpacity(0);
        gridPaneTemplate.setDisable(true);

    }

    private void updateOccupationOfRunwayInDB(Runway runway) {
        List<Object> list = List.of(Requests.REQUEST_UPDATE_RUNWAY_OCCUPATION, runway);

        try {
            Client.sendToServer(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String answer = null;
        try {
            answer = String.valueOf(Client.getFromServer().get(0));
            System.out.println(answer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (answer.equals(Configs.UNSUCCESS_ACTION)){
            indicator(false);
        }
        else
        {
            indicator(true);

        }


    }

    private void editGridPaneTemplate(Runway runway) {

        Image image = new Image(runway.getPicture_path());
        ImageViewTemplate.setImage(image);
        StateTextTemplate.setText(runway.getState());
        if (runway.getState().equals(Configs.ACTIVE_STATE_RUNWAY)) {
            StateButtonTemplate.setText(Configs.NON_ACTIVE_STATE_RUNWAY);
        }
        if (runway.getState().equals(Configs.NON_ACTIVE_STATE_RUNWAY)) {
            StateButtonTemplate.setText(Configs.ACTIVE_STATE_RUNWAY);
          }

        OccupationTextTemplate.setText(runway.getOccupation());
        if(runway.getOccupation().equals(Configs.OCCUPATED_RUNWAY)) {
        OccupationButtonTemplate.setText(Configs.NOT_OCCUPATED_RUNWAY);
        RunwayWithFlightInfo runwayWithFlightInfo =  (RunwayWithFlightInfo) runway;
        DepartureTimeTextTemplate.setText(runwayWithFlightInfo.getDeparture_time());
        FlightNumberTextTemplate.setText(runwayWithFlightInfo.getFlight_number());
        DepartureTimeTextTemplate.setOpacity(1);
        FlightNumberTextTemplate.setOpacity(1);
        DepartureTimeFormTextTemplate.setOpacity(1);


    }
        else {
            if (runway.getOccupation().equals(Configs.NOT_OCCUPATED_RUNWAY)) {
                OccupationButtonTemplate.setText(Configs.OCCUPATED_RUNWAY);
                DepartureTimeTextTemplate.setOpacity(0);
                FlightNumberTextTemplate.setOpacity(0);
                DepartureTimeFormTextTemplate.setOpacity(0);
            }
        }

}

    private void setPropertiesForTable() {
        RunwayNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number_of_runway"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        OccupationColumn.setCellValueFactory(new PropertyValueFactory<>("occupation"));
    }
    public void AnchorPaneClicked(MouseEvent mouseEvent) {
        RunwaysTable.getSelectionModel().clearSelection();
        gridPaneTemplate.setDisable(true);
        gridPaneTemplate.setOpacity(0);
    }
    public void TableClicked(MouseEvent mouseEvent) {

        if(RunwaysTable.getSelectionModel().getSelectedItem()!=null) {
            editGridPaneTemplate(runways_list.get(RunwaysTable.getSelectionModel().getSelectedIndex()));

            gridPaneTemplate.setDisable(false);
            gridPaneTemplate.setOpacity(1);
        }
        else {gridPaneTemplate.setDisable(true);
            gridPaneTemplate.setOpacity(0);
        }

    }
    private ObservableList<Runway> getRunways() throws IOException {
        List<Object> list=List.of(Requests.REQUEST_SHOW_RUNWAYS);
        try {
            Client.sendToServer(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        list=Client.getFromServer();
        ArrayList<Runway> runways= (ArrayList<Runway>) list.get(0);
        if(runways_list.size()!=0){
            runways_list.clear();
        }
        for (Runway runway:runways){
            runways_list.add(runway);
        }
        return  runways_list;
    }


    public void returnBackButtonClicked(ActionEvent actionEvent) throws IOException {
        Main.setSceneOnRootStage("centralWorkScene.fxml");

    }
}
