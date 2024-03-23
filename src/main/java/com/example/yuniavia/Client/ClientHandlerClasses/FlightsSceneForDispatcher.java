package com.example.yuniavia.Client.ClientHandlerClasses;

import com.example.yuniavia.Client.Client;
import com.example.yuniavia.Client.Main;
import com.example.yuniavia.Server.Constants.Requests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlightsSceneForDispatcher {
    @FXML
    TableView<Flight> FlightsTable;
    @FXML
    TableColumn<Flight,String> DepartureCityColumn;
    @FXML
    TableColumn<Flight,String> ArrivalCityColumn;
    @FXML
    TableColumn<Flight,String> DepartureTimeColumn;
    @FXML
    TableColumn<Flight,String> FlightNumberColumn;
    @FXML
    TableColumn<Flight,String> NotesColumn;
    @FXML
    Button EnterFlightButton;


    ObservableList<Flight> flights_list = FXCollections.observableArrayList();
    private static Stage root_stage_global=null;
    private static Stage flights_stage=null;
    public static Flight selected_flight=null;
    public static void openStageWithFlights(Stage root_stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("flightsSceneForDispatcher.fxml"));
        Scene flights_scene = new Scene(fxmlLoader.load(), 800, 600);
        //flag_of_solution=false;


       // Button button= (Button) flights_scene.lookup("#EnterFlightButton");


        root_stage_global=root_stage;
        Stage stage =new Stage();
        stage.setTitle("Flights");
        stage.setScene(flights_scene);
        flights_stage=stage;
    /*    button.setOnAction(event -> {

        });
        button.setDisable(false);*/
        stage.initModality(Modality.WINDOW_MODAL); // Установка модального режима окна
        stage.initOwner(root_stage); // Установка основного окна (владельца) для блокировки
        stage.showAndWait(); // Показать окно и ж




    }



    public void initialize() throws IOException {
        setPropertiesForTable();
       enterDataInTable();
    }

    private ObservableList<Flight> getFlights() throws IOException {
        List<Object> list=List.of(Requests.REQUEST_SHOW_FLIGHTS);
        try {
            Client.sendToServer(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        list=Client.getFromServer();
        ArrayList<Flight> flights= (ArrayList<Flight>) list.get(0);
        if(flights_list.size()!=0){
            flights_list.clear();
        }
        for (Flight flight:flights){
            flights_list.add(flight);
        }
        return  flights_list;
    }
    private void enterDataInTable() throws IOException {
        ObservableList<Flight> flights = getFlights();
        FlightsTable.setItems(flights);
    }
    private void setPropertiesForTable() {
        DepartureCityColumn.setCellValueFactory(new PropertyValueFactory<>("departure_city"));
        ArrivalCityColumn.setCellValueFactory(new PropertyValueFactory<>("arrival_city"));
        DepartureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departure_time"));
        FlightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flight_number"));
        NotesColumn.setCellValueFactory(new PropertyValueFactory<>("note"));

    }


    public void tableClicked(MouseEvent mouseEvent) {
        if (FlightsTable.getSelectionModel().getSelectedItem()!=null){
            EnterFlightButton.setDisable(false);
            selected_flight=flights_list.get(FlightsTable.getSelectionModel().getSelectedIndex());
        }
    }

    public void sceneClicked(MouseEvent mouseEvent) {
        selected_flight=null;
        FlightsTable.getSelectionModel().clearSelection();
        EnterFlightButton.setDisable(true);

    }

    public void enterFlightClicked(MouseEvent mouseEvent) {
        if(selected_flight!=null) {
            flights_stage.close();
            root_stage_global.requestFocus(); // Возвращение фокуса на основное окно после закрытия окна 2
        }
    }
}
