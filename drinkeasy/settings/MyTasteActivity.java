package com.example.drinkeasy.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.drinkeasy.R;

public class MyTasteActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_taste);
//처음 들어오면 최초 접근인지 여부에 따라서 상단바 표시여부를 결정한다.
        intent = getIntent();
        int beforeAct = intent.getIntExtra("beforeActivity",0);




    }
}
