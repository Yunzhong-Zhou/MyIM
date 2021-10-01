package com.im.myim.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.im.myim.R;
import com.im.myim.activity.NewsNotificationActivity;
import com.im.myim.activity.PublishNewsActivity;
import com.im.myim.adapter.Fragment2Adapter;
import com.im.myim.base.BaseFragment;
import com.im.myim.model.Fragment2Model;
import com.im.myim.utils.CommonUtil;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by fafukeji01 on 2016/1/6.
 * 工作台
 */
public class Fragment2 extends BaseFragment {
    private LinearLayout linearLayout1, linearLayout2, linearLayout3;
    private TextView textView1, textView2, textView3;
    private View view1, view2, view3;

    RelativeLayout rl_tongzhi;
    View view_hongdian;
    ImageView iv_fabu;


    RecyclerView recyclerView;
    List<Fragment2Model> list = new ArrayList<>();
    Fragment2Adapter adapter;

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
        /*if (MainActivity.item == 1) {
            //获取列表数据
            requestServer();
        }*/
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
//        findViewByID_My(R.id.headView).setPadding(0, (int) CommonUtil.getStatusBarHeight(getActivity()), 0, 0);
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

        linearLayout1 = findViewByID_My(R.id.linearLayout1);
        linearLayout2 = findViewByID_My(R.id.linearLayout2);
        linearLayout3 = findViewByID_My(R.id.linearLayout3);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);
        view1 = findViewByID_My(R.id.view1);
        view2 = findViewByID_My(R.id.view2);
        view3 = findViewByID_My(R.id.view3);

        rl_tongzhi = findViewByID_My(R.id.rl_tongzhi);
        rl_tongzhi.setOnClickListener(this);
        view_hongdian = findViewByID_My(R.id.view_hongdian);
        iv_fabu = findViewByID_My(R.id.iv_fabu);
        iv_fabu.setOnClickListener(this);

        recyclerView = findViewByID_My(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new Fragment2Adapter(getActivity(), dialog, list);
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void initData() {
        requestServer();

        //模拟数据
        String[] imageUrls = new String[]{
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic16.nipic.com%2F20111008%2F5203963_093910733000_2.jpg&refer=http%3A%2F%2Fpic16.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635510309&t=c5ceec21a925231ea7e5ea1083405369",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic15.nipic.com%2F20110708%2F3388327_164155701127_2.jpg&refer=http%3A%2F%2Fpic15.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512403&t=f91060b5f2fda181af1fb2490f684632",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic15.nipic.com%2F20110811%2F8029346_082444436000_2.jpg&refer=http%3A%2F%2Fpic15.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512403&t=ae71aa7b5bf511cd89be5de7980e414d",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic14.nipic.com%2F20110527%2F7539498_003034652127_2.jpg&refer=http%3A%2F%2Fpic14.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512403&t=e48c322fb888f3d5050a7df81f05631c",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic38.nipic.com%2F20140226%2F18049074_195317304156_2.jpg&refer=http%3A%2F%2Fpic38.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512403&t=fa8fd645ac8e7cb6c4ffbde68cb4cba7",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2Fmn01%2F041919151924%2F1Z419151924-6.jpg&refer=http%3A%2F%2Fpic.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512470&t=993368b4efb674fce0377a3de8e8a753",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.huabanimg.com%2F6c5940f96c2e9b1581f512eead01d233044d84b02092e-nONB1C_fw658&refer=http%3A%2F%2Fhbimg.huabanimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512470&t=4cd3ddab53d339b77a7f2b46e8dec782",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%253A%252F%252Fdingyue.ws.126.net%252F2021%252F0611%252F2e84f869j00qujnx3001nc000hs00hsc.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512470&t=cbae2cf91386d69f0cf631fe0f134ec4",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2F911%2F042516130027%2F160425130027-6.jpg&refer=http%3A%2F%2Fpic.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512470&t=795950e6e73130cfddf601a09e247613",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%253A%252F%252Fdingyue.ws.126.net%252F2021%252F0529%252F5a3b3393j00qttu9l0025c000hs00o0c.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512470&t=c4e780a6b955aaf44a13bd065959edff",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%3A%2F%2Fdingyue.ws.126.net%2F2021%2F0410%2F914ffef9j00qrcje4005td200tk00vcg007x008e.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512470&t=2dc7726d39f45f4f362d503762116f3e",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%3A%2F%2Fdingyue.ws.126.net%2F2021%2F0405%2F7379635bj00qr3gn9001rc000hs00m8m.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512470&t=370f91960cb1768a8b64415bb7ab3927",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%253A%252F%252Fdingyue.ws.126.net%252F2021%252F0611%252F46e685e4j00qui1730026c000hs00vlc.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512470&t=3d62d6f30ab4a89c9b86b7635f6cffd0",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%3A%2F%2Fdingyue.ws.126.net%2F2021%2F0320%2F73d30616j00qq8wuh001qc000h600ptc.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512470&t=98d6a5fa5c4f5348b2c4b7ef81883afe",
        };
        for (int i = 0; i < 20; i++) {
            List<String> imgUrls = new ArrayList<>();
            imgUrls.addAll(Arrays.asList(imageUrls).subList(0, i % 9));
            Fragment2Model model = new Fragment2Model("图片数量：" + imgUrls.size(), imgUrls);

            list.add(model);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayout1:
                textView1.setTextSize(18);
                textView2.setTextSize(14);
                textView3.setTextSize(14);
                textView1.setTypeface(null, Typeface.BOLD);
                textView2.setTypeface(null, Typeface.NORMAL);
                textView3.setTypeface(null, Typeface.NORMAL);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.INVISIBLE);

                break;
            case R.id.linearLayout2:
                textView1.setTextSize(14);
                textView2.setTextSize(18);
                textView3.setTextSize(14);
                textView1.setTypeface(null, Typeface.NORMAL);
                textView2.setTypeface(null, Typeface.BOLD);
                textView3.setTypeface(null, Typeface.NORMAL);
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.INVISIBLE);
                break;
            case R.id.linearLayout3:
                textView1.setTextSize(14);
                textView2.setTextSize(14);
                textView3.setTextSize(18);
                textView1.setTypeface(null, Typeface.NORMAL);
                textView2.setTypeface(null, Typeface.NORMAL);
                textView3.setTypeface(null, Typeface.BOLD);
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.VISIBLE);

                break;
            case R.id.rl_tongzhi:
                //动态通知
                CommonUtil.gotoActivity(getActivity(), NewsNotificationActivity.class, false);
                break;
            case R.id.iv_fabu:
                //发布动态
                CommonUtil.gotoActivity(getActivity(), PublishNewsActivity.class, false);
                break;
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


}
