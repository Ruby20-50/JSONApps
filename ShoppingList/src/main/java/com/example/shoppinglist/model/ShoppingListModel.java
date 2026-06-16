package com.example.shoppinglist.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

import com.example.shoppinglist.exceptions.ItemNotFoundException;
import org.json.JSONObject;

public class ShoppingListModel {
    private List<ShoppingItem> items;

  public ShoppingListModel(){
      this.items = new ArrayList<>();
  }

  public void addItem(String name){
      items.add(new ShoppingItem(name));
  }
  public void markItemAsDone(String name) throws ItemNotFoundException{
      for(ShoppingItem item : items) {
          if(item.getName().equals(name)){
              item.setIsDone(true);
              return;
          }
      }
      throw new ItemNotFoundException(name + " is not found in the list");
  }
    public void saveToFile(String filePath) throws IOException {
      JSONArray jsonArray = new JSONArray();
      for(ShoppingItem item : items){
          JSONObject jItem = new JSONObject();
          jItem.put("name", item.getName());
          jItem.put("isDone", item.getIsDone());
          jsonArray.put(jItem);
      }
      try(FileWriter writer = new FileWriter(filePath)){
          writer.write(jsonArray.toString(2));
          writer.flush();
      }
    }
    public void loadFromFile(String filePath) throws IOException{
        StringBuilder content = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath))
        ) ){
            String line;
            while((line = reader.readLine()) != null){
                content.append(line);
            }
        }
        items.clear();
        JSONArray jsonArray = new JSONArray(content.toString());
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jItem = jsonArray.getJSONObject(i);
            ShoppingItem item = new ShoppingItem(jItem.getString("name"));
            item.setIsDone(jItem.getBoolean("isDone"));
            items.add(item);
        }
    }
    public List<ShoppingItem> getItems(){
      return items;
    }
}
