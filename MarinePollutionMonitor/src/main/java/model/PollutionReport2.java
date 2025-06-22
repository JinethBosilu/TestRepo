package model;

import java.time.LocalDate;

public class PollutionReport2 {
    private String type;
    private LocalDate date;
    private double latitude;
    private double longitude;
    private String description;

    public PollutionReport2(String type, LocalDate date, double latitude, double longitude, String description) {
        this.type = type;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }
}
