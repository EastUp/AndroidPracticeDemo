package com.east.customview.shadow.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.east.customview.R;


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description: 自定义阴影的外层View
 * @author: jamin
 * @date: 2020/11/7
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class ShadowViewGroup extends LinearLayout {
    private float shadowRadius;
    private float offsetx;
    private float offsety;
    private ColorStateList shadowColor;
    private Paint paint;

    public ShadowViewGroup(Context context) {
        this(context, null);
    }

    public ShadowViewGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowViewGroup);
        shadowColor = arr.getColorStateList(R.styleable.ShadowViewGroup_shadowColor);
        shadowRadius = arr.getDimension(R.styleable.ShadowViewGroup_shadowRadius, dp2px(3f));
        offsetx = arr.getDimension(R.styleable.ShadowViewGroup_offsetx, 0f);
        offsety = arr.getDimension(R.styleable.ShadowViewGroup_offsety, 0f);

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setDither(true);

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setShadowLayer(shadowRadius, offsetx, offsety, shadowColor.getColorForState(getDrawableState(), 0));
        float dp5 = (float) dp2px(5f);
        canvas.drawRoundRect(new RectF(dp5, dp5, getWidth() - dp5, getHeight() - dp5), dp2px(8f), dp2px(8f), paint);
    }


    /**
     * dp 转 px
     */
    private float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
