package com.im.myim.activity;

import android.os.Bundle;
import android.view.View;

import com.im.myim.R;
import com.im.myim.base.BaseActivity;
import com.im.myim.utils.CommonUtil;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
/**
 * Created by Mr.Z on 2021/9/30.
 * 守护列表
 */
public class ShouHuListActivity extends BaseActivity {
    List<String> list = new ArrayList<>();
    RecyclerView recyclerView;
    CommonAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar.reset().init();
        setContentView(R.layout.activity_shouhulist);
        findViewByID_My(R.id.headView).setPadding(0, (int) CommonUtil.getStatusBarHeight(this), 0, 0);

    }

    @Override
    protected void initView() {
        //刷新
        setSpringViewMore(true);//不需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                /*Map<String, String> params = new HashMap<>();
                params.put("page", page1 + "");
                params.put("u_token", localUserInfo.getToken());
                Request(params);*/
            }

            @Override
            public void onLoadmore() {
            }
        });
        recyclerView = findViewByID_My(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<String>
                (this, R.layout.item_shouhulist, list) {
            @Override
            protected void convert(ViewHolder holder, String model, int position) {
                holder.setText(R.id.tv, model);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 20; i++) {
            list.add(""+(i+2));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;

            case R.id.tv_shouhu:
                //守护TA
//                dialogShouHu();
                break;

        }
    }

    @Override
    protected void updateView() {
        titleView.setVisibility(View.GONE);
    }
}