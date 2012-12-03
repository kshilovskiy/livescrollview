package com.kshilovskiy.livescrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.BaseAdapter;
import android.widget.ScrollView;

/**
 * Created with IntelliJ IDEA.
 * User: konstantinshilovskiy
 * Date: 12/3/12
 */
public class LiveScrollView extends ScrollView{

    public LiveScrollView(Context context) {
        super(context);
    }

    public LiveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LiveScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private NonScrollableListView mListView;
    private int mMinElementHeight;
    private int mMaxElementHeight;


    public void setAdapter(BaseAdapter adapter){
        mListView.setAdapter(adapter);

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
