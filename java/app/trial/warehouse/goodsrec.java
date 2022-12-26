package app.trial.warehouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class goodsrec extends AppCompatActivity {
    Button creategr, changegr, dispgr, menu, close;
    ConstraintLayout popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsrec);
        creategr = findViewById(R.id.crgr);
        changegr = findViewById(R.id.chgr);
        dispgr = findViewById(R.id.dgr);
        menu = findViewById(R.id.button);
        popup = findViewById(R.id.popup);
        close = findViewById(R.id.close);
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
        creategr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(goodsrec.this, app.trial.warehouse.gr.creategr.class);
                startActivity(intent);
            }
        });

        changegr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(goodsrec.this, updategr.class);
                startActivity(intent);
            }
        });

        dispgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(goodsrec.this, app.trial.warehouse.gr.dispgr.class);
                startActivity(intent);
            }
        });
    }
}