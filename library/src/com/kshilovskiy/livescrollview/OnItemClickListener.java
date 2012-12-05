package com.kshilovskiy.livescrollview;

import android.view.View;
import android.widget.BaseAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: kshilovskiy
 * Date: 12/5/12
 */
public interface OnItemClickListener {
    void onItemClick(BaseAdapter adapter, View view, int position);
}
