module org.example.toylanguagecountsemaphore {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens Domain.Statements to javafx.fxml;
    opens Controller to javafx.fxml;
    opens Domain to javafx.fxml;
    opens View.GUI to javafx.fxml;
    exports View.GUI;
}
