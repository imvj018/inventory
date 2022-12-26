package app.trial.warehouse.storagetype;

import androidx.appcompat.app.AppCompatActivity;

public class StListItem extends AppCompatActivity {
    private String strtype;
    private String location;
    private String warehouse;

    public StListItem(String strtype, String location, String warehouse) {
        this.strtype = strtype;
        this.location = location;
        this.warehouse = warehouse;
    }

    public String getStrtype() {
        return strtype;
    }

    public String getLocation() {
        return location;
    }

    public String getWarehouse() {
        return warehouse;
    }
}
