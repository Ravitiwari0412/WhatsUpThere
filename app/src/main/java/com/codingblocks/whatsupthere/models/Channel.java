package com.codingblocks.whatsupthere.models;

import org.json.JSONObject;

/**
 * Created by buck on 7/10/2017.
 */

public class Channel implements JSONPredicter {

    private  Units units;
    private Item item;

    public Units getUnits() {
        return units;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public void predict(JSONObject data) {
        units=new Units();
        units.predict(data.optJSONObject("units"));
        item=new Item();
        item.predict(data.optJSONObject("item"));

    }
}
