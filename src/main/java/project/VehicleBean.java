package project;

import java.io.Serializable;

public class VehicleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String V_no;
    private String u_name;
    private String v_type;

    public VehicleBean() {
    }

    public VehicleBean(String V_no, String u_name, String v_type) {
        this.V_no = V_no;
        this.u_name = u_name;
        this.v_type = v_type;
    }

    public String getV_no() {
        return V_no;
    }

    public void setV_no(String v_no) {
        V_no = v_no;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getV_type() {
        return v_type;
    }

    public void setV_type(String v_type) {
        this.v_type = v_type;
    }
}