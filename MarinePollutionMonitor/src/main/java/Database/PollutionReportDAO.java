package Database;

import model.PollutionReport2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PollutionReportDAO {

    public List<PollutionReport2> fetchAllReports() {
        List<PollutionReport2> reports = new ArrayList<>();
        String query = "SELECT type, date, location, description FROM pollution_reports";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("type");
                LocalDate date = rs.getDate("date").toLocalDate();
                String loc = rs.getString("location");
                String description = rs.getString("description");

                // location is in "lat,lng" format — split it
                String[] parts = loc.split(",");
                double lat = Double.parseDouble(parts[0]);
                double lng = Double.parseDouble(parts[1]);

                reports.add(new PollutionReport2(type, date, lat, lng, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reports;
    }
}
