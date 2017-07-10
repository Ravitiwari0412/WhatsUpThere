package com.codingblocks.whatsupthere.models;

import org.json.JSONObject;

/**
 * Created by buck on 7/10/2017.
 */

public class Condition implements JSONPredicter {

    private int Code;
    private  int temperature;
    private String description;

    public int getCode() {
        return Code;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void predict(JSONObject data) {
        Code=data.optInt("code");
        temperature=data.optInt("temp");
        description=data.optString("text");

    }
}
