package app.trial.warehouse.materialgroup;


import androidx.appcompat.app.AppCompatActivity;

public class MgItemList extends AppCompatActivity {
    private String matcode;
    private String matdesc;
    private String matgrp;
    private String buom;
    private String locnum;
    private String ibtype;
    private String obtype;
    private String wnumber;

    public MgItemList(String matcode, String matdesc, String matgrp, String buom, String locnum, String ibtype, String obtype, String wnumber) {
        this.matcode = matcode;
        this.matdesc = matdesc;
        this.matgrp = matgrp;
        this.buom = buom;
        this.locnum = locnum;
        this.ibtype = ibtype;
        this.obtype = obtype;
        this.wnumber = wnumber;
    }

    public String getMatcode() {
        return matcode;
    }

    public String getMatdesc() {
        return matdesc;
    }

    public String getMatgrp() {
        return matgrp;
    }

    public String getBuom() {
        return buom;
    }

    public String getLocnum() {
        return locnum;
    }

    public String getIbtype() {
        return ibtype;
    }

    public String getObtype() {
        return obtype;
    }

    public String getWnumber() {
        return wnumber;
    }
}
