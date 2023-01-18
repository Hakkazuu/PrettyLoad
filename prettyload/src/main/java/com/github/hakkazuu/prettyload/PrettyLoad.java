package com.github.hakkazuu.prettyload;

import android.content.Context;
import android.content.res.Resources;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

public class PrettyLoad {

    // todo убрать setAnimator из вью, сделать единый, методом с параметрами
    // todo remove order, use ANIMATION_TYPE
    // todo add view animation like @ViewAnimation
    // todo add view animation like @ViewGroupAnimation

    public static final int ANIMATION_TYPE_ALL_TOGETHER = 0;
    public static final int ANIMATION_TYPE_VERTICAL_ORDER = 1;
    public static final int ANIMATION_TYPE_HORIZONTAL_ORDER = 2;

    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;

    static final int DEFAULT_ANIMATION_TYPE = ANIMATION_TYPE_ALL_TOGETHER;
    static final int DEFAULT_DURATION = 500;
    static final int DEFAULT_ORDER_DELAY = 100;
    static final Placeholder DEFAULT_PLACEHOLDER = Placeholder.fromLayout(R.drawable.pretty_background, R.layout.pretty_placeholder);
    static final int DEFAULT_PLACEHOLDER_COUNT = 5;
    static final int DEFAULT_START_COLOR = R.color.pretty_start_color;
    static final int DEFAULT_END_COLOR = R.color.pretty_end_color;

    private Context mContext;
    private Object mRootObject;
    private SparseArray<? super PrettyItem> mPrettyItemArray = new SparseArray<>();
    private int mAnimationType = DEFAULT_ANIMATION_TYPE;
    private long mDuration = DEFAULT_DURATION;
    private long mOrderDelay = DEFAULT_ORDER_DELAY;
    private Placeholder mPlaceholder = DEFAULT_PLACEHOLDER;
    private int mStartColorResId = DEFAULT_START_COLOR;
    private int mEndColorResId = DEFAULT_END_COLOR;
    private OnErrorListener mOnErrorListener = null;

    private PrettyLoad() {}

    private PrettyLoad(Context context, Object rootObject) {
        mContext = context;
        mRootObject = rootObject;
    }

    public void start() {
        start(null);
        int[] i = {};
    }

    public void start(String tag) {
        for (int index = 0; index < mPrettyItemArray.size(); ++index) {
            if(tag == null) {
                ((PrettyItem)mPrettyItemArray.valueAt(index)).start();
            } else {
                if(((PrettyItem)mPrettyItemArray.valueAt(index)).getTag().equals(tag))
                    ((PrettyItem)mPrettyItemArray.valueAt(index)).start();
            }
        }
    }

    public void stop() {
        stop(null);
    }

    public void stop(String tag) {
        for (int index = 0; index < mPrettyItemArray.size(); index++) {
            if(tag == null) {
                ((PrettyItem)mPrettyItemArray.valueAt(index)).stop();
            } else {
                if(((PrettyItem)mPrettyItemArray.valueAt(index)).getTag().equals(tag))
                    ((PrettyItem)mPrettyItemArray.valueAt(index)).stop();
            }
        }
    }

    private void  fillViewArray() {
        for(Field field : mRootObject.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(ViewAnimation.class)) {
                ViewAnimation viewAnimation = field.getAnnotation(ViewAnimation.class);
                field.setAccessible(true);
                try {
                    View view = (View) field.get(mRootObject);

                    PrettyView prettyView = new PrettyView.Builder(view)
                            .setTag(viewAnimation.tag())
                            .setPlaceholder(viewAnimation.placeholderDrawableResId() == 0 ?
                                    mPlaceholder
                                    : Placeholder.fromDrawable(viewAnimation.placeholderDrawableResId()))
                            .setColors(
                                    viewAnimation.startColorResId() == 0 ? mStartColorResId : viewAnimation.startColorResId(),
                                    viewAnimation.endColorResId() == 0 ? mEndColorResId : viewAnimation.endColorResId()
                            )
                            .setAnimationSettings(mDuration, mOrderDelay)
                            .build();

                    mPrettyItemArray.append(prettyView.getId(), prettyView);
                } catch (Exception e) {
                    if(mOnErrorListener != null)
                        mOnErrorListener.error(e.getLocalizedMessage());

                    e.printStackTrace();
                }
            } else if(field.isAnnotationPresent(ViewGroupAnimation.class)) {
                ViewGroupAnimation viewGroupAnimation = field.getAnnotation(ViewGroupAnimation.class);
                field.setAccessible(true);
                try {
                    ViewGroup viewGroup = (ViewGroup) field.get(mRootObject);

                    PrettyViewGroup prettyViewGroup = new PrettyViewGroup.Builder(viewGroup)
                            .setTag(viewGroupAnimation.tag())
                            .setPlaceholder(viewGroupAnimation.placeholderLayoutResId() == 0 ?
                                    mPlaceholder
                                    : Placeholder.fromLayout(
                                            viewGroupAnimation.placeholderDrawableResId() == 0 ? mPlaceholder.getDrawableResId() : viewGroupAnimation.placeholderDrawableResId(),
                                            viewGroupAnimation.placeholderLayoutResId(),
                                            viewGroupAnimation.placeholderLayoutViewIds()),
                                    viewGroupAnimation.placeholderCount() == 0 ? DEFAULT_PLACEHOLDER_COUNT : viewGroupAnimation.placeholderCount())
                            .setColors(
                                    viewGroupAnimation.startColorResId() == 0 ? mStartColorResId : viewGroupAnimation.startColorResId(),
                                    viewGroupAnimation.endColorResId() == 0 ? mEndColorResId : viewGroupAnimation.endColorResId()
                            )
                            .setOrientation(viewGroupAnimation.orientation())
                            .setAnimationSettings(mDuration, mOrderDelay)
                            .build();

                    mPrettyItemArray.append(prettyViewGroup.getId(), prettyViewGroup);
                } catch (Exception e) {
                    if(mOnErrorListener != null)
                        mOnErrorListener.error(e.getLocalizedMessage());

                    e.printStackTrace();
                }
            }
        }
    }

    private <I extends PrettyItem> void calculateOrder(I prettyItem) {
        // todo
        switch (mAnimationType) {
            case ANIMATION_TYPE_ALL_TOGETHER:
                break;
            case ANIMATION_TYPE_VERTICAL_ORDER:
                break;
            case ANIMATION_TYPE_HORIZONTAL_ORDER:
                break;
        }
    }

    public interface OnErrorListener {
        void error(String error);
    }

    public static class Builder {

        private PrettyLoad mInstance = null;

        private Builder() {}

        public Builder(Context context, Object rootObject) {
            mInstance = new PrettyLoad(context, rootObject);
        }

        public Builder setPlaceholderDrawable(int drawableResId) {
            mInstance.mPlaceholder.setDrawableResId(drawableResId);

            return this;
        }

        public Builder setColors(int startColorResId, int endColorResId) {
            mInstance.mStartColorResId = startColorResId;
            mInstance.mEndColorResId = endColorResId;

            return this;
        }

        public Builder setAnimationSettings(int animationType, long duration) {
            mInstance.mAnimationType = animationType;
            mInstance.mDuration = duration;

            return this;
        }

        public Builder setOnErrorListener(OnErrorListener onErrorListener) {
            mInstance.mOnErrorListener = onErrorListener;

            return this;
        }

        public Builder addViewAnimation(PrettyView prettyView) {

            return this;
        }

        public Builder addViewGroupAnimation() {

            return this;
        }

        public PrettyLoad build() {
            mInstance.fillViewArray();
            return mInstance;
        }

    }

    private Context getContext() {
        return mContext;
    }

    private Resources getResources() {
        return mContext.getResources();
    }

}