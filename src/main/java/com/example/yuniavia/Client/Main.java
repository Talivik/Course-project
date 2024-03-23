package com.example.yuniavia.Client;

import com.example.yuniavia.Server.Constants.Requests;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.List;

import static com.example.yuniavia.Server.Constants.Configs.SERVER_HOST;
import static com.example.yuniavia.Server.Constants.Configs.SERVER_PORT;

public class Main extends Application {


    public static Stage root_stage = null;
    public static void setSceneOnRootStage(String name_of_fxml_file) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(name_of_fxml_file));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        root_stage.setScene(scene);
    }
    public static Scene getSceneFromFXML(String name_of_fxml_file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(name_of_fxml_file));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        return scene;
    }
    @Override
    public void start(Stage stage)  {

        root_stage =new Stage();
        root_stage.setTitle("YuniAvia");


        try {
            setSceneOnRootStage("authorizationScene.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        root_stage.show();
        root_stage.setOnCloseRequest(windowEvent -> {
            //CentralScene.stop();
            System.exit(0);
        });
        root_stage.setResizable(false);
        root_stage.show();

        try {

            Client.startConnection();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws IOException {
        launch();



    }

}