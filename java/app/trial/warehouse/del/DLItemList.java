package app.trial.warehouse.del;


import androidx.appcompat.app.AppCompatActivity;

public class DLItemList extends AppCompatActivity {
    private String idc;
    private String delnum;
    private String matnum;
    private String matdesc;
    private String quantity;
    private String uom;
    private String locnum;

    public DLItemList(String idc, String delnum, String matnum, String matdesc, String quantity, String uom, String locnum) {
        this.idc = idc;
        this.delnum = delnum;
        this.matnum = matnum;
        this.matdesc = matdesc;
        this.quantity = quantity;
        this.uom = uom;
        this.locnum = locnum;
    }

    public String getIdc() {
        return idc;
    }

    public String getDelnum() {
        return delnum;
    }

    public String getMatnum() {
        return matnum;
    }

    public String getMatdesc() {
        return matdesc;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUom() {
        return uom;
    }

    public String getLocnum() {
        return locnum;
    }

}
