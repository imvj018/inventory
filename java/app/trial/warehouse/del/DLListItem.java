package app.trial.warehouse.del;


import androidx.appcompat.app.AppCompatActivity;

public class DLListItem extends AppCompatActivity{
    private String id;
    private String ctime;
    private String dtime;
    private String shiparty;
    private String delnum;

    public DLListItem(String id, String ctime, String dtime,String shiparty,String delnum) {
        this.id = id;
        this.ctime = ctime;
        this.dtime = dtime;
        this.shiparty = shiparty;
        this.delnum = delnum;
    }

    public String getId() {
        return id;
    }

    public String getCtime() {
        return ctime;
    }

    public String getDtime() {
        return dtime;
    }

    public String getShiparty() {
        return shiparty;
    }

    public String getDelnum() {
        return delnum;
    }
}
