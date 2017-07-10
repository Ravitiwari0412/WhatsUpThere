package com.codingblocks.whatsupthere;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingblocks.whatsupthere.Service.WeatherServiceCallBack;
import com.codingblocks.whatsupthere.Service.YahooWeatherService;
import com.codingblocks.whatsupthere.models.Channel;
import com.codingblocks.whatsupthere.models.Condition;

public class RealActivity extends AppCompatActivity implements WeatherServiceCallBack {

    ImageView icon;
    TextView tvtemp;
    TextView tvcondn;
    TextView tvloctn;
    private YahooWeatherService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real);
        icon=(ImageView)findViewById(R.id.ivIcon);
        tvtemp=(TextView)findViewById(R.id.tvTemp);
        tvcondn=(TextView)findViewById(R.id.tvCondtn);
        tvloctn=(TextView)findViewById(R.id.tvLoctn);
        service=new YahooWeatherService(this);
        service.refreshWeather(getIntent().getStringExtra("location"));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void serviceSuccess(Channel channel) {
        Condition condition=channel.getItem().getCondition();

        int resource=getResources().getIdentifier("drawable/icon"+channel.getItem().getCondition().getCode(),null,getPackageName());
        Drawable drawable= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = getDrawable(resource);
        }
        icon.setImageDrawable(drawable);
        tvcondn.setText(condition.getDescription());
        tvtemp.setText(condition.getTemperature()+"\u00B0"+channel.getUnits().getTemperature());
        tvloctn.setText(service.getLocation());


    }

    @Override
    public void serviceFailure(Exception exception) {

    }
}
