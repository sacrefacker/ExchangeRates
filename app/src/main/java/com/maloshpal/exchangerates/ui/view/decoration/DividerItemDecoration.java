package com.maloshpal.exchangerates.ui.view.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public class DividerItemDecoration extends BaseDividerItemDecoration
{
    public DividerItemDecoration(Context context) {
        super(context);
    }

    public DividerItemDecoration(Context context, int resId) {
        super(context, resId);
    }

    @Override
    protected void drawDecoration(@NonNull Canvas canvas,
                                  @NonNull RecyclerView parent,
                                  @NonNull RecyclerView.State state,
                                  int childCount) {

        int range = childCount - 1;
        for (int i = 0; i < range; i++) {
            drawDividerForChild(canvas, parent, i);
        }
    }
}