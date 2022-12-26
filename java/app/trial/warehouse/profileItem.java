package app.trial.warehouse;

public class profileItem {
    private String mId, mFullname, mEmpId, mPhnum, mPword, mAilid, mAccess;


    public profileItem(String Id, String Fullname, String empId, String Phnum, String password, String mailid, String access) {

        mId = Id;
        mFullname = Fullname;
        mEmpId = empId;
        mPhnum = Phnum;
        mPword = password;
        mAilid = mailid;
        mAccess = access;
    }


    public String getId() {
        return mId;
    }

    public String getFullname() {
        return mFullname;
    }

    public String getEmpId() {
        return mEmpId;
    }

    public String getPhnum() {
        return mPhnum;
    }

    public String getpassword() {
        return mPword;
    }
    public String getmailid() {
        return mAilid;
    }
    public String getaccess() {
        return mAccess;
    }


}
