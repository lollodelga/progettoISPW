module ldg.progettoispw {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.management;


    opens ldg.progettoispw to javafx.fxml;
    exports ldg.progettoispw;
    exports ldg.progettoispw.View;
    opens ldg.progettoispw.View to javafx.fxml;
}