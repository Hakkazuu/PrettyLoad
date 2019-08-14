package com.github.hakkazuu.prettyload;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hakkazuu on 2019-07-14 at 19:14.
 */
public class PrettyLoad {

    private static PrettyLoad mInstance;

    private Context mContext;
    private SparseArray<DeclaredView> mDeclaredViewArray = new SparseArray<>();
    private int mDrawableId = 0;
    private long mDuration = 500;
    private List<Integer> mColorList = new ArrayList<>();

    private PrettyLoad() {}

    private PrettyLoad(Context context) {
        mContext = context;
    }

    public static PrettyLoad init(Context context, Object rootObject) {
        mInstance = new PrettyLoad(context);
        mInstance.fillViewArray(rootObject);
        return mInstance;
    }

    public PrettyLoad setDrawable(int drawableId) {
        mInstance.mDrawableId = drawableId;
        return mInstance;
    }

    public PrettyLoad setColors(int... colorIds) {
        for(int id : colorIds) mInstance.mColorList.add(getResources().getColor(id));
        return mInstance;
    }

    public PrettyLoad setDuration(long duration) {
        mDuration = duration;
        return mInstance;
    }

    public static void start() {
        if(!mInstance.mColorList.isEmpty()) {
            for (int index = 0; index < mInstance.mDeclaredViewArray.size(); index++) {
                DeclaredView declaredView = mInstance.mDeclaredViewArray.valueAt(index);

                declaredView.setAnimator(ValueAnimator.ofObject(new ArgbEvaluator(), mInstance.mColorList.toArray()));
                declaredView.getAnimator().setDuration(mInstance.mDuration);
                declaredView.getAnimator().setRepeatMode(ValueAnimator.REVERSE);
                declaredView.getAnimator().setRepeatCount(ValueAnimator.INFINITE);
                declaredView.getAnimator().addUpdateListener(animator -> {
                    Drawable drawable;
                    if(mInstance.mDrawableId != 0) drawable = getResources().getDrawable(mInstance.mDrawableId);
                    else drawable = declaredView.getOldBackground();
                    drawable.setColorFilter((int) animator.getAnimatedValue(), PorterDuff.Mode.SRC_ATOP);
                    declaredView.getView().setBackground(drawable);
                });
                declaredView.getAnimator().start();
            }
        }
    }

    public static void stop() {
        for (int index = 0; index < mInstance.mDeclaredViewArray.size(); index++) {
            mInstance.mDeclaredViewArray.valueAt(index).stop();
        }
    }

    private <V extends View> void fillViewArray(Object rootObject) {
        for(Field field : rootObject.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(MakePretty.class)) {
                MakePretty makePretty = field.getAnnotation(MakePretty.class);
                if(makePretty.isPretty()) {
                    field.setAccessible(true);
                    try {
                        V view = (V) field.get(rootObject);
                        DeclaredView declaredView = new DeclaredView(view, makePretty);
                        mDeclaredViewArray.append(declaredView.getId(), declaredView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static Context getContext() {
        return mInstance.mContext;
    }

    private static Resources getResources() {
        return mInstance.mContext.getResources();
    }

    private class DeclaredView {

        private View mView;
        private MakePretty mAnnotation;
        private Drawable mOldBackground;
        private ValueAnimator mAnimator;

        private DeclaredView() {}

        public DeclaredView(View view, MakePretty annotation) {
            mView = view;
            mOldBackground = view.getBackground();
            mAnnotation = annotation;
        }

        public int getId() {
            return mView.getId();
        }

        public View getView() {
            return mView;
        }

        public void setView(View view) {
            this.mView = view;
        }

        public MakePretty getAnnotation() {
            return mAnnotation;
        }

        public void setAnnotation(MakePretty annotation) {
            this.mAnnotation = annotation;
        }

        public Drawable getOldBackground() {
            return mOldBackground;
        }

        public void setOldBackground(Drawable oldBackground) {
            this.mOldBackground = oldBackground;
        }

        public ValueAnimator getAnimator() {
            return mAnimator;
        }

        public void setAnimator(ValueAnimator animator) {
            this.mAnimator = animator;
        }

        public void stop() {
            mAnimator.end();
            mView.setBackground(mOldBackground);
        }
    }

}