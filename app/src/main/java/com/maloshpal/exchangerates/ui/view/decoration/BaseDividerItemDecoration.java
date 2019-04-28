package com.maloshpal.exchangerates.ui.view.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseDividerItemDecoration extends RecyclerView.ItemDecoration
{
    /**
     * Default divider will be used
     */
    public BaseDividerItemDecoration(Context context) {
        final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
        mDivider = styledAttributes.getDrawable(0);
        styledAttributes.recycle();
    }

    /**
     * Custom divider will be used
     */
    public BaseDividerItemDecoration(Context context, int resId) {
        mDivider = ContextCompat.getDrawable(context, resId);
    }

    public void setLeftPaddingInPx(int padding) {
        mLeftPadding = padding;
    }

    public void setRightPaddingInPx(int padding) {
        mRightPadding= padding;
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        //https://stackoverflow.com/a/49150289
        int childCount = parent.getLayoutManager().getChildCount();

        if (childCount > 0) {
            drawDecoration(canvas, parent, state, childCount);
        }
    }

    protected abstract void drawDecoration(@NonNull Canvas canvas,
                                  @NonNull RecyclerView parent,
                                  @NonNull RecyclerView.State state,
                                  int childCount);

    protected void drawDividerForChild(@NonNull Canvas c, @NonNull RecyclerView parent, int childNumber) {
        int left = parent.getPaddingLeft() + mLeftPadding;
        int right = parent.getWidth() - (parent.getPaddingRight() + mRightPadding);

        View child = parent.getChildAt(childNumber);

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

        int bottom = child.getBottom() + params.bottomMargin;
        int top = bottom - mDivider.getIntrinsicHeight();

        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(c);
    }

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private final Drawable mDivider;
    private int mLeftPadding = 0;
    private int mRightPadding = 0;
}