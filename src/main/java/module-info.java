module com.example.yuniavia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.yuniavia.Server to javafx.fxml;
    exports com.example.yuniavia.Client;
    exports com.example.yuniavia.Server;
   // opens com.example.yuniavia.Server to javafx.fxml;
    opens com.example.yuniavia.Client to javafx.fxml;
    exports com.example.yuniavia.db;
    opens com.example.yuniavia.db to javafx.fxml;
    exports com.example.yuniavia.Client.ClientHandlerClasses;
    opens com.example.yuniavia.Client.ClientHandlerClasses to javafx.fxml;
}