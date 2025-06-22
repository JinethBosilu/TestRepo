package model;

import javafx.beans.property.SimpleStringProperty;

public class PollutionReport {
    private final SimpleStringProperty type;
    private final SimpleStringProperty date;
    private final SimpleStringProperty location;
    private final SimpleStringProperty description;

    public PollutionReport(String type, String date, String location, String description) {
        this.type = new SimpleStringProperty(type);
        this.date = new SimpleStringProperty(date);
        this.location = new SimpleStringProperty(location);
        this.description = new SimpleStringProperty(description);
    }

    public String getType() { return type.get(); }
    public String getDate() { return date.get(); }
    public String getLocation() { return location.get(); }
    public String getDescription() { return description.get(); }
}
