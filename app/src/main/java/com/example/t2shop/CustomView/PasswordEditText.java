package com.example.t2shop.CustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.t2shop.R;

import java.lang.reflect.Type;

public class PasswordEditText extends androidx.appcompat.widget.AppCompatEditText {
    private Drawable eye, eyeStrike;
    private boolean useStrike = false, visible;
    Drawable drawable;
    public PasswordEditText(Context context) {
        super(context);
        init(null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attr){
        if (attr!=null){
            TypedArray array = getContext().getTheme().obtainStyledAttributes(attr, R.styleable.PasswordEditText, 0, 0);
            this.useStrike = array.getBoolean(R.styleable.PasswordEditText_useStrike, false);
        }
        eye = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_remove_red_eye_24).mutate();
        eyeStrike = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_visibility_off_24).mutate();
        setting();
    }
    private void setting(){
        setInputType(InputType.TYPE_CLASS_TEXT|(visible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
        Drawable[] drawables = getCompoundDrawables();
        drawable = useStrike && !visible?eyeStrike:eye;
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawable,drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getX() >= getRight() - drawable.getBounds().width()){
            visible = !visible;
            setting();
            invalidate();
        }
        return super.onTouchEvent(event);
    }
}
