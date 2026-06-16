package com.example.shoppinglist.controller;

import com.example.shoppinglist.exceptions.ItemNotFoundException;
import com.example.shoppinglist.model.ShoppingItem;
import com.example.shoppinglist.model.ShoppingListModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class ShoppingListController {
    private ShoppingListModel model;
    @FXML
    private ListView<String> itemListView;
    @FXML
    private TextField txt;

    @FXML
    public void initialize(){
        model = new ShoppingListModel();
        updateListView();

    }
    @FXML
    public void onAddItem(){
        try{String itemName = txt.getText().trim();
        if(!itemName.isEmpty()){
     model.addItem(itemName);
     txt.clear();
     updateListView();
    }}catch(NullPointerException e){
            showError("","",e.getMessage());
        }
    }
    @FXML
    public void onMarkDone() {
        String selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            String itemName;
            if(selectedItem.contains("(Done)")) {
                itemName = selectedItem.replace(" ", "(Done)");
            }else{
                itemName = selectedItem.replace(" (Done)", "");
            }


            try{
                model.markItemAsDone(itemName);
                updateListView();
            }catch(ItemNotFoundException e){
                showError("Error", "Item not Found!", e.getMessage());
            }
        }
    }


    @FXML
    public void onSave(){
        FileChooser file = new FileChooser();
        file.setTitle("Save Shopping List");
        file.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File f = file.showSaveDialog(itemListView.getScene().getWindow());
        if(file != null){
            try{
                model.saveToFile(f.getAbsolutePath());
            }catch(IOException e){
                showError("Saving error", "file could not be saved", e.getMessage());
            }
        }
    }

    @FXML
    public void onLoad(){
        FileChooser fileCh = new FileChooser();
        fileCh.setTitle("load list");
        fileCh.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON File", "*.json")
        );
        File file = fileCh.showOpenDialog(itemListView.getScene().getWindow());
        try{
            model.loadFromFile(file.getAbsolutePath());
            updateListView();
        }catch(IOException e){
            showError("Error in loading","could not load file",e.getMessage());
        }
    }

    private void updateListView(){
        itemListView.getItems().clear();
        for(ShoppingItem item : model.getItems()){
            String displayText = item.getName() + (item.getIsDone()?" (Done)": "" );
            itemListView.getItems().add(displayText);
        }
    }
    private void showError(String title, String headerTxt, String contentTxt){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        alert.setContentText(contentTxt);
        alert.showAndWait();
    }

}