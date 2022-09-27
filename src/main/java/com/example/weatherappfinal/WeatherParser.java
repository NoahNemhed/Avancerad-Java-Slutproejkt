package com.example.weatherappfinal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.*;

import com.example.weatherappfinal.WeatherBean;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.util.ArrayList;

public class WeatherParser {

    protected String API_KEY = "bad24bb89282a1a8c66b9b7fe4b8f604";

    //Method to get Current weather as XML.
    public WeatherBean getWeather(String city, String country) throws IOException {
        //Create a weatherBean object.
        WeatherBean weatherBean = new WeatherBean(city,country);
        //URL to fetch
        String apiURL = "http://api.openweathermap.org/data/2.5/weather?q=" + weatherBean.getCity() + ","
                + weatherBean.getCountry() + "&APPID=" + API_KEY + "&mode=xml";

        //URL
        URL url = new URL(apiURL);

        //Requsting URL
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        //Read response
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

        //String to save each line from response
        String temp = "";

        //String to save all lines from response
        String ApiResponse = "";
        //Loop through response
        while ((temp = br.readLine()) != null) {
            ApiResponse += temp;
        }
        System.out.println(ApiResponse);
        //Close BufferedReader
        br.close();
        //Create a xmlDoc from String
        Document doc = convertStringToXml(ApiResponse);
        doc.getDocumentElement().normalize();


        //Create NodeList that gets everything under last updated
        NodeList nodeList1 = doc.getElementsByTagName("lastupdate");
        for(int i = 0; i<nodeList1.getLength(); i++){
            // Save node of the current list id
            Node node = nodeList1.item(i);
            // set the current node as an Element
            Element element = (Element) node;
            // get the content of an attribute in element and save it
            String lastUpdate = element.getAttribute("value");
            //Removes "T" from String
            String[] ar = lastUpdate.split("T");
            //Updates format of String
            lastUpdate = ar[0] + " " + ar[1];
            //update bean
            weatherBean.setLastUpdated(lastUpdate);
        }


        //Create NodeList that gets everything under clouds
        NodeList nodeList2 = doc.getElementsByTagName("clouds");
        for (int i = 0; i < nodeList2.getLength(); i++) {
            // Save node of the current list id
            Node node = nodeList2.item(i);
            // set the current node as an Element
            Element eElement = (Element) node;
            // get the content of an attribute in element and save it
            String clouds = eElement.getAttribute("name");
            //update bean
            weatherBean.setClouds(clouds);

        }

        //Create NodeList that gets everything under temperature
        NodeList nodeList3 = doc.getElementsByTagName("temperature");

        Node node = nodeList3.item(0);

        // set the current node as an Element
        Element eElement = (Element) node;
        // get the content of an attribute in element
        String weather = eElement.getAttribute("value");
        //Convert from Kelvin to Celsius and save it
        double celsius = Math.round(Double.parseDouble(weather) - 273.15);
        //update bean
        weatherBean.setWeather(String.valueOf(celsius));



        return weatherBean;

    }

    //Method to get weather forecast as XML
    public ArrayList<WeatherBean> getWeatherForeCast(String city, String country, int amount) throws IOException {
        //Creating arrayList to store weatherBeans.
        ArrayList<WeatherBean> weatherBeans = new ArrayList<>();

        //Arrays to store data
        String[] weather = new String[amount];
        String[] clouds = new String[amount];
        String[] date = new String[amount];

        //URL to fetch
        String apiURL = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + country + "&appid=" + API_KEY + "&mode=xml";

        //URL
        URL url = new URL(apiURL);

        //Requsting URL
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        //Read response
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

        //String to save each line from response
        String temp = "";

        //String to save all lines from response
        String apiResponse = "";
        //Loop through response
        while ((temp = br.readLine()) != null) {
            apiResponse += temp;
        }

        //Close BufferedReader
        br.close();
        System.out.println(apiResponse);

        //Create a xmlDoc from String
        Document doc = convertStringToXml(apiResponse);
        doc.getDocumentElement().normalize();


        //Save clouds to array.
        NodeList nodeList = doc.getElementsByTagName("clouds");
        for(int i = 0; i<amount; i++){
            Node node = nodeList.item(i);
            Element element = (Element) node;
            String cloud = element.getAttribute("value");
            clouds[i] = cloud;
        }


        //Save temperatures to array.
        NodeList nodeList1 = doc.getElementsByTagName("temperature");
        for(int i = 0; i<amount; i++){
            // Save node of the current list id
            Node node = nodeList1.item(i);
            // set the current node as an Element
            Element element = (Element) node;
            // get the content of an attribute in element and save it
            temp = element.getAttribute("value");
            double temperature = Math.round(Double.parseDouble(temp) - 273.15);
            weather[i] = String.valueOf(temperature);
        }


        //Save day to array.
        NodeList nodeList2 = doc.getElementsByTagName("time");
        for(int i = 0; i<amount; i++){
            // Save node of the current list id
            Node node = nodeList2.item(i);
            // set the current node as an Element
            Element element = (Element) node;
            // get the content of an attribute in element and save it
            String[] day = element.getAttribute("from").split("T");
            date[i] = day[1];

        }

        //Create amount of objects and add them to ArrayList
        for(int i = 0; i<amount; i++){
            WeatherBean weatherBean = new WeatherBean(city,country);
            weatherBean.setLastUpdated(date[i]);
            weatherBean.setClouds(clouds[i]);
            weatherBean.setWeather(weather[i]);

            weatherBeans.add(weatherBean);
        }



        return weatherBeans;
    }



    //Method to convert String to an XML document so we can parse the data.
    private static Document convertStringToXml(String xmlString) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = dbf.newDocumentBuilder();

            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));

            return doc;
        }
        catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

    }

}