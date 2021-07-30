package com.demo.demo2;

import android.app.usage.UsageEvents;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @author: liercheng
 * @date: 2021/7/29
 * @description:
 */
public class MyAdapter extends BaseQuickAdapter<Bean, BaseViewHolder> {

    public MyAdapter(int layoutResId, @Nullable List<Bean> data) {
        super(R.layout.item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bean item) {
        CheckBox checkBox = helper.getView(R.id.cb);
        checkBox.setChecked(item.isCheck());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setCheck(!item.isCheck());
                EventBus.getDefault().post(new String());
            }
        });
    }
}
