package com.codingblocks.whatsupthere.models;

import org.json.JSONObject;

/**
 * Created by buck on 7/10/2017.
 */

public class Item implements JSONPredicter {

    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void predict(JSONObject data) {
        condition=new Condition();
        condition.predict(data.optJSONObject("condition"));

    }
}
