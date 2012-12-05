package com.kshilovskiy.livescrollview;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * Created with IntelliJ IDEA.
 * User: kshilovskiy
 * Date: 9/18/12
 */
class NonScrollableListView extends LinearLayout {
    private BaseAdapter mAdapter;
    private AdapterDataSetObserver mDataSetObserver;
    private OnItemClickListener mItemClickListener;

    public NonScrollableListView(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public NonScrollableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonScrollableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mAdapter != null && mDataSetObserver != null) {
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
    }

    public void setAdapter(BaseAdapter adapter) {
        this.mAdapter = adapter;

        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }

        mDataSetObserver = new AdapterDataSetObserver();
        mAdapter.registerDataSetObserver(mDataSetObserver);

        mDataSetObserver.onChanged();
    }

    private void fillChildViews() {
        if (mAdapter != null) {
            int requiredChilrenCount = mAdapter.getCount();
            int currentChildrenCount = getChildCount();

            for (int i = 0; i < requiredChilrenCount; i++) {
                View nextChild = getChildAt(i);
                View nextChildToAdd = mAdapter.getView(i, nextChild, this);
                nextChildToAdd.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                boolean isEnabled = mAdapter.isEnabled(i);

                if (isEnabled) {
                    final int position = i;
                    nextChildToAdd.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mItemClickListener != null) {

                                mItemClickListener.onItemClick(mAdapter, view, position);
                            }
                        }
                    });
                } else {
                    nextChildToAdd.setOnClickListener(null);
                }

                if (nextChild == null) {
                    addView(nextChildToAdd);
                }

            }

            //Remove remaining child views if any
            for (int i = requiredChilrenCount; i < currentChildrenCount; i++) {
                //The length of the children list changes so need to get it at each iteration
                removeViewAt(getChildCount() - 1);
            }
        } else {
            removeAllViews();
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    private class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            fillChildViews();
        }
    }
}
