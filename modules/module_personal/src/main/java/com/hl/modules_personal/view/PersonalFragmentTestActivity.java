package com.hl.modules_personal.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hl.modules_personal.R;


public class PersonalFragmentTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_fragment_test);

        // 加入测试碎片
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.apft_contentFcv, PersonalFragment.newInstance("param1", "param2"))
                .commit();
    }
}
