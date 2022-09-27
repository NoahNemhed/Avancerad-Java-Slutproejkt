package com.example.weatherappfinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HomePageController {
    WeatherParser weatherParser = new WeatherParser();
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private Button button;

    @FXML
    private TextField city;

    @FXML
    private TextField country;

    @FXML
    private ComboBox foreCastDays;

    //Metod för att kolla ifall Textboxen bara innehåller bokstäver.
    private boolean checkIfTextFieldOnlyContainsLetters(String s){
        char[] chars = s.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    //Metod som använder sig av textfield som parametrar för att göra en wBean och sedan föra över datan till en ny FXML fil och displaya datan
    public void searchWeather(javafx.event.ActionEvent actionEvent) throws IOException {
        //Variabel som ska hålla antal dagar vi ska kolla efter.
        int days;
        //kollar så att det är något skrivet i textfälten.
        if(city.getText().length() > 0 && country.getText().length() > 0){
            //Kollar så att något alternativ är valt i comboboxen ifall inget är valt så är det -1.
            if(checkIfTextFieldOnlyContainsLetters(city.getText()) && checkIfTextFieldOnlyContainsLetters(country.getText())){
                if(foreCastDays.getSelectionModel().getSelectedIndex() == -1){
                    days = 1;
                }else{
                    days = (int) foreCastDays.getSelectionModel().getSelectedItem();
                }
                //Ifall det bara är en dag så kommer vi att redirecta till show-wheater-page och endast göra en wb.
                if(days == 1){
                    WeatherBean wb = weatherParser.getWeather(city.getText(),country.getText());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("show-weather-page.fxml"));
                    root = loader.load();
                    WeatherController cwc = loader.getController();
                    cwc.setTextToLabel(wb);

                    stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                //annars redirectar vi och gör en arraylist med days antal beans.
                else{
                    ArrayList<WeatherBean> weatherBeanList = weatherParser.getWeatherForeCast(city.getText(),country.getText(),days);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("show-weather-page.fxml"));
                    root = loader.load();
                    WeatherController cwc = loader.getController();
                    cwc.setTextToLabel(weatherBeanList);
                    stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);

                    stage.show();
                }

            }else{
                //Det är inte bara bokstäver skrivna.
                city.setText("");
                country.setText("");
            }
        }else{
            //Inget är skrivet i text rutorna.
        }


    }

}