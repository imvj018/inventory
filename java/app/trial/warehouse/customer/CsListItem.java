package app.trial.warehouse.customer;

import androidx.appcompat.app.AppCompatActivity;

public class CsListItem extends AppCompatActivity {
    public String cusgrp;
    public String cusdesc;

    public CsListItem(String cusgrp, String cusdesc) {
        this.cusgrp = cusgrp;
        this.cusdesc = cusdesc;
    }

    public String getCusgrp() {
        return cusgrp;
    }

    public String getCusdesc() {
        return cusdesc;
    }
}
