package com.kshilovskiy.listview.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.kshilovskiy.listview.R;

/**
 * Created with IntelliJ IDEA.
 * User: kshilovskiy
 * Date: 12/5/12
 */
public class SimpleArrayAdapter extends ArrayAdapter {

    private LayoutInflater mInflater;
    private String[] mItemTitles;
    private Drawable[] mDrawables;

    public SimpleArrayAdapter(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);

        mInflater = LayoutInflater.from(context);
        mItemTitles = objects;

        mDrawables = generateDrawableArray(context, objects.length);
    }

    @Override
    public int getCount() {
        return mItemTitles.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.simple_adapter_item, null);
            holder = new ViewHolder();

            holder.mTitle = (TextView) convertView.findViewById(R.id.titleView);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTitle.setText(mItemTitles[position]);
        holder.mImageView.setImageDrawable(mDrawables[position]);
        return convertView;
    }

    /**
     * Generates an array of random drawables from a set of 3 pictures
     * @param context
     * @param count
     * @return
     */
    private Drawable[] generateDrawableArray(Context context, int count){
        Drawable[] drawables = new Drawable[count];

        for(int i=0; i < count; i++){
            double rand = Math.random();

            if(rand < 0.3){
                drawables[i] = context.getResources().getDrawable(R.drawable.photo);
            }
            else if(rand < 0.6){
                drawables[i] = context.getResources().getDrawable(R.drawable.photo_concert);
            }
            else{
                drawables[i] = context.getResources().getDrawable(R.drawable.photo_park);
            }
        }

        return drawables;
    }

    class ViewHolder{
        TextView mTitle;
        ImageView mImageView;
    }
}
