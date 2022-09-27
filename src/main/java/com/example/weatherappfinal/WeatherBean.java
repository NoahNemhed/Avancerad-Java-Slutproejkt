package com.example.weatherappfinal;

public class WeatherBean {

    private String city;

    private String country;

    private String clouds;

    private String weather;

    private String lastUpdated;

    public WeatherBean(String city, String country) {
        this.city = city;
        this.country = country;

    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public Double getWeather() {
        return Double.parseDouble(weather);
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return
                "city='" + city + '\'' +
                        ", country='" + country + '\'' +
                        ", clouds='" + clouds + '\'' +
                        ", weather='" + weather + '\'' +
                        ", lastUpdated='" + lastUpdated + '\'';
    }
}
