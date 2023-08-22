module com.example.observablelists {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;


    opens com.example.observablelists to javafx.fxml;
    exports com.example.observablelists;
}