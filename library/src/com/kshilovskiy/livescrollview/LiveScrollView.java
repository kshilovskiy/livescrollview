package com.kshilovskiy.livescrollview;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: konstantinshilovskiy
 * Date: 12/3/12
 */
public class LiveScrollView extends ScrollView{
    private static final String TAG = "LiveScrollView";

    private BaseAdapter mAdapter;
    private AdapterDataSetObserver mDataSetObserver;
    private int mResizableViewId;
    private NonScrollableListView mListView;
    private float mMinElementHeight;
    private float mMaxElementHeight;
    private int mChildCount = 0;
    private List<View> mParentViews;
    private List<View> mResizableViews;
    private float mFirstResizablePaddingTop;

    public LiveScrollView(Context context) {
        super(context);

        setupViews(context);
    }

    public LiveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LiveScrollView,
                0, 0);

        try {
            mMinElementHeight = a.getDimension(R.styleable.LiveScrollView_minResizableHeight, 0);
            mMaxElementHeight = a.getDimension(R.styleable.LiveScrollView_maxResizableHeight, 0);
            mFirstResizablePaddingTop = a.getDimension(R.styleable.LiveScrollView_firstResizablePaddingTop, Float.MIN_VALUE);
        } finally {
            a.recycle();
        }
        setupViews(context);
    }

    public LiveScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupViews(context);
    }

    public void setAdapter(BaseAdapter adapter){
        mListView.setAdapter(adapter);

        mAdapter = adapter;

        if(mAdapter != null && mDataSetObserver != null){
            adapter.unregisterDataSetObserver(mDataSetObserver);
        }

        mDataSetObserver = new AdapterDataSetObserver();
        mAdapter.registerDataSetObserver(mDataSetObserver);

        mDataSetObserver.onChanged();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        resizeChildren();

    }

    private void resizeChildren(){
        for(int i = 0; i < mChildCount; i++){
            View parentView = mParentViews.get(i);
            View resizableView = mResizableViews.get(i);

            int scrollY = getScrollY();
            int top = parentView.getTop() + parentView.getPaddingTop() - (int)mFirstResizablePaddingTop;

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)resizableView.getLayoutParams();
            float height = Math.min(mMaxElementHeight,Math.max(mMinElementHeight, mMaxElementHeight + (top - scrollY)));


            params.height = (int)height;

            resizableView.setLayoutParams(params);
        }

        requestLayout();
    }

    private void setupViews(Context context){
        mListView = new NonScrollableListView(context);

        mListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                calculateScrollValues();
                resizeChildren();
            }
        });

        addView(mListView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void calculateScrollValues(){
        int totalChildCount = mListView.getChildCount();

        mParentViews = new ArrayList<View>();
        mResizableViews = new ArrayList<View>();

        mChildCount = 0;
        for(int i = 0; i < totalChildCount; i++){


            View rootParentView = mListView.getChildAt(i);
            View resizebleView = rootParentView.findViewById(mResizableViewId);

            if(resizebleView != null){
                mParentViews.add(rootParentView);
                mResizableViews.add(resizebleView);

                mChildCount++;
            }

        }
    }

    public void setResizableViewId(int resizableViewId) {
        this.mResizableViewId = resizableViewId;
    }

    public void setMinElementHeight(float minElementHeight) {
        this.mMinElementHeight = minElementHeight;
    }

    public void setMaxElementHeight(float maxElementHeight) {
        this.mMaxElementHeight = maxElementHeight;
    }

    public void setmFirstResizablePaddingTop(float firstResizablePaddingTop) {
        this.mFirstResizablePaddingTop = firstResizablePaddingTop;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListView.setOnItemClickListener(listener);
    }

    private class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            calculateScrollValues();
        }
    }
}
