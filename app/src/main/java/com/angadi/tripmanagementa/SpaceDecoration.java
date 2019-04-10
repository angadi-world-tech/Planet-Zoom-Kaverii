package com.angadi.tripmanagementa;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public class SpaceDecoration extends RecyclerView.ItemDecoration {
    private int space;
    public SpaceDecoration(int space){
        this.space=space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
        outRect.left=outRect.right=outRect.bottom=outRect.top=space;
    }
}
