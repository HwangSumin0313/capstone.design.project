package com.example.drinkeasy;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.drinkeasy.db.Ingredient;
import java.util.ArrayList;

public class IngredientSearchAdapter extends ArrayAdapter<Ingredient> {

    private ArrayList<Ingredient> ingredients;
    private OnItemClick clickListener;

    public IngredientSearchAdapter(@NonNull Context context, @NonNull ArrayList<Ingredient> ingredients, OnItemClick listener){
        super(context, 0 , ingredients);
        this.ingredients = new ArrayList<>(ingredients);
        this.clickListener=listener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.adapter_ingredient_search, parent, false
            );
        }

        TextView text = convertView.findViewById(R.id.ing_search_txt);
        ImageButton image = convertView.findViewById(R.id.ing_search_img);
        ImageButton add = convertView.findViewById(R.id.ing_except_add);

        //getItem(position) 코드로 자동완성 될 아이템을 가져온다
        final Ingredient item = getItem(position);


        MultiTransformation multiOption = new MultiTransformation( new CenterCrop(), new RoundedCorners(20) );

        if (item != null) {
            text.setText(item.getName());
            Glide.with(image)
                    .load(item.getImage())
                    .apply(RequestOptions.bitmapTransform(multiOption))
                    .into(image);
            add.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    clickListener.onClick(0,item.getId());
                }
            });



        }


        return convertView;

    }

    //-------------------------- 이 아래는 자동완성을 위한 검색어를 찾아주는 코드이다 --------------------------
    @NonNull
    @Override
    public Filter getFilter() {
        return ingredientFilter;
    }

    private Filter ingredientFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            ArrayList<Ingredient> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(ingredients);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Ingredient item : ingredients) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Ingredient) resultValue).getName();
        }
    };




}

