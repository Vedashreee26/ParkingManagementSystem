package project;

import java.sql.Timestamp;

public class EntryRecordBean {

    private String recordid;
    private String v_no;
    private String space_no;
    private Timestamp EntryTime;
    private Timestamp ExitTime;

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public String getV_no() {
        return v_no;
    }

    public void setV_no(String v_no) {
        this.v_no = v_no;
    }

    public String getSpace_no() {
        return space_no;
    }

    public void setSpace_no(String space_no) {
        this.space_no = space_no;
    }

    public Timestamp getEntryTime() {
        return EntryTime;
    }

    public void setEntryTime(Timestamp entryTime) {
        EntryTime = entryTime;
    }

    public Timestamp getExitTime() {
        return ExitTime;
    }

    public void setExitTime(Timestamp exitTime) {
        ExitTime = exitTime;
    }
}