package project;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ParkingSpaceBean implements Serializable {
	private String space_no;
	private String status;
	public String getSpace_no() {
		return space_no;
	}
	public void setSpace_no(String space_no) {
		this.space_no = space_no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
