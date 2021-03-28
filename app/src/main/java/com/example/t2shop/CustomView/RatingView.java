package com.example.t2shop.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.t2shop.Common.Constants;
import com.example.t2shop.R;

public class RatingView extends View {
    private Bitmap star[], currentBitmap, star0;
    private Rect[] rects;
    private int rating;
    public RatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        star = new Bitmap[5];
//        star[0] = BitmapFactory.decodeResource(this.getResources(), R.drawable.star1);
//        star[0] = Bitmap.createScaledBitmap(star[0], 100* Constants.SCREEN_WIDTH/1080, 100*Constants.SCREEN_HEIGHT/1920,true);
//        star[1] = BitmapFactory.decodeResource(this.getResources(), R.drawable.star2);
//        star[1] = Bitmap.createScaledBitmap(star[1], 100* Constants.SCREEN_WIDTH/1080, 100*Constants.SCREEN_HEIGHT/1920,true);
//        star[2] = BitmapFactory.decodeResource(this.getResources(), R.drawable.star3);
//        star[2] = Bitmap.createScaledBitmap(star[2], 100* Constants.SCREEN_WIDTH/1080, 100*Constants.SCREEN_HEIGHT/1920,true);
//        star[3] = BitmapFactory.decodeResource(this.getResources(), R.drawable.star4);
//        star[3] = Bitmap.createScaledBitmap(star[3], 100* Constants.SCREEN_WIDTH/1080, 100*Constants.SCREEN_HEIGHT/1920,true);
//        star[4] = BitmapFactory.decodeResource(this.getResources(), R.drawable.star5);
//        star[4] = Bitmap.createScaledBitmap(star[4], 100* Constants.SCREEN_WIDTH/1080, 100*Constants.SCREEN_HEIGHT/1920,true);
//        star0 = BitmapFactory.decodeResource(this.getResources(), R.drawable.star0);
//        star0 = Bitmap.createScaledBitmap(star0, 100* Constants.SCREEN_WIDTH/1080, 100*Constants.SCREEN_HEIGHT/1920,true);
//        rects = new Rect[5];
//        for (int i =0; i < rects.length; i++){
//            rects[i] = new Rect(150*Constants.SCREEN_WIDTH/1080 + i*Constants.SCREEN_WIDTH/5, 0, 100*Constants.SCREEN_WIDTH/1080, 100*Constants.SCREEN_HEIGHT/1920);
//        }
//        currentBitmap = star[4];
    }
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.YELLOW);
        Toast.makeText(getContext(), ""+getY(), Toast.LENGTH_SHORT).show();
//        for (int i = 0; i < 5; i ++){
//            canvas.drawBitmap(currentBitmap, 0, 0, null);
//        }
//        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN){
//            for (int i = 0; i < rects.length; i++){
//                if (rects[i].contains((int)event.getX(), (int)event.getY())){
//                    this.rating = i+1;
//                    currentBitmap = star[i];
//                }
//            }
//        }
        return true;
    }

    public Rect[] getRects() {
        return rects;
    }

    public void setRects(Rect[] rects) {
        this.rects = rects;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
