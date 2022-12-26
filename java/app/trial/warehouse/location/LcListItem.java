package app.trial.warehouse.location;

import androidx.appcompat.app.AppCompatActivity;

public class LcListItem extends AppCompatActivity {
    private String location;
    private String locdesc;
    private String whouse;

    public LcListItem(String location, String locdesc, String whouse) {
        this.location = location;
        this.locdesc = locdesc;
        this.whouse = whouse;
    }

    public String getLocation() {
        return location;
    }

    public String getLocdesc() {
        return locdesc;
    }

    public String getWhouse() {
        return whouse;
    }
}
