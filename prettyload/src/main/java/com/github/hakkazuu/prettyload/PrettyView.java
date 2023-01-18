package com.github.hakkazuu.prettyload;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

class PrettyView extends PrettyItem {

    private View mView;
    private Drawable mViewBackground;
    private Drawable mViewForeground;
    private int mViewWidth = 0;
    private int mViewHeight = 0;

    private String mTag;
    private Placeholder mPlaceholder;
    private int mStartColorResId;
    private int mEndColorResId;
    private ValueAnimator mAnimator;

    private PrettyView() {}

    private PrettyView(View view) {
        mView = view;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) mViewForeground = mView.getForeground();
        else mViewBackground = mView.getBackground();

        mViewWidth = mView.getWidth();
        mViewHeight = mView.getHeight();
    }

    @Override
    void start() {
        mView.setClickable(false);

        mAnimator.start();
    }

    @Override
    void stop() {
        mView.setClickable(true);

        if(mAnimator != null)
            mAnimator.end();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) mView.setForeground(mViewForeground);
        else mView.setBackground(mViewBackground);
    }

    @Override
    String getTag() {
        return mTag;
    }

    @Override
    int getId() {
        return mView.getId();
    }

    private void changeForeground(int color) {
        Drawable drawable;

        if(mPlaceholder.getDrawableResId() != 0)
            drawable = mView.getResources().getDrawable(mPlaceholder.getDrawableResId(), null);
        else drawable = mViewForeground != null ? mViewForeground : mViewBackground;

        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) mView.setForeground(drawable);
        else mView.setBackground(drawable);
    }

    public static class Builder {

        private PrettyView mInstance = null;

        private Builder() {}

        public Builder(View view) {
            mInstance = new PrettyView(view);
        }

        public Builder setTag(String tag) {
            mInstance.mTag = tag;

            return this;
        }

        public Builder setPlaceholder(Placeholder placeholder) {
            mInstance.mPlaceholder = placeholder;

            return this;
        }

        public Builder setColors(int startColorResId, int endColorResId) {
            mInstance.mStartColorResId = startColorResId;
            mInstance.mEndColorResId = endColorResId;

            return this;
        }

        public Builder setAnimationSettings(long duration, long orderDelay) {
            ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(),
                    mInstance.mView.getContext().getResources().getColor(mInstance.mStartColorResId),
                    mInstance.mView.getContext().getResources().getColor(mInstance.mEndColorResId));

            valueAnimator.setDuration(duration);
            valueAnimator.setStartDelay(orderDelay);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.addUpdateListener(animator -> mInstance.changeForeground((int) animator.getAnimatedValue()));

            //mInstance.changeForeground(mInstance.mView.getContext().getResources().getColor(mInstance.mStartColorResId, null));

            mInstance.mAnimator = valueAnimator;

            return this;
        }

        public PrettyView build() {
            return mInstance;
        }

    }

}