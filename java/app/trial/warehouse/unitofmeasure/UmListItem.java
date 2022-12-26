package app.trial.warehouse.unitofmeasure;

import androidx.appcompat.app.AppCompatActivity;

public class UmListItem extends AppCompatActivity {
    private String umid;
    private String umdesc;

    public UmListItem(String umid, String umdesc) {
        this.umid = umid;
        this.umdesc = umdesc;
    }

    public String getUmid() {
        return umid;
    }

    public String getUmdesc() {
        return umdesc;
    }
}
