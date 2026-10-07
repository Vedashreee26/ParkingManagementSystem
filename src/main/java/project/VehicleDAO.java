package project;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    public boolean vehicleExists(String vNo) throws Exception {

        String sql = "SELECT V_NO FROM VEHICLE WHERE V_NO = ?";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vNo);

            ResultSet rs = ps.executeQuery();

            return rs.next();
        }
    }

    public void addVehicle(VehicleBean vehicle) throws Exception {

        String sql = "INSERT INTO VEHICLE (V_NO, U_NAME, V_TYPE) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vehicle.getV_no());
            ps.setString(2, vehicle.getU_name());
            ps.setString(3, vehicle.getV_type());

            ps.executeUpdate();
        }
    }

    public ArrayList<VehicleBean> getAllVehicles() throws Exception {

        ArrayList<VehicleBean> vehicles = new ArrayList<>();

        String sql = "SELECT V_NO, U_NAME, V_TYPE FROM VEHICLE ORDER BY V_NO";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                VehicleBean v = new VehicleBean();

                v.setV_no(rs.getString("V_NO"));
                v.setU_name(rs.getString("U_NAME"));
                v.setV_type(rs.getString("V_TYPE"));

                vehicles.add(v);
            }
        }

        return vehicles;
    }
}