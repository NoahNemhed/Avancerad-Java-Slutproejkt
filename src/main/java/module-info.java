module com.example.weatherappfinal {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens com.example.weatherappfinal to javafx.fxml;
    exports com.example.weatherappfinal;
}