package com.example.shoppinglist.model;

public class ShoppingItem {

    private String name;
    private boolean isDone;

    public ShoppingItem(String name){
        this.name = name;
        this.isDone = false;
    }
    public String getName(){
        return name;
    }
    public boolean getIsDone(){
        return isDone;
    }
    public void setIsDone(boolean isDone){
        this.isDone = isDone;
    }

}
