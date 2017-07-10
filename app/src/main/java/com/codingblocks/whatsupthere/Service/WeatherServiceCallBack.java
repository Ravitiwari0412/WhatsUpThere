package com.codingblocks.whatsupthere.Service;

import com.codingblocks.whatsupthere.models.Channel;

/**
 * Created by buck on 7/10/2017.
 */

public interface WeatherServiceCallBack {
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
