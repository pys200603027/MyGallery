package com.sample.android.mygallary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sample.android.lib.ui.list.PhotoListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn).setOnClickListener(v -> {

            Intent intent = new Intent(this, PhotoListActivity.class);
            startActivity(intent);
        });
    }
}
