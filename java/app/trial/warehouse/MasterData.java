package app.trial.warehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MasterData extends AppCompatActivity {
    Button matmas, matgrp, cust, ven, uom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_data);
        matmas = findViewById(R.id.matmas);
        matgrp = findViewById(R.id.matgrp);
        cust = findViewById(R.id.cust);
        ven = findViewById(R.id.ven);
        uom = findViewById(R.id.uom);
        matmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterData.this, app.trial.warehouse.materialmaster.MmDispShow.class);
                startActivity(intent);
            }
        });
        matgrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterData.this, app.trial.warehouse.materialgroup.MgDispShow.class);
                startActivity(intent);
            }
        });
        cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterData.this, app.trial.warehouse.customer.CusDispShow.class);
                startActivity(intent);
            }
        });
        ven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterData.this, app.trial.warehouse.vendor.VenDispShow.class);
                startActivity(intent);
            }
        });
        uom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasterData.this, app.trial.warehouse.unitofmeasure.UomDispShow.class);
                startActivity(intent);
            }
        });
    }
}