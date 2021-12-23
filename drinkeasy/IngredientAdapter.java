package com.example.drinkeasy;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.drinkeasy.db.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {

    //TODO : 드링크 리스트랑 액티비티 아직 해결한거 없음.
    private List<Ingredient> ingredients;
    private OnItemClick clickListener;

    // TODO:: 생성자
    public IngredientAdapter(List<Ingredient> ingredients, OnItemClick clickListener) {
        this.ingredients = ingredients;
        this.clickListener = clickListener;
    }

    // 리사이클러뷰에 들어갈 뷰 홀더, 그리고 그 뷰 홀더에 들어갈 아이템들을 지정
    public static class IngredientHolder extends RecyclerView.ViewHolder {
        private ImageButton image;
        private TextView text;

        public IngredientHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.image_ingredients);
            this.text = itemView.findViewById(R.id.text_ingredients);
        }
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ingredients, parent, false);
        IngredientHolder ingredientHolder = new IngredientHolder(holderView);
        return ingredientHolder;
    }


    // 실제 각 뷰 홀더에 데이터를 연결해주는 함수
    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        final Ingredient ingredient = ingredients.get(position);
        //텍스트 바인딩
        holder.text.setText(ingredient.getName());
        //이미지 바인딩.
        MultiTransformation multiOption = new MultiTransformation(new CenterCrop(), new RoundedCorners(36));
        Glide.with(holder.image)
                .load(ingredient.getImage())
                .apply(RequestOptions.bitmapTransform(multiOption))
                .into(holder.image);

        //TODO: 메인함수에 온이미지 클릭리스너 함수 구현
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(1, ingredient.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
