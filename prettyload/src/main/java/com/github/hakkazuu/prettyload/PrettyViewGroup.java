package com.github.hakkazuu.prettyload;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

class PrettyViewGroup extends PrettyItem {

    private ViewGroup mViewGroup;
    private GridLayout mOverlappingLayout;
    private List<PrettyView> mPrettyViewList;

    private String mTag;
    private Placeholder mPlaceholder;
    private int mStartColorResId;
    private int mEndColorResId;
    private int mPlaceholderCount;
    private int mOrientation;
    private ValueAnimator mAnimator;
    private long mDuration;
    private long mOrderDelay;

    private PrettyViewGroup() {}

    private PrettyViewGroup(ViewGroup viewGroup) {
        mViewGroup = viewGroup;
    }

    @Override
    void start() {
        mViewGroup.setVisibility(View.INVISIBLE);
        fillOverlappingLayout();

        for(PrettyView prettyView : mPrettyViewList)
            prettyView.start();
    }

    @Override
    void stop() {
        mViewGroup.setVisibility(View.VISIBLE);
        ((ViewGroup)mViewGroup.getParent()).removeView(mOverlappingLayout);

        for(PrettyView prettyView : mPrettyViewList)
            prettyView.stop();
    }

    @Override
    String getTag() {
        return mTag;
    }

    @Override
    int getId() {
        return mViewGroup.getId();
    }

    private void fillOverlappingLayout() {
        mPrettyViewList = new ArrayList<>();

        mOverlappingLayout = new GridLayout(mViewGroup.getContext());
        mOverlappingLayout.setLayoutParams(mViewGroup.getLayoutParams());
        mOverlappingLayout.setClickable(false);

        if(mOrientation == PrettyLoad.ORIENTATION_HORIZONTAL) {
            mOverlappingLayout.setColumnCount(mPlaceholderCount);
        } else if(mOrientation == PrettyLoad.ORIENTATION_VERTICAL) {
            mOverlappingLayout.setRowCount(mPlaceholderCount);
        }

        ((ViewGroup)mViewGroup.getParent()).addView(mOverlappingLayout);

        for(int i = 0; i < mPlaceholderCount; ++i) {
            View view = LayoutInflater.from(mViewGroup.getContext()).inflate(mPlaceholder.getLayoutResId(), mOverlappingLayout, false);

            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(view.getLayoutParams());
            if(mOrientation == PrettyLoad.ORIENTATION_HORIZONTAL) {
                layoutParams.rowSpec = GridLayout.spec(0);
                layoutParams.columnSpec = GridLayout.spec(i);
            } else if(mOrientation == PrettyLoad.ORIENTATION_VERTICAL) {
                layoutParams.rowSpec = GridLayout.spec(i);
                layoutParams.columnSpec = GridLayout.spec(0);
            }

            if(!mPlaceholder.getLayoutViewIdList().isEmpty()) {
                mOverlappingLayout.addView(view, layoutParams);

                for(int viewId : mPlaceholder.getLayoutViewIdList()) {
                    View subview = view.findViewById(viewId);
                    if(subview != null) {
                        PrettyView prettyView = new PrettyView.Builder(subview)
                                .setTag(mTag)
                                .setPlaceholder(mPlaceholder)
                                .setColors(mStartColorResId, mEndColorResId)
                                .setAnimationSettings(mDuration, mOrderDelay)
                                .build();

                        mPrettyViewList.add(prettyView);
                    }
                }
            } else {
                PrettyView prettyView = new PrettyView.Builder(view)
                        .setTag(mTag)
                        .setPlaceholder(mPlaceholder)
                        .setColors(mStartColorResId, mEndColorResId)
                        .setAnimationSettings(mDuration, mOrderDelay)
                        .build();

                mOverlappingLayout.addView(view, layoutParams);
                mPrettyViewList.add(prettyView);
            }
        }
    }

    public static class Builder {

        private PrettyViewGroup mInstance = null;

        private Builder() {}

        public Builder(ViewGroup viewGroup) {
            mInstance = new PrettyViewGroup(viewGroup);
        }

        public Builder setTag(String tag) {
            mInstance.mTag = tag;

            return this;
        }

        public Builder setPlaceholder(Placeholder placeholder, int placeholderCount) {
            mInstance.mPlaceholder = placeholder;
            mInstance.mPlaceholderCount = placeholderCount;

            return this;
        }

        public Builder setColors(int startColorResId, int endColorResId) {
            mInstance.mStartColorResId = startColorResId;
            mInstance.mEndColorResId = endColorResId;

            return this;
        }

        public Builder setOrientation(int orientation) {
            mInstance.mOrientation = orientation;

            return this;
        }

        public Builder setAnimationSettings(long duration, long orderDelay) {
            mInstance.mDuration = duration;
            mInstance.mOrderDelay = orderDelay;

            return this;
        }

        public PrettyViewGroup build() {
            //mInstance.fillOverlappingLayout();

            return mInstance;
        }

    }


}