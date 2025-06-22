package Database;

import java.sql.*;
import model.PollutionReport;

public class ReportService {

    public static boolean saveReport(PollutionReport report) {
        String sql = "INSERT INTO pollution_reports (type, date, location, description) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, report.getType());
            stmt.setDate(2, Date.valueOf(report.getDate()));
            stmt.setString(3, report.getLocation());
            stmt.setString(4, report.getDescription());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
