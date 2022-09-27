package com.example.weatherappfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class WeatherController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Label weather;


    public void setTextToLabel(WeatherBean wb){
        weather.setText("City - "  + wb.getCity() + "\n" +
                "Weather - " + wb.getWeather() + " Celcius" +"\n" +
                "Clouds - " + wb.getClouds() + "\n" +
                "Last Updated - " + wb.getLastUpdated()
        );
    }

    public void setTextToLabel(ArrayList<WeatherBean> wbList) throws IOException {
        String temp = "";
        for(WeatherBean wb : wbList){
            temp += "Weather - " + wb.getWeather() + " Celcius" +"\n" +
                    "Clouds - " + wb.getClouds() + "\n" +
                    "Last Updated - " + wb.getLastUpdated() + "\n";
        }



    }



    public void goToHomePage(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home-page-view.fxml"));
        root = loader.load();

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




}
