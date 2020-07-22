package com.some.modulea;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "/moduleA/moduleA")
public class ModuleAActivity extends FragmentActivity {

    LinearLayout ll;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulea);

        ll = findViewById(R.id.root);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView item = new TextView(ModuleAActivity.this);
                item.setText("moduleA");
                ll.addView(item,0);
            }
        });
    }
}
