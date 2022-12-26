package app.trial.warehouse.gr;

public class dgrItem {

    private String mId, mGrn, mVendor, mWarehouse;

    public dgrItem(String id, String grn, String vendor, String warehouse) {
        mId = id;
        mGrn = grn;
        mVendor = vendor;
        mWarehouse = warehouse;


    }


    public String getId() {
        return mId;
    }

    public String getGrn() {
        return mGrn;
    }

    public String getVendor() {
        return mVendor;
    }

    public String getWarehouse() {
        return mWarehouse;
    }

}
