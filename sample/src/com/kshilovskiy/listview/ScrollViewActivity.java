package com.kshilovskiy.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;
import com.kshilovskiy.listview.adapter.MultiTypeAdapter;
import com.kshilovskiy.listview.adapter.SimpleArrayAdapter;
import com.kshilovskiy.livescrollview.LiveScrollView;
import com.kshilovskiy.livescrollview.OnItemClickListener;


public class ScrollViewActivity extends Activity implements OnItemClickListener {
    public static final String TYPE_MULTITYPE = "com.kshilovskiy.listview.action.TYPE_MULTITYPE";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getIntent().getAction();

        setContentView(R.layout.simple_scrollview);

        LiveScrollView liveScrollView = (LiveScrollView) findViewById(R.id.liveScrollView);

        //Specifies the view we want to resize
        liveScrollView.setResizableViewId(R.id.imageView);

        SimpleArrayAdapter arrayAdapter;

        if(TYPE_MULTITYPE.equals(action)){
            arrayAdapter = new MultiTypeAdapter(this, 0, GENRES);

            //You can set top offset programatically or use an xml attribute 'livescrollview:firstResizablePaddingTop'
            float topOffset = getResources().getDimension(R.dimen.top_offset);

            //This attribute can also be set in XML layout but since we are using one layout for different adapter,
            // set it programatically
            liveScrollView.setmFirstResizablePaddingTop(topOffset);
        }
        else{
            arrayAdapter = new SimpleArrayAdapter(this, 0, GENRES);
        }

        liveScrollView.setAdapter(arrayAdapter);
        liveScrollView.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(BaseAdapter adapter, View view, int position) {
        Toast.makeText(this, (String)adapter.getItem(position), Toast.LENGTH_SHORT).show();

    }

    private static final String[] GENRES = new String[] {
            "Action", "Adventure", "Animation", "Children", "Comedy", "Documentary", "Drama",
            "Foreign", "History", "Independent", "Romance", "Sci-Fi", "Television", "Thriller"
    };
}
