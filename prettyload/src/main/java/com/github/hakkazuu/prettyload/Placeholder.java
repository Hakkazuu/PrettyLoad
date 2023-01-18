package com.github.hakkazuu.prettyload;

import java.util.ArrayList;
import java.util.List;

public class Placeholder {

    private int mDrawableResId = 0;

    private int mLayoutResId = 0;

    private List<Integer> mLayoutViewIdList = new ArrayList<>();

    private Placeholder() {}

    private Placeholder(int drawableResId) {
        mDrawableResId = drawableResId;
    }

    private Placeholder(int drawableResId, int layoutResId, int... layoutViewIds) {
        mDrawableResId = drawableResId;
        mLayoutResId = layoutResId;

        mLayoutViewIdList = new ArrayList<>();
        for(int viewId : layoutViewIds)
            mLayoutViewIdList.add(viewId);
    }

    public static Placeholder fromDrawable(int drawableResId) {
        return new Placeholder(drawableResId);
    }

    public static Placeholder fromLayout(int drawableResId, int layoutResId, int... layoutViewIds) {
        return new Placeholder(drawableResId, layoutResId, layoutViewIds);
    }

    int getDrawableResId() {
        return mDrawableResId;
    }

    public void setDrawableResId(int drawableResId) {
        mDrawableResId = drawableResId;
    }

    int getLayoutResId() {
        return mLayoutResId;
    }

    List<Integer> getLayoutViewIdList() {
        return mLayoutViewIdList;
    }

}