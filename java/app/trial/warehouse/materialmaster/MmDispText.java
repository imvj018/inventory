package app.trial.warehouse.materialmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import app.trial.warehouse.R;


public class MmDispText extends AppCompatActivity {
    private TextView head, desc, desc1,desc2,desc3,desc4,desc5;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm_disp_text);
        head = findViewById(R.id.dltextview1);
        desc = findViewById(R.id.dltextview2);
        desc1 = findViewById(R.id.dltextview3);
        desc2 = findViewById(R.id.dltextview4);
        desc3 = findViewById(R.id.dltextview5);
        desc4 = findViewById(R.id.dltextview6);
        desc5 = findViewById(R.id.dltextview7);

        head.setText("Material : " +  getIntent().getStringExtra("matcode")+" ("+getIntent().getStringExtra("matdesc")+")");
        desc.setText("Material Group : " + getIntent().getStringExtra("matgrp"));
        desc1.setText("UOM : " + getIntent().getStringExtra("buom"));
        desc2.setText("Location No : " + getIntent().getStringExtra("locnumber"));
        desc3.setText("Inbound Room : " + getIntent().getStringExtra("ibroomtype"));
        desc4.setText("Outbound Room : " + getIntent().getStringExtra("obroomtype"));
        desc5.setText("Warehouse Number : " + getIntent().getStringExtra("wnumber"));
    }
}