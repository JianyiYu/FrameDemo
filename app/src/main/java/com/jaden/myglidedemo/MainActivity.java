package com.jaden.myglidedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jaden.myglidedemo.myglide.MyGlide;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDownLoad;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDownLoad = findViewById(R.id.btn_download);
        imgView = findViewById(R.id.img_download);

        btnDownLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MyGlide.with(this).load("http://pic17.nipic.com/20111101/3094309_171235018306_2.jpg").into(imgView);
    }
}
