module com.example.shoppinglist {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.example.shoppinglist to javafx.fxml;
    exports com.example.shoppinglist;
    exports com.example.shoppinglist.model;
    opens com.example.shoppinglist.model to javafx.fxml;
    exports com.example.shoppinglist.exceptions;
    opens com.example.shoppinglist.exceptions to javafx.fxml;
    exports com.example.shoppinglist.controller;
    opens com.example.shoppinglist.controller to javafx.fxml;
}