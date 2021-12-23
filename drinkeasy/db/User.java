package com.example.drinkeasy.db;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

//Serializable로 구현, Parcelable은 어려워서 일단 포기.
public class User implements Serializable {

    public User(){
        for (int i=0; i<5; i++) this.hateIngredientsSet.add(0);
        for (int i=0; i<20; i++) this.hateBrandSet.add(0);
        this.maxStar=5;
    }

    private String nickName;
    private ArrayList<String> reviews = new ArrayList<>(); // reviews의 UID 배열
    private ArrayList<Integer> favorites = new ArrayList<>();  // 음료 _id 배열
    //아래는 개인별 검색기 설정임
    private ArrayList<Integer> hateIngredientsSet = new ArrayList<>();
    private ArrayList<Integer> hateIngredients = new ArrayList<>();  // 성분 _id 배열
    private ArrayList<Integer> hateBrandSet = new ArrayList<>();
    private int minPrice;
    private int maxPrice;
    private int minStar;
    private int maxStar;

    //getter
    public String getNickName() {        return nickName; }
    public ArrayList<String> getReviews() {        return reviews; }
    public ArrayList<Integer> getFavorites() {        return favorites; }
    public ArrayList<Integer> getHateIngredientsSet() {        return hateIngredientsSet; }
    public ArrayList<Integer> getHateIngredients() {        return hateIngredients; }
    public int getMinPrice() {        return minPrice; }
    public int getMaxPrice() {        return maxPrice; }
    public int getMinStar() {        return minStar; }
    public int getMaxStar() {        return maxStar; }
    public ArrayList<Integer> getHateBrandSet() {        return hateBrandSet;    }

    //setter
    public void setNickName(String nickName) {        this.nickName = nickName;    }
    public void setReviews(ArrayList<String> reviews) {        this.reviews = reviews;    }
    public void setFavorites(ArrayList<Integer> favorites) {        this.favorites = favorites;    }
    public void modifyHateIngredientsSet(int index) {
        if (this.hateIngredientsSet.get(index)==0) this.hateIngredientsSet.set(index,1);
        else this.hateIngredientsSet.set(index,0);
    }
    public void modifyHateBrandSet(int index) {
        if (this.hateBrandSet.get(index)==0) this.hateBrandSet.set(index,1);
        else this.hateBrandSet.set(index,0);
    }
    public void addHateIngredients(int value) {        this.hateIngredients.add(value); }
    public void deleteHateIngredients(int value) { this.hateIngredients.remove(this.hateIngredients.indexOf(value));}
    public void setMinPrice(int minPrice) {        this.minPrice = minPrice; }
    public void setMaxPrice(int maxPrice) {        this.maxPrice = maxPrice; }
    public void setMinStar(int minStar) {        this.minStar = minStar; }
    public void setMaxStar(int maxStar) {        this.maxStar = maxStar; }

}
