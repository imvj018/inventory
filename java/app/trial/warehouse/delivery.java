package app.trial.warehouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class delivery extends AppCompatActivity {
    Button createdel, changedel, dispdel, menu, close;
    ConstraintLayout popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        createdel = findViewById(R.id.crdel);
        changedel = findViewById(R.id.chdel);
        dispdel = findViewById(R.id.dispdel);
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

        createdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(delivery.this, app.trial.warehouse.del.createdel.class);
                startActivity(intent);
            }
        });
        dispdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(delivery.this, app.trial.warehouse.del.DLDisplay.class);
                startActivity(intent);
            }
        });
    }
}