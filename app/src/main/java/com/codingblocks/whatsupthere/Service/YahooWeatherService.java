package com.codingblocks.whatsupthere.Service;

import android.net.Uri;
import android.os.AsyncTask;

import com.codingblocks.whatsupthere.models.Channel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by buck on 7/10/2017.
 */

public class YahooWeatherService  {
    private WeatherServiceCallBack callBack;



    private String location;
    private Exception error;
    private String temperatureUnit="C";

    public YahooWeatherService(WeatherServiceCallBack callBack) {
        this.callBack = callBack;
    }

    public String getLocation() {
        return location;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public void refreshWeather(String location){
        this.location=location;
        new AsyncTask<String, Void, Channel>() {


            @Override
            protected Channel doInBackground(String[] locations) {

                String location = locations[0];

                Channel channel = new Channel();

                String unit = getTemperatureUnit().equalsIgnoreCase("f") ? "f" : "c";

                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='" + unit + "'", location);

                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();
                    connection.setUseCaches(false);

                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    reader.close();

                    JSONObject data = new JSONObject(result.toString());

                    JSONObject queryResults = data.optJSONObject("query");

                    int count = queryResults.optInt("count");

                    if (count == 0) {
                        error = new LocationWeatherException("No weather information found for " + location);
                        return null;
                    }

                    JSONObject channelJSON = queryResults.optJSONObject("results").optJSONObject("channel");
                    channel.predict(channelJSON);

                    return channel;

                } catch (Exception e) {
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Channel channel) {

                if (channel == null && error != null) {
                    callBack.serviceFailure(error);
                } else {
                    callBack.serviceSuccess(channel);
                }

            }

        }.execute(location);
    }
    private class LocationWeatherException extends Exception {
        LocationWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }


}
