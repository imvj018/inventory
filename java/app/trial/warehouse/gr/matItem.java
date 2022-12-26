package app.trial.warehouse.gr;

public class matItem {
    private String xmatcode, xmatdesc, xmatgrp, xbuom, xlocnumber, xibroomtype, xobroomtype, xwnumber;

    public matItem(String matcode, String matdesc, String matgrp, String buom, String locnumber, String ibroomtype, String obroomtype, String wnumber) {
        xmatcode = matcode;
        xmatdesc = matdesc;
        xmatgrp = matgrp;
        xbuom = buom;
        xlocnumber = locnumber;
        xibroomtype = ibroomtype;
        xobroomtype = obroomtype;
        xwnumber = wnumber;

    }

    public String getmatcode() {
        return xmatcode;
    }

    public String getmatdesc() {
        return xmatdesc;
    }

    public String getmatgrp() {
        return xmatgrp;
    }

    public String getbuom() {
        return xbuom;
    }

    public String getlocnumber() {
        return xlocnumber;
    }

    public String getibroomtype() {
        return xibroomtype;
    }

    public String getobroomtype() {
        return xobroomtype;
    }

    public String getwnumber() {
        return xwnumber;
    }


}

