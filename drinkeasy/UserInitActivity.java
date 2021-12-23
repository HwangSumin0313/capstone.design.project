package com.example.drinkeasy;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.drinkeasy.db.Ingredient;
import com.example.drinkeasy.db.IngredientsAccess;
import com.example.drinkeasy.db.User;
import com.example.drinkeasy.db.UserAccess;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
public class UserInitActivity extends AppCompatActivity implements OnItemClick {

    private static final String TAG = "UserInitActivity";
    private boolean fromMain;
    private ImageButton btn_back;
    private User userData = new User();
    private ConstraintLayout userManagementView;
    private EditText nickname, minPrice, maxPrice;
    private ImageView nickAvailImage;
    private UserAccess uao = new UserAccess();
    private ToggleButton milk,caffeine,nuts,peach,sugar;
    private ArrayList<Ingredient> ingredients;
    private AutoCompleteTextView searchBox;
    private ArrayList<Integer> hateIngredients;
    private RecyclerView ingredientsRecycler;
    private RecyclerView.Adapter ingredientAdapter;
    private TextView hateIngEmptyNotify;
    private ImageButton br0,br1,br2,br3,br4,br6,br7,br8,br9,br10,br11,br12,br13,br14,br15;
    private Button brs0,brs1,brs2,brs3,brs4, startMain, logout,appinfo,withdrawal;
    private int[] brandList = {
            R.id.gongcha, R.id.dunkin, R.id.touslesjours, R.id.mega, R.id.paik,
            R.id.angelinus, R.id.yogerpresso, R.id.ediya, R.id.coffeebay, R.id.coffeebean,
            R.id.tomntoms, R.id.twosome,R.id.paris, R.id.pascucci, R.id.hollys,
            R.id.sb_normal_txt, R.id.sb_tea_txt, R.id.sb_reserve_txt, R.id.sb_blonde_txt, R.id.sb_jeju_txt
    };
    private RatingBar minRate,maxRate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_init);

        //변수초기화
        btn_back = findViewById(R.id.btn_back_userinit);
        userManagementView = findViewById(R.id.user_set);
        nickname = findViewById(R.id.nickname);
        nickAvailImage = findViewById(R.id.is_avail_nick);
        startMain = findViewById(R.id.btn_user_init);
        milk = findViewById(R.id.ingEXbt_milk);
        caffeine = findViewById(R.id.ingEXbt_caffeine);
        nuts = findViewById(R.id.ingEXbt_nuts);
        peach = findViewById(R.id.ingEXbt_peach);
        sugar = findViewById(R.id.ingEXbt_sugar);
        ingredients = IngredientsAccess.getInstance(this).getAllData();
        hateIngredients = userData.getHateIngredients();
        ingredientsRecycler = findViewById(R.id.view_hate_ingredients);
        hateIngEmptyNotify = findViewById(R.id.view_hate_ingredients_empty);
        br0 = findViewById(R.id.gongcha);
        br1 = findViewById(R.id.dunkin);
        br2 = findViewById(R.id.touslesjours);
        br3 = findViewById(R.id.mega);
        br4 = findViewById(R.id.paik);
        br6 = findViewById(R.id.angelinus);
        br7 = findViewById(R.id.yogerpresso);
        br8 = findViewById(R.id.ediya);
        br9 = findViewById(R.id.coffeebay);
        br10 = findViewById(R.id.coffeebean);
        br11 = findViewById(R.id.tomntoms);
        br12 = findViewById(R.id.twosome);
        br13 = findViewById(R.id.paris);
        br14 = findViewById(R.id.pascucci);
        br15 = findViewById(R.id.hollys);
        brs0 = findViewById(R.id.sb_normal_txt);
        brs1 = findViewById(R.id.sb_tea_txt);
        brs2 = findViewById(R.id.sb_reserve_txt);
        brs3 = findViewById(R.id.sb_blonde_txt);
        brs4 = findViewById(R.id.sb_jeju_txt);
        searchBox = findViewById(R.id.ingredientSearchBox);
        minPrice = findViewById(R.id.min_price);
        maxPrice = findViewById(R.id.max_price);
        minRate = findViewById(R.id.min_rating);
        maxRate = findViewById(R.id.max_rating);
        logout = findViewById(R.id.btn_user_logout);
        appinfo = findViewById(R.id.btn_app_info);
        withdrawal = findViewById(R.id.btn_user_exit);

        //fromMain을 받아와서 Main에서의 접근이면 백버튼과 user관리(회원탈퇴, 로그아웃, 앱정보)를 보이도록 하고 아니면 보이지 않도록 한다.
        //TODO:: 현재 테스트 목적으로 비활성화 마지막에 활성화 시킬것. ctrl + /
//        fromMain = getIntent().getBooleanExtra("fromMain",true);
//        if (!fromMain) {
//            btn_back.setVisibility(View.GONE);
//            userManagementView.setVisibility(View.GONE);
//        }

        //backButton에 기능할당
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        userData = (User) getIntent().getSerializableExtra("userData");
        if (userData == null) {
            userData = new User();
            userData = uao.getActivateUserData();
        }

        //최초 로드시 기존 정보를 화면에 띄워준다.
        viewInitialize();

        //TODO:: nickname에 글자가 입력될때마다 isAvail을 확인해서 nickAvailImage를 전환한다.

        //검색기능구현
        //트레이 버튼 설정
        //검색박스 설정
        IngredientSearchAdapter searchAdapter = new IngredientSearchAdapter(this, ingredients, this);
        searchBox.setAdapter(searchAdapter);


        startMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //닉네임이 제약조건에 맞는지 검사해서 맞으면 Main으로 업데이트하고 아니면 오류메시지를 출력한다.
                //TODO:: 가격 및 평점 제약 설정
                if (isAvailNick(nickname.getText().toString())) {
                    updateUserDataWithInputedData();
                    updateFirebaseUserData();
                } else {
                    //TODO::User업데이트가 불가능한 경우 오류메시지를 출력한다.
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

            }
        });
        appinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:: APPINFO구현
            }
        });
        withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revokeAccess();
            }
        });

    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    private void revokeAccess() {
        //TODO:: 사용자 관련 DB모두 제거
        FirebaseAuth.getInstance().getCurrentUser().delete();
    }

    private boolean isAvailNick(String nick) {
        //TODO::닉네임에 대한 세부검사코드 작성할것
        return true;
    }

    private void updateUserDataWithInputedData() {
        userData.setNickName(nickname.getText().toString());
        if (minPrice.getText().toString().length() !=0 && minPrice.getText().toString() !=null ) userData.setMinPrice(Integer.parseInt(minPrice.getText().toString()));
        if (maxPrice.getText().toString().length() !=0 && maxPrice.getText().toString() !=null ) userData.setMaxPrice(Integer.parseInt(maxPrice.getText().toString()));
        else userData.setMaxPrice(50000);
        userData.setMinStar(minRate.getNumStars());
        userData.setMaxStar(maxRate.getNumStars());

    }

    //뒤로가기버튼을 위함.
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void viewInitialize() {
        nickname.setText(userData.getNickName());
        //트레이아이콘 초기화
        if (userData.getHateIngredientsSet().get(0) == 1) milk.performClick();
        if (userData.getHateIngredientsSet().get(1) == 1) caffeine.performClick();
        if (userData.getHateIngredientsSet().get(2) == 1) nuts.performClick();
        if (userData.getHateIngredientsSet().get(3) == 1) peach.performClick();
        if (userData.getHateIngredientsSet().get(4) == 1) sugar.performClick();
        ingredientRecyclerViewInit();
        //TODO:: 브랜드 아이콘 초기화.
        minPrice.setText(Integer.toString(userData.getMinPrice()));
        maxPrice.setText(Integer.toString(userData.getMaxPrice()));
        minRate.setRating((float)userData.getMinStar());
        maxRate.setRating((float)userData.getMaxStar());
    }

    private void updateFirebaseUserData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
         FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("users").document(user.getUid()).set(userData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            updateUI(MainActivity.class,true);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //TODO::파이어베이스 업로드 실패시 코드 작성
                        }
                    });
    }


    private void updateUI(Class nextClass, boolean ableBackButton) {
        Intent intent = new Intent(this, nextClass);
        intent.putExtra("userData",userData);
        intent.putExtra("fromMain",fromMain);
        if (!ableBackButton) intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
        startActivity(intent);
        finish();
    }
    public void brandButtonClick(View v){
        brandButtonToggle(v.getId());
    }

    private void brandButtonToggle(int id){
        int index;
        for (index=0; index<20; index++){
            if (brandList[index]==id) break;
        }
        userData.modifyHateBrandSet(index);
        if (index<15){
            ImageButton current = findViewById(id);
            if (userData.getHateBrandSet().get(index)==0){
                MultiTransformation multiOption = new MultiTransformation(new CenterCrop());
                Glide.with(current)
                        .load(current.getBackground())
                        .apply(RequestOptions.bitmapTransform(multiOption))
                        .into(current);
            }
            else{
                MultiTransformation multiOption = new MultiTransformation(new CenterCrop(), new GrayscaleTransformation(), new BlurTransformation(12));
                Glide.with(current)
                        .load(current.getBackground())
                        .apply(RequestOptions.bitmapTransform(multiOption))
                        .into(current);
            }
        }
        else{
            Button current =  findViewById(id);
            if (userData.getHateBrandSet().get(index)==0) current.setBackground(getResources().getDrawable(R.drawable.bt_circle_on));
            else current.setBackground(getResources().getDrawable(R.drawable.bt_circle_off));
        }

    }
    //트레이버튼에 기능할당
    //TODO:: 각 버튼의 구체적인 기능 기술
    public void trayButtonClick(View v){
        ToggleButton current;
        Resources resources = getResources();
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
                userData.modifyHateIngredientsSet(0);
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
                userData.modifyHateIngredientsSet(1);
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
                userData.modifyHateIngredientsSet(2);
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
                userData.modifyHateIngredientsSet(3);
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
                userData.modifyHateIngredientsSet(4);
                break;
        }
    }

    private void ingredientRecyclerViewInit(){
        if (userData.getHateIngredients().size()!=0) hateIngEmptyNotify.setVisibility(View.GONE);
        else hateIngEmptyNotify.setVisibility(View.VISIBLE);
        //리사이클러뷰초기설정
        RecyclerView.LayoutManager layoutManager;
        ingredientsRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        //리사이클러뷰 가로로 보기위함.
        ingredientsRecycler.setLayoutManager(layoutManager);
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        ingredientsRecycler.scrollToPosition(userData.getHateIngredients().size() - 1);
        ingredientAdapter = new IngredientAdapter(IngredientsAccess.getInstance(this).getSomeData(userData.getHateIngredients()), this);
        ingredientsRecycler.setAdapter(ingredientAdapter);
    }


    @Override
    public void onClick(int type, int value) {
        //0은 음료추가, 1은 음료 제거
        switch (type){
            case 0:
                if (!isOverlap(value)) userData.addHateIngredients(value);
                else{
                    //TODO:: 중복알림 코드 작성: 이미 추가된 성분입니다.
                    //TODO:: 가능하다면 자동으로 검색목록에서 삭제되는것도 고려.
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);
                searchBox.setText("");
                break;
            case 1:
                userData.deleteHateIngredients(value);
                break;
        }
        //리사이클러뷰 업데이트
        ingredientRecyclerViewInit();
    }

    private boolean isOverlap(int value) {
        for (int i = 0; i < userData.getHateIngredients().size(); i++) {
            if (userData.getHateIngredients().get(i) == value) return true;
        }
        return false;
    }
}







interface OnItemClick {
    void onClick (int type, int value);
}
