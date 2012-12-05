package com.kshilovskiy.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created with IntelliJ IDEA.
 * User: kshilovskiy
 * Date: 12/5/12
 */
public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        Button simpleButton = (Button)findViewById(R.id.simpleTypeButton);
        simpleButton.setOnClickListener(this);

        Button multitypeButton = (Button)findViewById(R.id.multiTypeButton);
        multitypeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String action = null;

        if(view.getId() == R.id.multiTypeButton){
            action = ScrollViewActivity.TYPE_MULTITYPE;
        }

        Intent i = new Intent(this, ScrollViewActivity.class);
        i.setAction(action);
        startActivity(i);
    }
}
