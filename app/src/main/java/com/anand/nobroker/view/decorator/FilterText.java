package com.anand.nobroker.view.decorator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.anand.nobroker.R;

@SuppressLint("AppCompatCustomView")
public class FilterText extends TextView {

    public FilterText(Context context) {
        super(context);
        this.setTextColor(getResources().getColor(R.color.text_color));
    }

    public FilterText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setTextColor(getResources().getColor(R.color.text_color));
    }

    public void selected() {
        this.setBackground(getResources().getDrawable(R.drawable.rect_text_box_selected));
        this.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    public void unSelected() {
        this.setBackground(getResources().getDrawable(R.drawable.rect_text_box_normal));
        this.setTextColor(getResources().getColor(R.color.text_color));
    }
}
