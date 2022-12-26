package app.trial.warehouse.wmstock;

public class wmstockItem {
    private String xId, xgrncopy, xmaterial, xmatdesc, xquantity, xuom, xlocation, xstoragetype;

    public wmstockItem(String id, String grncopy, String material, String matdesc, String quantity, String uom, String location, String storage) {
        xId = id;
        xgrncopy = grncopy;
        xmaterial = material;
        xmatdesc = matdesc;
        xquantity = quantity;
        xuom = uom;
        xlocation = location;
        xstoragetype = storage;

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

    public String getmatdesc() {
        return xmatdesc;
    }

    public String getquantity() {
        return xquantity;
    }

    public String getuom() {
        return xuom;
    }

    public String getlocation() {
        return xlocation;
    }

    public String getstoragetype() {
        return xstoragetype;
    }

}
