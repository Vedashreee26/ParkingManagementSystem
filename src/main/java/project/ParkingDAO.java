package project;
import java.sql.*;
import java.util.ArrayList;


public class ParkingDAO {
	
	public String getAvailableSpace() throws Exception {

        String sql =
            "SELECT SPACE_NO FROM PARKING_SPACE " +
            "WHERE STATUS = 'AVAILABLE' AND ROWNUM = 1";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getString("SPACE_NO");
            }

            return null;
        }
    }

    public void updateSpaceStatus(String spaceNo, String status)
            throws Exception {

        String sql =
            "UPDATE PARKING_SPACE SET STATUS = ? WHERE SPACE_NO = ?";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, spaceNo);

            ps.executeUpdate();
        }
    }

    public int getTotalSpaces() throws Exception {

        String sql = "SELECT COUNT(*) FROM PARKING_SPACE";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            return rs.getInt(1);
        }
    }

    public int getOccupiedSpaces() throws Exception {

        String sql =
            "SELECT COUNT(*) FROM PARKING_SPACE WHERE STATUS = 'OCCUPIED'";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            return rs.getInt(1);
        }
    }

    public int getAvailableSpaces() throws Exception {

        String sql =
            "SELECT COUNT(*) FROM PARKING_SPACE WHERE STATUS = 'AVAILABLE'";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            return rs.getInt(1);
        }
    }

    public ArrayList<ParkingSpaceBean> getAllSpaces() throws Exception {

        ArrayList<ParkingSpaceBean> spaces = new ArrayList<>();

        String sql =
            "SELECT SPACE_NO, STATUS FROM PARKING_SPACE ORDER BY SPACE_NO";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ParkingSpaceBean space = new ParkingSpaceBean();

                space.setSpace_no(rs.getString("SPACE_NO"));
                space.setStatus(rs.getString("STATUS"));

                spaces.add(space);
            }
        }

        return spaces;
    }
}
