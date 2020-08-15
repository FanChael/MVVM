package com.hl.module_location.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hl.module_location.R;
import com.hl.module_location.view.LocationListActivity;

public class LocationCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_create);

        startActivity(new Intent(this, LocationListActivity.class));
    }
}
