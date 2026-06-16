module org.travelplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.travelplanner to javafx.fxml;
    exports org.travelplanner;
}