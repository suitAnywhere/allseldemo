package com.demo.demo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private CheckBox checkBox;
    private List<Bean> datas = new ArrayList<>();
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        checkBox = ((CheckBox) findViewById(R.id.checkBox));
        MyAdapter myAdapter = new MyAdapter(0, datas);
        recyclerView.setAdapter(myAdapter);
        for (int i = 0; i < 5; i++) {
            datas.add(new Bean());
        }
        myAdapter.notifyDataSetChanged();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("TAG", "onCheckedChanged: " + position);
                for (int i = 0; i < datas.size(); i++) {
                    if (position == -1) {
                        datas.get(i).setCheck(b);
                    } else {
                        if (!b) {
                            if (i == position) {
                                datas.get(i).setCheck(b);
                            }
                        } else {
                            datas.get(i).setCheck(b);
                        }
                    }

                }
                String str = "";
                for (int i = 0; i < datas.size(); i++) {
                    str = str + datas.get(i).isCheck() + ",";
                }
                Log.d("TAG", "onCheckedChanged: " + str);
                myAdapter.notifyDataSetChanged();
                position = -1;
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String event) {
        boolean isAllSel = true;
        String str = "";
        for (int i = 0; i < datas.size(); i++) {
            str = str + datas.get(i).isCheck() + ",";
        }
        Log.d("TAG", "onEvent: " + str);

        for (int i = 0; i < datas.size(); i++) {
            if (!datas.get(i).isCheck()) {
                position = i;
                Log.d("TAG", "onEvent: " + position);
                isAllSel = false;
                break;
            }
        }
        checkBox.setChecked(isAllSel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}