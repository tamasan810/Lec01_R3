package com.example.admin.lec01;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Lec01 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawCanvas drawCanvas = new DrawCanvas(this);
        setContentView(drawCanvas);
//        setContentView(new DrawCanvas(this));
    }
}
