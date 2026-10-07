package project;


import java.sql.*;

public class EntryExitDAO {

    public boolean isVehicleCurrentlyParked(String vNo)
            throws Exception {

        String sql =
            "SELECT RECORDID FROM ENTRY_RECORD " +
            "WHERE V_NO = ? AND EXITTIME IS NULL";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vNo);

            ResultSet rs = ps.executeQuery();

            return rs.next();
        }
    }

    public void createEntry(String recordId,
                            String vNo,
                            String spaceNo)
            throws Exception {

        String sql =
            "INSERT INTO ENTRY_RECORD " +
            "(RECORDID, V_NO, SPACE_NO, ENTRYTIME) " +
            "VALUES (?, ?, ?, SYSDATE)";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, recordId);
            ps.setString(2, vNo);
            ps.setString(3, spaceNo);

            ps.executeUpdate();
        }
    }

    public EntryRecordBean getActiveRecord(String vNo)
            throws Exception {

        String sql =
            "SELECT RECORDID, V_NO, SPACE_NO, ENTRYTIME, EXITTIME " +
            "FROM ENTRY_RECORD " +
            "WHERE V_NO = ? AND EXITTIME IS NULL";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                EntryRecordBean record = new EntryRecordBean();

                record.setRecordid(rs.getString("RECORDID"));
                record.setV_no(rs.getString("V_NO"));
                record.setSpace_no(rs.getString("SPACE_NO"));
                record.setEntryTime(rs.getTimestamp("ENTRYTIME"));
                record.setExitTime(rs.getTimestamp("EXITTIME"));

                return record;
            }

            return null;
        }
    }

    public void updateExit(String recordId)
            throws Exception {

        String sql =
            "UPDATE ENTRY_RECORD " +
            "SET EXITTIME = SYSDATE " +
            "WHERE RECORDID = ?";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, recordId);

            ps.executeUpdate();
        }
    }

    public void createTransaction(String transactionId,
                                   String recordId,
                                   int amount,
                                   String paymentStatus)
            throws Exception {

    	String sql =
    		    "INSERT INTO PARKING_TRANSACTION " +
    		    "(TRANSACTIONID, RECORDID, AMOUNT, PAYMENTSTATUS) " +
    		    "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, transactionId);
            ps.setString(2, recordId);
            ps.setInt(3, amount);
            ps.setString(4, paymentStatus);

            ps.executeUpdate();
        }
    }
}