package com.im.myim.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cretin.tools.scancode.CaptureActivity;
import com.im.myim.R;
import com.im.myim.activity.MainActivity;
import com.im.myim.base.BaseFragment;
import com.im.myim.utils.CommonUtil;
import com.im.myim.utils.MyLogger;
import com.liaoinstan.springview.widget.SpringView;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * Created by fafukeji01 on 2016/1/6.
 * 工作台
 */
public class Fragment2 extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        StatusBarUtil.setTransparent(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onStart() {
        super.onStart();
        /*if (MainActivity.item == 1) {
            requestServer();
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.item == 1) {
            //获取列表数据
            requestServer();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        /*if (MainActivity.item == 1) {
            requestServer();
        }*/
    }

    @Override
    protected void initView(View view) {
        findViewByID_My(R.id.headView).setPadding(0, (int) CommonUtil.getStatusBarHeight(getActivity()), 0, 0);
        //刷新
        setSpringViewMore(true);//不需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //获取统计数量
                Map<String, String> params1 = new HashMap<>();
                params1.put("u_token", localUserInfo.getToken());
                params1.put("y_store_id", localUserInfo.getBelongid());
                RequestTongJi(params1);
            }

            @Override
            public void onLoadmore() {

            }
        });

    }

    @Override
    protected void initData() {
        requestServer();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void requestServer() {
        super.requestServer();
        this.showLoadingPage();
        //获取统计数量
        Map<String, String> params1 = new HashMap<>();
        params1.put("u_token", localUserInfo.getToken());
        params1.put("y_store_id", localUserInfo.getBelongid());
        RequestTongJi(params1);
    }

    /**
     * 获取统计数据
     */
    private void RequestTongJi(Map<String, String> params) {
        /*OkhttpUtil.okHttpPost(URLs.Fragment2_TongJi, params, headerMap, new CallBackUtil<Fragment2_TongJiModel>() {
            @Override
            public Fragment2_TongJiModel onParseResponse(Call call, Response response) {
                return null;
            }

            @Override
            public void onFailure(Call call, Exception e, String err) {
//                hideProgress();
//                showEmptyPage();
//                myToast(err);
            }

            @Override
            public void onResponse(Fragment2_TongJiModel response) {
//                hideProgress();
                list_tab.clear();
                list_tab.add(new Fragment2TabModel("待接车", response.getDai_jie_che_sum()));
                list_tab.add(new Fragment2TabModel("待分配", response.getDai_fen_pai_sum()));
                list_tab.add(new Fragment2TabModel("待施工", response.getDai_shi_gong_sum()));
                list_tab.add(new Fragment2TabModel("进行中", response.getJin_xing_zhong_sum()));
                list_tab.add(new Fragment2TabModel("待复检", response.getDai_fu_jian_sum()));
                list_tab.add(new Fragment2TabModel("已完工", response.getYi_wan_gong_sum()));

                list_tab.add(new Fragment2TabModel("已付款", response.getIs_pay_sum()));
                list_tab.add(new Fragment2TabModel("已提车", response.getYi_ti_che_sum()));

                list_tab.add(new Fragment2TabModel("救援", response.getJiu_yuan_sum()));
                mAdapter_tab.notifyDataSetChanged();

               *//* int num = response.getDai_jie_che_sum()
                        +response.getDai_fen_pai_sum()
                        +response.getDai_shi_gong_sum()
                        +response.getJin_xing_zhong_sum()
                        +response.getDai_fu_jian_sum()
                        +response.getYi_wan_gong_sum()
                        +response.getIs_pay_sum()
                        +response.getYi_ti_che_sum()
                        +response.getJiu_yuan_sum();*//*
                ((MainActivity) getActivity()).getNavigationBar().setMsgPointCount(1, response.getDai_shi_gong_sum());
            }
        });*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == CaptureActivity.REQUEST_CODE_SCAN) {
            // 扫描二维码回传
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    //获取扫描结果
                    Bundle bundle = data.getExtras();
                    String result = bundle.getString(CaptureActivity.EXTRA_SCAN_RESULT);
                    MyLogger.i("扫码返回", result);
                }
            }
        }
    }

}
