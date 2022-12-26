package app.trial.warehouse.warehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import java.util.List;

public class WhListItem extends AppCompatActivity {
    private String wnumber;
    private String wdesc;

    public WhListItem(String wnumber, String wdesc) {
        this.wnumber = wnumber;
        this.wdesc = wdesc;
    }



    public String getWnumber() {
        return wnumber;
    }

    public String getWdesc() {
        return wdesc;
    }
}
