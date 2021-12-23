package com.example.drinkeasy.settings;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ToggleButton;

import com.example.drinkeasy.R;
import com.example.drinkeasy.IngredientSearchAdapter;
import com.example.drinkeasy.db.Ingredient;
import com.example.drinkeasy.db.IngredientsAccess;
import com.example.drinkeasy.db.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class IngredientSearchFragment extends Fragment implements OnItemClick {

    private ToggleButton milk,caffeine,nuts,peach,sugar;
    private AutoCompleteTextView searchBox;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Integer> topTray, myIngredients;
    private User userData = new User();


    public static IngredientSearchFragment newInstance() {
        return new IngredientSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.ingredient_search_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        //검색박스 설정
//        ingredients = IngredientsAccess.getInstance(getActivity()).getAllData();
//        searchBox = view.findViewById(R.id.ingredientSearchBox);
//        //IngredientSearchAdapter searchAdapter = new IngredientSearchAdapter(getActivity(),ingredients,this);
//        searchBox.setAdapter(searchAdapter);

        //트레이 버튼 설정
        BtnOnClick btnOnClick = new BtnOnClick();
        btnOnClick.setResources(getResources());
        milk = view.findViewById(R.id.ingEXbt_milk);
        caffeine = view.findViewById(R.id.ingEXbt_caffeine);
        nuts = view.findViewById(R.id.ingEXbt_nuts);
        peach = view.findViewById(R.id.ingEXbt_peach);
        sugar = view.findViewById(R.id.ingEXbt_sugar);
        milk.setOnClickListener(btnOnClick);
        caffeine.setOnClickListener(btnOnClick);
        nuts.setOnClickListener(btnOnClick);
        peach.setOnClickListener(btnOnClick);
        sugar.setOnClickListener(btnOnClick);

        //유저 데이터 초기화

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userData = documentSnapshot.toObject(User.class);

                /* beforeAct 변수
                 * 0이면 최초 회원가입 직후의 접근임
                 * 1이면 검색기 설정에서의 접근임
                 * 2이면 내 취향 설정에서의 접근임
                 * */
                int beforeAct = 2;


                //내 취향 설정일때의 접근
                if (beforeAct!=1){
                    topTray = userData.getHateIngredientsSet();
                    myIngredients = userData.getHateIngredients();
                }
                //검색기 설정일때의 접근
                else{
                }

                //상단트레이 설정
                if (!topTray.isEmpty()) {
                    if (topTray.get(0) == 1) milk.performClick();
                    if (topTray.get(1) == 1) caffeine.performClick();
                    if (topTray.get(2) == 1) nuts.performClick();
                    if (topTray.get(3) == 1) peach.performClick();
                    if (topTray.get(4) == 1) sugar.performClick();
                }

                //my ingredients를 매개변수로 리사이클러뷰 어댑터 초기화

                ingredients = IngredientsAccess.getInstance(getActivity()).getSomeData(myIngredients);


                //이거 바탕으로 성분 어댑터 작성하기.
                //데이터베이스에서 성분 받아오기 쌉완료..

            }
        });





    }


    private void refresh(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(int type, int value) {
        switch (type){
            case 0: //음료 검색 후 DB에 추가
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("users").document(user.getUid());
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        userData = documentSnapshot.toObject(User.class);
                    }
                });
        }
        //value값은 음료 id임
        //type==0 이면 음료 검색 후 추가하는 코드
        //쇼ㅔㄷ=1이면 ㅇ,ㅁ려

        //type에 따라서 value값을 DB에 업데이트
        //어댑터에서 버튼이 눌렸을 때의 코드 작성

    }




}




class BtnOnClick implements View.OnClickListener {
    private ToggleButton current;
    private Resources resources;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ingEXbt_milk:
                current = v.findViewById(R.id.ingEXbt_milk);
                if (current.isChecked()){
                    current.setBackgroundDrawable(resources.getDrawable(R.drawable.bt_ingredient_except_on));
                    //on상태일때 추가코드작성
                }
                else {
                    current.setBackgroundDrawable(resources.getDrawable(R.drawable.bt_ingredient_except_off));
                    //off상태일때 추가코드작성
                }
                break;

            case R.id.ingEXbt_caffeine:
                current = v.findViewById(R.id.ingEXbt_caffeine);
                if (current.isChecked()){
                    current.setBackgroundDrawable(resources.getDrawable(R.drawable.bt_ingredient_except_on));
                    //on상태일때 추가코드작성
                }
                else {
                    current.setBackgroundDrawable(resources.getDrawable(R.drawable.bt_ingredient_except_off));
                    //off상태일때 추가코드작성
                }
                break;

            case R.id.ingEXbt_nuts:
                current = v.findViewById(R.id.ingEXbt_nuts);
                if (current.isChecked()){
                    current.setBackgroundDrawable(resources.getDrawable(R.drawable.bt_ingredient_except_on));
                    //on상태일때 추가코드작성
                }
                else {
                    current.setBackgroundDrawable(resources.getDrawable(R.drawable.bt_ingredient_except_off));
                    //off상태일때 추가코드작성
                }
                break;

            case R.id.ingEXbt_peach:
                current = v.findViewById(R.id.ingEXbt_peach);
                if (current.isChecked()){
                    current.setBackgroundDrawable(resources.getDrawable(R.drawable.bt_ingredient_except_on));
                    //on상태일때 추가코드작성
                }
                else {
                    current.setBackgroundDrawable(resources.getDrawable(R.drawable.bt_ingredient_except_off));
                    //off상태일때 추가코드작성
                }
                break;

            case R.id.ingEXbt_sugar:
                current = v.findViewById(R.id.ingEXbt_sugar);
                if (current.isChecked()){
                    current.setBackgroundDrawable(resources.getDrawable(R.drawable.bt_ingredient_except_on));
                    //on상태일때 추가코드작성
                }
                else {
                    current.setBackgroundDrawable(resources.getDrawable(R.drawable.bt_ingredient_except_off));
                    //off상태일때 추가코드작성
                }
                break;
        }
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }
}

interface OnItemClick {
    void onClick (int type, int value);
}