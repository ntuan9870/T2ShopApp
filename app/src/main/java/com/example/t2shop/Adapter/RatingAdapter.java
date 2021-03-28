package com.example.t2shop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t2shop.R;

import java.util.ArrayList;
import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Drawable> drawables = new ArrayList<>();
    private Drawable drawable0, drawable1;
    public static int amount_star = 0;

    public RatingAdapter(Context context) {
        this.context = context;
        drawable0 = ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_baseline_star_border_24, null);
        drawable1 = ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_baseline_star_24, null);
        for (int i = 0; i < 5; i++){
            drawables.add(drawable0);
        }
        for (int i = 0; i < 5; i++){
            if (i <= amount_star-1){
                drawables.set(i, drawable1);
            }else{
                drawables.set(i, drawable0);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_star, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.img_star.setImageDrawable(drawables.get(position));
        if (drawables.get(position)==drawable0){
            holder.img_star.setColorFilter(0xFF000000);
        }else{
            holder.img_star.setColorFilter(0xFFFFC107);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 5; i++){
                    if (i <= position){
                        drawables.set(i, drawable1);
                    }else{
                        drawables.set(i, drawable0);
                    }
                }
                amount_star = position+1;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return drawables.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_star;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_star = itemView.findViewById(R.id.img_star);
        }
    }
}
