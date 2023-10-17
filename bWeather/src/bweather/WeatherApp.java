package bweather;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {
    public static JSONObject getWeatherData(String locationName){
            //fetch the location name
           JSONObject location = getData(locationName,1,1,1);

           //get the longitude and altitude
           double longitude = (double) location.get("longitude");
           double latitude = (double) location.get("latitude");

            //fetch the weather data
           JSONObject WeatherData = getData(locationName,2,latitude,longitude);

            //get the weather description
            String temp ;
            // get the temperature
            temp = WeatherData.get("temperature_2m").toString();
            int temp_int = (int) Double.parseDouble(temp);

            String weatherDes = "";
            String weatherDesCode;
            //get the weather description
            weatherDesCode =  WeatherData.get("weathercode").toString();
            weatherDes = AnalyzCode(weatherDesCode);

            //get the humidity
            String humidity = WeatherData.get("relativehumidity_2m").toString();
            // get the wind Speed
            String windSpeed = WeatherData.get("windspeed_10m").toString();

            //System.out.println("The temperature is "+ temp+ " and the condition is "+weatherDes+" the windspeed is "+windSpeed+" and the humidity is "+ humidity);
            // send the final object to the main method
            JSONObject finalData = new JSONObject();
            finalData.put("Temp",temp_int); finalData.put("Desc",weatherDes); finalData.put("Hum",humidity); finalData.put("Wind",windSpeed);
            finalData.put("City",locationName);
            return finalData;

    }

    private static String AnalyzCode(String weatherDesCode) {
        switch (weatherDesCode){
            case "0": return "Clear";
            case "1": return "Mainly clear";
            case "2": return "Partly cloudy";
            case "3": return "Overcast";
            case "61": return "Slight Rain";
            case "63": return "Moderate Rain";
            case "35": return "Heavy Rain";
            case "80": return "Slight Rain Showers";
            case "81": return "Moderate Rain Showers";
            case "82": return "Heavy Rain Showers";
            default: return "Unknown";
        }
    }

    public static JSONObject getData(String locationName, int define, double latitude, double longitude){
        String urlString="";
        if(define == 1){

            // replace all white spaces in the location name with "+"
            locationName = locationName.replace(" ","+");

            // build the url using the location name so we can all the API
             urlString = "https://geocoding-api.open-meteo.com/v1/search?name="+locationName+"&count=10&language=en&format=json";

        }
        else {
            urlString = "https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+longitude+"&current=temperature_2m,relativehumidity_2m,is_day,rain,weathercode,windspeed_10m&hourly=temperature_2m,relativehumidity_2m,weathercode,windspeed_10m&timezone=Europe%2FBerlin";
        }



        // make an HTTP Client to make HTTP Request
        try {
            HttpsURLConnection conn = fetchApiResponse(urlString);
            if(conn.getResponseCode() != 200){
                System.out.println("Could not connect to the API");
                return null;}
            else{
                    StringBuilder resultJSON = new StringBuilder();
                    Scanner scan = new Scanner(conn.getInputStream());
                    while (scan.hasNext()){
                        resultJSON.append((scan.nextLine()));
                    }
                    scan.close();
                    conn.disconnect();

                    //Parse the JSON STRING into a JSON OBJECT
                    JSONParser parser = new JSONParser();
                    JSONObject resultsJsonObject = (JSONObject) parser.parse(String.valueOf(resultJSON));



                    // get the list of location data
                    if(define ==1){
                        return getLocationArray(resultsJsonObject);

                    }
                    else {
                         return getWeatherArray(resultsJsonObject);
                    }

                }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    return null;
    }

    private static HttpsURLConnection fetchApiResponse(String urlString){
        try{
            URL url  = new URL(urlString);
            HttpsURLConnection  conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            return conn;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private static JSONObject getLocationArray(JSONObject OBJ){
        JSONArray Data = (JSONArray) OBJ.get("results");
        JSONObject dData = (JSONObject) Data.get(0);
        return dData;
    }

    public static JSONObject getWeatherArray(JSONObject OBJ){
        JSONObject Data = (JSONObject) OBJ.get("current");
        return Data;
    }


}
