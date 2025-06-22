package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12778973";
    private static final String USER = "sql12778973";
    private static final String PASSWORD = "S6vxzgfCrK";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static int getReportCount() {
    String query = "SELECT COUNT(*) FROM pollution_reports";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {
        return rs.next() ? rs.getInt(1) : 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return 0;
    }
}

    public static int getPollutionTypeCount(String type) {
    String query = "SELECT COUNT(*) FROM pollution_reports WHERE LOWER(type) = LOWER(?)";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, type);
        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getInt(1) : 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return 0;
    }
}


}


