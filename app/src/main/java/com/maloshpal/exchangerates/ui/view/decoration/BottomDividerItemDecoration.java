package com.maloshpal.exchangerates.ui.view.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public class BottomDividerItemDecoration extends BaseDividerItemDecoration
{
    public BottomDividerItemDecoration(Context context) {
        super(context);
    }

    public BottomDividerItemDecoration(Context context, int resId) {
        super(context, resId);
    }

    @Override
    protected void drawDecoration(@NonNull Canvas canvas,
                                  @NonNull RecyclerView parent,
                                  @NonNull RecyclerView.State state,
                                  int childCount) {

        drawDividerForChild(canvas, parent, childCount - 1);
    }
}