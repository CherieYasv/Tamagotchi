module com.example {
    requires javafx.controls;

    opens org.uabc2109545.Vista to javafx.graphics;
    exports org.uabc2109545.Vista;
}