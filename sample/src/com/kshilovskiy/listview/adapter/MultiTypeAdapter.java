package com.kshilovskiy.listview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.kshilovskiy.listview.R;

/**
 * Created with IntelliJ IDEA.
 * User: kshilovskiy
 * Date: 12/5/12
 */
public class MultiTypeAdapter extends SimpleArrayAdapter {

    private Context mContext;

    public MultiTypeAdapter(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
        mContext = context;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    @Override
    public Object getItem(int position) {
        if(position > 0){
            return super.getItem(position - 1);
        }

        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(position == 0){
            ImageView mapImageView = new ImageView(mContext);
            mapImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mapImageView.setImageResource(R.drawable.map);

            return mapImageView;
        }

        return super.getView(position - 1 , convertView, parent);
    }
}
