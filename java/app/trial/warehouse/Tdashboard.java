package app.trial.warehouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import app.trial.warehouse.wmstock.wmstock;

public class Tdashboard extends AppCompatActivity {
    Button menu, close, gr, delivery, stockov, whouse, master;
    ConstraintLayout popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tdashboard);
        menu = findViewById(R.id.button);
        popup = findViewById(R.id.popup);
        close =  findViewById(R.id.close);
        gr = findViewById(R.id.button2);
        delivery = findViewById(R.id.button4);
        stockov = findViewById(R.id.button6);
        whouse = findViewById(R.id.configbutton);
        master = findViewById(R.id.masterbutton);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.setVisibility(View.VISIBLE);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.setVisibility(View.INVISIBLE);
            }
        });
        gr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tdashboard.this, goodsrec.class);
                startActivity(intent);
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tdashboard.this, delivery.class);
                startActivity(intent);
            }
        });
        stockov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tdashboard.this, wmstock.class);
                startActivity(intent);
            }
        });
        whouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tdashboard.this, app.trial.warehouse.warehouse.WhDispShow.class);
                startActivity(intent);
            }
        });
        master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tdashboard.this, MasterData.class);
                startActivity(intent);
            }
        });
    }
}