package app.trial.warehouse.gr;

public class dgr2Item {
    private String xId, xgrncopy, xmaterial, xmatdesc, xquantity, xuom;

    public dgr2Item(String id, String grncopy, String material,  String matedesc, String quantity, String uom) {
        xId = id;
        xgrncopy = grncopy;
        xmaterial = material;
        xmatdesc = matedesc;
        xquantity = quantity;
        xuom = uom;

    }

    public String getId() {
        return xId;
    }

    public String getgrncopy() {
        return xgrncopy;
    }

    public String getmaterial() {
        return xmaterial;
    }
    public String getmatedesc() {
        return xmatdesc;
    }

    public String getquantity() {
        return xquantity;
    }

    public String getuom() {
        return xuom;
    }

}

