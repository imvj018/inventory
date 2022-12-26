package app.trial.warehouse.vendor;


import androidx.appcompat.app.AppCompatActivity;

public class VnListItem extends AppCompatActivity {
    private String vcode;
    private String vdesc;

    public VnListItem(String vcode, String vdesc) {
        this.vcode = vcode;
        this.vdesc = vdesc;
    }

    public String getVcode() {
        return vcode;
    }

    public String getVdesc() {
        return vdesc;
    }
}
