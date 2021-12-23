package com.example.drinkeasy.db;

import java.util.HashMap;

public class Drink
{
    private int id;
    private String korName;
    private String engName;
    private String Brand;
    private String imgUrl;
    private HashMap <Integer, Integer> ingredients = new HashMap<Integer, Integer>();
    private int cluster;
    private String category;
    private String note;
    private String allergyInfo;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKorName() {
        return korName;
    }

    public void setKorName(String korName) {
        this.korName = korName;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public HashMap<Integer, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashMap<Integer, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAllergyInfo() {
        return allergyInfo;
    }

    public void setAllergyInfo(String allergyInfo) {
        this.allergyInfo = allergyInfo;
    }
}
