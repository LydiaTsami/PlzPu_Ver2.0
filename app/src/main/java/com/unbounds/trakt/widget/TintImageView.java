package com.unbounds.trakt.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.unbounds.trakt.R;

/**
 * Created by maclir on 2015-11-21.
 */
public class TintImageView extends ImageView {

    private ColorStateList tint;

    public TintImageView(final Context context) {
        super(context);
    }

    public TintImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TintImageView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(final Context context, final AttributeSet attrs, final int defStyle) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TintImageView, defStyle, 0);
        tint = a.getColorStateList(R.styleable.TintImageView_tint);
        a.recycle();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (tint != null && tint.isStateful())
            updateTintColor();
    }

    public void setColorFilter(final ColorStateList tint) {
        this.tint = tint;
        super.setColorFilter(tint.getColorForState(getDrawableState(), 0));
    }

    private void updateTintColor() {
        final int color = tint.getColorForState(getDrawableState(), 0);
        setColorFilter(color);
    }

}
