package com.im.myim.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cy.cyflowlayoutlibrary.FlowLayout;
import com.cy.cyflowlayoutlibrary.FlowLayoutAdapter;
import com.cy.dialog.BaseDialog;
import com.im.myim.R;
import com.im.myim.activity.SVIPActivity;
import com.im.myim.activity.UserDetailActivity;
import com.im.myim.activity.WebContentActivity;
import com.im.myim.base.BaseFragment;
import com.im.myim.model.CommonModel;
import com.im.myim.model.Fragment1Model;
import com.im.myim.net.URLs;
import com.im.myim.okhttp.CallBackUtil;
import com.im.myim.okhttp.OkhttpUtil;
import com.im.myim.utils.BuySvipDialog;
import com.im.myim.utils.CommonUtil;
import com.im.myim.utils.MyLogger;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by fafukeji01 on 2016/1/6.
 * 首页
 */

public class Fragment1 extends BaseFragment implements CardStackListener {
    private LinearLayout linearLayout1, linearLayout2;
    private TextView textView1, textView2;
    private View view1, view2;

    //卡片层叠
    CardStackView cardStackView;
    List<String> list = new ArrayList<>();
    CommonAdapter<String> mAdapter;
    CardStackLayoutManager manager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if (MainActivity.item == 0) {
            requestServer();
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        /*if (MainActivity.item == 0) {
            requestServer();
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initView(View view) {
//        findViewByID_My(R.id.headView).setPadding(0, (int) CommonUtil.getStatusBarHeight(getActivity()), 0, 0);
        //刷新
        /*setSpringViewMore(false);//不需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                *//*Map<String, String> params = new HashMap<>();
                params.put("page", page1 + "");
                params.put("u_token", localUserInfo.getToken());
                Request(params);*//*
            }

            @Override
            public void onLoadmore() {
            }
        });*/
        linearLayout1 = findViewByID_My(R.id.linearLayout1);
        linearLayout2 = findViewByID_My(R.id.linearLayout2);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        view1 = findViewByID_My(R.id.view1);
        view2 = findViewByID_My(R.id.view2);

        cardStackView = findViewByID_My(R.id.cardStackView);
        manager = new CardStackLayoutManager(getActivity(), this);
        manager.setStackFrom(StackFrom.Top);//卡片层叠样式
        manager.setVisibleCount(3);//层数
        manager.setTranslationInterval(8.0f);//层叠间距
        manager.setScaleInterval(0.95f);//层叠缩放比例
        manager.setMaxDegree(20.0f);//滑动最大角度
        manager.setDirections(Direction.HORIZONTAL);//滑动动画
        manager.setSwipeThreshold(0.3f);//
        manager.setCanScrollHorizontal(true);//能否平滑
        manager.setCanScrollVertical(true);//能否竖滑
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        cardStackView.setLayoutManager(manager);


        setData();
        mAdapter = new CommonAdapter<String>
                (getActivity(), R.layout.item_fragment1, list) {
            @Override
            protected void convert(ViewHolder holder, String model, int position) {
                ImageView iv = holder.getView(R.id.iv);
                Glide.with(getActivity()).load(model)
                        .centerCrop()
//                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                        .placeholder(R.mipmap.loading)//加载站位图
                        .error(R.mipmap.zanwutupian)//加载失败
                        .into(iv);//加载图片

                List<CommonModel> list_jiben = new ArrayList<>();
                list_jiben.add(new CommonModel("0", R.mipmap.ic_nan));
                list_jiben.add(new CommonModel("0cm", null));
                list_jiben.add(new CommonModel("0kg", null));
                list_jiben.add(new CommonModel("job", null));
                list_jiben.add(new CommonModel("address", null));

                //标签
                FlowLayoutAdapter<CommonModel> flowLayoutAdapter1 = new FlowLayoutAdapter<CommonModel>(list_jiben) {
                    @Override
                    public void bindDataToView(FlowLayoutAdapter.ViewHolder holder, int position, CommonModel bean) {
                        LinearLayout ll = holder.getView(R.id.ll);
                        ImageView iv = holder.getView(R.id.iv);
                        if (bean.getImg()!=null){
                            iv.setVisibility(View.VISIBLE);
                            iv.setImageDrawable(getResources().getDrawable(bean.getImg()));
                        }else {
                            iv.setVisibility(View.GONE);
                        }

                        TextView tv = holder.getView(R.id.tv);
                        tv.setText(bean.getName());
//                tv.setTextColor(getResources().getColor(R.color.white));

                    }

                    @Override
                    public void onItemClick(int position, CommonModel bean) {
//                        showToast("点击" + position);
                    }

                    @Override
                    public int getItemLayoutID(int position, CommonModel bean) {
                        return R.layout.item_flowlayout2;
                    }
                };
                ((FlowLayout) holder.getView(R.id.flowLayout1)).setAdapter(flowLayoutAdapter1);

                holder.getView(R.id.rl_xindong).setOnClickListener(v -> {
                    //心动弹窗
//                    showBuySvipDialog();
                    new BuySvipDialog(getActivity(), dialog, true) {
                        @Override
                        public void onCallBack() {

                        }
                    };
                });

                holder.getView(R.id.cardView).setOnClickListener(v->{
                    //跳转详情
                    CommonUtil.gotoActivity(getActivity(), UserDetailActivity.class);
                });
            }
        };

        cardStackView.setAdapter(mAdapter);


    }

    @Override
    protected void initData() {
//        requestServer();
    }

    private void setData() {
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
        //生成数据集，用来保存随即生成数，并用于判断
        List<Integer> mylist = new ArrayList();
        Random rd = new Random();
        while (mylist.size() < imageUrls.length) {
            int num = rd.nextInt(imageUrls.length);
            if (!mylist.contains(num)) {
                mylist.add(num); //往集合里面添加数据。
            }
        }
        for (int i = 0; i < imageUrls.length; i++) {
            MyLogger.i(">>>>>>" + mylist.get(i));
            list.add(imageUrls[mylist.get(i)]);
        }

        /*for (String url : imageUrls) {
            list.add(url);
        }*/
    }

    @Override
    public void requestServer() {
        super.requestServer();
        this.showLoadingPage();
        Map<String, String> params = new HashMap<>();
        params.put("u_token", localUserInfo.getToken());
        Request(params);
    }

    private void Request(Map<String, String> params) {
        OkhttpUtil.okHttpPost(URLs.Fragment1, params, headerMap, new CallBackUtil<Fragment1Model>() {
            @Override
            public Fragment1Model onParseResponse(Call call, Response response) {
                return null;
            }

            @Override
            public void onFailure(Call call, Exception e, String err) {
                hideProgress();
                showEmptyPage();
                myToast(err);
            }

            @Override
            public void onResponse(Fragment1Model response) {
                hideProgress();

            }
        });
    }

    private void RequestUpData(Map<String, String> params) {
        OkhttpUtil.okHttpPost(URLs.JiShuJiaoLiuDetail_JiaRu, params, headerMap, new CallBackUtil() {
            @Override
            public Object onParseResponse(Call call, Response response) {
                return null;
            }

            @Override
            public void onFailure(Call call, Exception e, String err) {
                hideProgress();
                if (!err.equals("")) {
                    showToast(err);
                }
            }

            @Override
            public void onResponse(Object response) {
                hideProgress();
                /*showToast("回复成功", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        requestServer();
                    }
                });*/
                myToast("加入成功");
                requestServer();
            }
        });
    }

    @Override
    public void onClick(View v) {
        /*Drawable drawable1 = getResources().getDrawable(R.mipmap.down_green);//选中-蓝色
        Drawable drawable2 = getResources().getDrawable(R.mipmap.down_black);//未选-灰色
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());*/
        switch (v.getId()) {
            case R.id.linearLayout1:
//                textView1.setTextColor(getResources().getColor(R.color.pink));
//                textView2.setTextColor(getResources().getColor(R.color.black1));
                textView1.setTextSize(18);
                textView2.setTextSize(14);
                textView1.setTypeface(null, Typeface.BOLD);
                textView2.setTypeface(null, Typeface.NORMAL);
//                textView1.setCompoundDrawables(null, null, drawable1, null);
//                textView2.setCompoundDrawables(null, null, drawable2, null);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.INVISIBLE);
                break;
            case R.id.linearLayout2:
//                textView1.setTextColor(getResources().getColor(R.color.black1));
//                textView2.setTextColor(getResources().getColor(R.color.pink));
                textView1.setTextSize(14);
                textView2.setTextSize(18);
                textView1.setTypeface(null, Typeface.NORMAL);
                textView2.setTypeface(null, Typeface.BOLD);
//                textView1.setCompoundDrawables(null, null, drawable2, null);
//                textView2.setCompoundDrawables(null, null, drawable1, null);
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void updateView() {

    }
    /**
     * ********************************购买SVIP弹窗******************************************
     */
    private void showBuySvipDialog() {
        dialog.contentView(R.layout.dialog_buysvip)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT))
                .animType(BaseDialog.AnimInType.CENTER)
                .gravity(Gravity.CENTER)
                .canceledOnTouchOutside(true)
                .dimAmount(0.8f)
                .show();

        LinearLayout ll1 = dialog.findViewById(R.id.ll1);
        LinearLayout ll2 = dialog.findViewById(R.id.ll2);
        LinearLayout ll3 = dialog.findViewById(R.id.ll3);

        TextView tv_time1 = dialog.findViewById(R.id.tv_time1);
        TextView tv_time2 = dialog.findViewById(R.id.tv_time2);
        TextView tv_time3 = dialog.findViewById(R.id.tv_time3);

        TextView tv_price1 = dialog.findViewById(R.id.tv_price1);
        TextView tv_price2 = dialog.findViewById(R.id.tv_price2);
        TextView tv_price3 = dialog.findViewById(R.id.tv_price3);

        TextView tv_money1 = dialog.findViewById(R.id.tv_money1);
        TextView tv_money2 = dialog.findViewById(R.id.tv_money2);
        TextView tv_money3 = dialog.findViewById(R.id.tv_money3);

        TextView tv_shiyong = dialog.findViewById(R.id.tv_shiyong);
        TextView textView = dialog.findViewById(R.id.textView);

        showTxtHint(textView,"3天免费试用，之后12元/月，您可以在设置中取消订阅,否则自动续订，确认即代表您同意%1$s");
        dialog.findViewById(R.id.rl1).setOnClickListener(v -> {
            //免费
            ll1.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_fense_4);
            ll2.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_huise_4);
            ll3.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_huise_4);

            tv_time1.setTextColor(getResources().getColor(R.color.pink));
            tv_time2.setTextColor(getResources().getColor(R.color.grey));
            tv_time3.setTextColor(getResources().getColor(R.color.grey));

            tv_price1.setTextColor(getResources().getColor(R.color.pink));
            tv_price2.setTextColor(getResources().getColor(R.color.grey));
            tv_price3.setTextColor(getResources().getColor(R.color.grey));

            tv_money1.setTextColor(getResources().getColor(R.color.white));
            tv_money2.setTextColor(getResources().getColor(R.color.grey));
            tv_money3.setTextColor(getResources().getColor(R.color.grey));

            tv_money1.setBackgroundResource(R.drawable.yuanjiao_10_fense_bottom);
            tv_money2.setBackgroundResource(R.color.transparent);
            tv_money3.setBackgroundResource(R.color.transparent);

            tv_shiyong.setVisibility(View.VISIBLE);

            showTxtHint(textView,"3天免费试用，之后12元/月，您可以在设置中取消订阅,否则自动续订，确认即代表您同意%1$s");

        });
        dialog.findViewById(R.id.rl2).setOnClickListener(v -> {
            //1个月
            ll1.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_huise_4);
            ll2.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_fense_4);
            ll3.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_huise_4);

            tv_time1.setTextColor(getResources().getColor(R.color.grey));
            tv_time2.setTextColor(getResources().getColor(R.color.pink));
            tv_time3.setTextColor(getResources().getColor(R.color.grey));

            tv_price1.setTextColor(getResources().getColor(R.color.grey));
            tv_price2.setTextColor(getResources().getColor(R.color.pink));
            tv_price3.setTextColor(getResources().getColor(R.color.grey));

            tv_money1.setTextColor(getResources().getColor(R.color.grey));
            tv_money2.setTextColor(getResources().getColor(R.color.white));
            tv_money3.setTextColor(getResources().getColor(R.color.grey));

            tv_money1.setBackgroundResource(R.color.transparent);
            tv_money2.setBackgroundResource(R.drawable.yuanjiao_10_fense_bottom);
            tv_money3.setBackgroundResource(R.color.transparent);

            tv_shiyong.setVisibility(View.INVISIBLE);
            showTxtHint(textView,"1个月内使用，之后12元/月，您可以在设置中取消订阅,否则自动续订，确认即代表您同意%1$s");
        });
        dialog.findViewById(R.id.rl3).setOnClickListener(v -> {
            //3个月
            ll1.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_huise_4);
            ll2.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_huise_4);
            ll3.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_fense_4);

            tv_time1.setTextColor(getResources().getColor(R.color.grey));
            tv_time2.setTextColor(getResources().getColor(R.color.grey));
            tv_time3.setTextColor(getResources().getColor(R.color.pink));

            tv_price1.setTextColor(getResources().getColor(R.color.grey));
            tv_price2.setTextColor(getResources().getColor(R.color.grey));
            tv_price3.setTextColor(getResources().getColor(R.color.pink));

            tv_money1.setTextColor(getResources().getColor(R.color.grey));
            tv_money2.setTextColor(getResources().getColor(R.color.grey));
            tv_money3.setTextColor(getResources().getColor(R.color.white));

            tv_money1.setBackgroundResource(R.color.transparent);
            tv_money2.setBackgroundResource(R.color.transparent);
            tv_money3.setBackgroundResource(R.drawable.yuanjiao_10_fense_bottom);

            tv_shiyong.setVisibility(View.INVISIBLE);
            showTxtHint(textView,"3个月内使用，之后12元/月，您可以在设置中取消订阅,否则自动续订，确认即代表您同意%1$s");
        });

        dialog.findViewById(R.id.tv_more).setOnClickListener(v -> {
            //跳转SVIP页面
            CommonUtil.gotoActivity(getActivity(), SVIPActivity.class, false);
        });
        dialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            dialog.dismiss();
            //支付宝支付
        });

        dialog.findViewById(R.id.dismiss).setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    private void showTxtHint(TextView textView,String s) {
        //文本提示
        String text = String.format(s, "《自动续费协议》");
        int index[] = new int[1];
        index[0] = text.indexOf("《自动续费协议》");

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder style=new SpannableStringBuilder(text);
        style.setSpan(new ClickableSpan() {
                          @Override
                          public void onClick(@NonNull View widget) {
                              Bundle bundle = new Bundle();
                              bundle.putString("url", URLs.HOST + "/single/h5/register?user_hash="+localUserInfo.getUserHash());
                              CommonUtil.gotoActivityWithData(getActivity(), WebContentActivity.class, bundle, false);
                          }

                          @Override
                          public void updateDrawState(@NonNull TextPaint ds) {
                              super.updateDrawState(ds);
                              ds.setColor(getResources().getColor(R.color.blue));
//                              new ForegroundColorSpan(getResources().getColor(R.color.green));
                          }
                      },
                index[0],
                index[0] + "《自动续费协议》".length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(style);
    }

    /**
     * ********************************卡片拖动监听******************************************
     */
    @Override
    public void onCardDragging(Direction direction, float ratio) {
        //拖动卡片时调用
    }

    @Override
    public void onCardSwiped(Direction direction) {
        //刷卡时调用
    }

    @Override
    public void onCardRewound() {
        //卡片倒带时调用
    }

    @Override
    public void onCardCanceled() {
        //卡片拖动小于阈值时调用
    }

    @Override
    public void onCardAppeared(View view, int position) {
        //卡片出现时调用
        MyLogger.i(">卡片出现>>>>第" + position + "张");
        //        if (manager.getTopPosition() == list.size() - 5) {
        if (position == list.size() - 5) {
            MyLogger.i(">卡片更新>>>>第" + position + "张");
            List<String> newlist = new ArrayList<>();
            setData();
            newlist.addAll(list);
//            mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemRangeInserted(list.size() - 1, newlist.size());
        }

    }

    @Override
    public void onCardDisappeared(View view, int position) {
        //卡片消失时调用

    }
}
