package app.trial.warehouse.materialgroup;

public class MgListItem {
    private String grp;
    private String desc;


    public MgListItem(String grp, String desc) {
        this.grp = grp;
        this.desc = desc;
    }

    public String getGrp() {
        return grp;
    }

    public String getDesc() {
        return desc;
    }
}
