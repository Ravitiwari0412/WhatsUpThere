package com.codingblocks.whatsupthere.models;

import org.json.JSONObject;

/**
 * Created by buck on 7/10/2017.
 */

public class Units implements JSONPredicter {

    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void predict(JSONObject data) {
        temperature=data.optString("temperature");

    }
}
