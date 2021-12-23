package com.example.drinkeasy.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.drinkeasy.R;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class IngredientsAccess  extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "drinks.db";
    private static final int DATABASE_VERSION = 1;
    static IngredientsAccess instance;

    private static int[] images = {
            R.drawable.ing0,R.drawable.ing1,R.drawable.ing2,R.drawable.ing3,R.drawable.ing4,
            R.drawable.ing5,R.drawable.ing6,R.drawable.ing7,R.drawable.ing8,R.drawable.ing9,
            R.drawable.ing10,R.drawable.ing11,R.drawable.ing12,R.drawable.ing13,R.drawable.ing14,
            R.drawable.ing15,R.drawable.ing16,R.drawable.ing17,R.drawable.ing18,R.drawable.ing19,
            R.drawable.ing20,R.drawable.ing21,R.drawable.ing22,R.drawable.ing23,R.drawable.ing24,
            R.drawable.ing25,R.drawable.ing26,R.drawable.ing27,R.drawable.ing28,R.drawable.ing29,
            R.drawable.ing30,R.drawable.ing31,R.drawable.ing32,R.drawable.ing33,R.drawable.ing34,
            R.drawable.ing35,R.drawable.ing36,R.drawable.ing37,R.drawable.ing38,R.drawable.ing39,
            R.drawable.ing40,R.drawable.ing41,R.drawable.ing42,R.drawable.ing43,R.drawable.ing44,
            R.drawable.ing45,R.drawable.ing46,R.drawable.ing47,R.drawable.ing48,R.drawable.ing49,
            R.drawable.ing50,R.drawable.ing51,R.drawable.ing52,R.drawable.ing53,R.drawable.ing54,
            R.drawable.ing55,R.drawable.ing56,R.drawable.ing57,R.drawable.ing58,R.drawable.ing59,
            R.drawable.ing60,R.drawable.ing61,R.drawable.ing62,R.drawable.ing63,R.drawable.ing64,
            R.drawable.ing65,R.drawable.ing66,R.drawable.ing67,R.drawable.ing68,R.drawable.ing69,
            R.drawable.ing70,R.drawable.ing71,R.drawable.ing72,R.drawable.ing73,R.drawable.ing74,
            R.drawable.ing75,R.drawable.ing76,R.drawable.ing77,R.drawable.ing78,R.drawable.ing79
    };

    //생성자
    private IngredientsAccess(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super( context, name, factory, version );
    }

    //실제로 사용되는 생성자
    public static IngredientsAccess getInstance(Context context){
        if(instance == null){
            instance = new IngredientsAccess(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        return instance;
    }

    // 모든 음료 데이터를 list로 리턴하는 메소드.
    public ArrayList<Ingredient> getAllData() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT * FROM INGREDIENTS ");
        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Cursor cursor = db.rawQuery(sb.toString(), null);
        // moveToNext 다음에 데이터가 있으면 true 없으면 false
        while( cursor.moveToNext() ) {
            //TODO:: 추후 2열에 있는 데이터 활용해서 검색
            Ingredient ingredient = new Ingredient();
            ingredient.setId(cursor.getInt(0));
            ingredient.setName(cursor.getString(1));
            ingredient.setImage(images[cursor.getInt(0)]);
            ingredients.add(ingredient);
            }
        return ingredients;
    }

    public ArrayList<Ingredient> getSomeData(ArrayList<Integer> data) {

        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Ingredient> ingredients = new ArrayList<>();


        for (int i : data) {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT * FROM INGREDIENTS WHERE _id ="+Integer.toString(i));
            Cursor cursor = db.rawQuery(sb.toString(), null);
            // moveToNext 다음에 데이터가 있으면 true 없으면 false
            while (cursor.moveToNext()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(cursor.getInt(0));
                ingredient.setName(cursor.getString(1));
                ingredient.setImage(images[cursor.getInt(0)]);
                ingredients.add(ingredient);
            }
        }
        return ingredients;
    }


}

