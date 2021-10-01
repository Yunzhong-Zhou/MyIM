package com.im.myim.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.im.myim.R;
import com.im.myim.base.BaseActivity;
import com.im.myim.model.NewsNotificationModel;
import com.im.myim.net.URLs;
import com.im.myim.okhttp.CallBackUtil;
import com.im.myim.okhttp.OkhttpUtil;
import com.im.myim.utils.CommonUtil;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mr.Z on 2021/10/1.
 * 动态通知
 */
public class NewsNotificationActivity extends BaseActivity {
    private RecyclerView recyclerView;
    List<NewsNotificationModel> list = new ArrayList<>();
    CommonAdapter<NewsNotificationModel> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsnotification);
    }

    @Override
    protected void initView() {
        recyclerView = findViewByID_My(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setSpringViewMore(false);//需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                /*page = 1;
                params.put("page", page + "");
                params.put("pageSize", "10");
                params.put("status", status);
                params.put("keyword", keyword);
                params.put("instudyId", instudyId);
                params.put("provinceId", provinceId);
                params.put("cityId", cityId);
                params.put("areaId", areaId);
                params.put("shopId",shopId);
                requestList(params);*/
            }

            @Override
            public void onLoadmore() {
                /*page = page + 1;
                //加载更多
                params.put("page", page + "");
                params.put("pageSize", "10");
                params.put("status", status);
                params.put("keyword", keyword);
                params.put("instudyId", instudyId);
                params.put("provinceId", provinceId);
                params.put("cityId", cityId);
                params.put("areaId", areaId);
                params.put("shopId",shopId);
                requestListMore(params);*/
            }
        });


        mAdapter = new CommonAdapter<NewsNotificationModel>
                (NewsNotificationActivity.this, R.layout.item_newsnotification, list) {
            @Override
            protected void convert(ViewHolder holder, NewsNotificationModel model, int position) {
                ImageView imageView1 = holder.getView(R.id.imageView1);
                ImageView imageView2 = holder.getView(R.id.imageView2);
                Glide.with(mContext)
                        .load("")
                        .centerCrop()
//                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                        .placeholder(R.mipmap.loading)//加载站位图
                        .error(R.mipmap.zanwutupian)//加载失败
                        .into(imageView1);//加载图片
                Glide.with(mContext)
                        .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%253A%252F%252Fdingyue.ws.126.net%252F2021%252F0529%252F5a3b3393j00qttu9l0025c000hs00o0c.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512470&t=c4e780a6b955aaf44a13bd065959edff")
                        .centerCrop()
//                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                        .placeholder(R.mipmap.loading)//加载站位图
                        .error(R.mipmap.zanwutupian)//加载失败
                        .into(imageView2);//加载图片

            }
        };
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                CommonUtil.gotoActivity(NewsNotificationActivity.this, NewsDetailActivity.class);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void initData() {
        requestServer();

        for (int i = 0; i < 20; i++) {
            list.add(new NewsNotificationModel());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestServer() {
        super.requestServer();
        /*this.showLoadingPage();
        page = 1;
        params.put("page", page + "");
        params.put("pageSize", "10");
        params.put("status", status);
        params.put("keyword", keyword);
        params.put("instudyId", instudyId);
        params.put("provinceId", provinceId);
        params.put("cityId", cityId);
        params.put("areaId", areaId);
        params.put("shopId",shopId);
        requestList(params);*/
    }

    private void requestList(Map<String, String> params) {
        OkhttpUtil.okHttpPost(URLs.Fragment2_3, params, headerMap, new CallBackUtil<NewsNotificationModel>() {
            @Override
            public NewsNotificationModel onParseResponse(Call call, Response response) {
                return null;
            }

            @Override
            public void onFailure(Call call, Exception e, String err) {
                showErrorPage();
                hideProgress();
                myToast(err);
            }

            @Override
            public void onResponse(NewsNotificationModel response) {
                showContentPage();
                hideProgress();
                /*list = response.getRecords();
                if (list.size() == 0) {
                    showEmptyPage();//空数据
                } else {
                    mAdapter = new CommonAdapter<NewsNotificationModel.RecordsBean>
                            (MyStoreListActivity.this, R.layout.item_fragment2_2, list) {
                        @Override
                        protected void convert(ViewHolder holder, NewsNotificationModel.RecordsBean model, int position) {
                            holder.setText(R.id.tv_name, model.getName());//标题
                            holder.setText(R.id.tv_shop, model.getDeviceNumber());
                            holder.setText(R.id.tv_num, model.getTotalRevenue());//money
                            holder.setText(R.id.tv_addr, model.getAddress());

                            ImageView imageView1 = holder.getView(R.id.imageView1);
                            Glide.with(MyStoreListActivity.this)
                                    .load(model.getImage())
//                                .fitCenter()
                                    .apply(RequestOptions.bitmapTransform(new
                                            RoundedCorners(CommonUtil.dip2px(MyStoreListActivity.this, 10))))
                                    .placeholder(R.mipmap.loading)//加载站位图
                                    .error(R.mipmap.zanwutupian)//加载失败
                                    .into(imageView1);//加载图片
                            ImageView imageView2 = holder.getView(R.id.imageView2);
                            if (model.getVisitStatus() != null && model.getVisitStatus().equals("3")) {
                                //已拜访
                                imageView2.setImageResource(R.mipmap.bg_yibaifang);
                            } else {
                                imageView2.setImageResource(R.mipmap.bg_daibaifang);
                            }
                            RelativeLayout relativeLayout =  holder.getView(R.id.relativeLayout);
                            if (selectItem == position){
                                relativeLayout.setVisibility(View.VISIBLE);
                            }else {
                                relativeLayout.setVisibility(View.GONE);
                            }
                            holder.getView(R.id.linearLayout).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (requestCode == Constant.SELECT_STORE) {
                                        selectItem = position;
                                        mAdapter.notifyDataSetChanged();
                                    } else {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", model.getId());
                                        CommonUtil.gotoActivityWithData(MyStoreListActivity.this, StoreDetailActivity.class, bundle, false);
                                    }
                                }
                            });
                        }
                    };
                    recyclerView.setAdapter(mAdapter);
                }*/

            }
        });

    }

    private void requestListMore(Map<String, String> params) {
        /*OkhttpUtil.okHttpPostJson(URLs.Fragment2_0, GsonUtils.toJson(params), headerMap, new CallBackUtil<NewsNotificationModel>() {
            @Override
            public NewsNotificationModel onParseResponse(Call call, Response response) {
                return null;
            }

            @Override
            public void onFailure(Call call, Exception e, String err) {
//                showErrorPage();
                hideProgress();
                myToast(err);
                page--;
            }

            @Override
            public void onResponse(NewsNotificationModel response) {
//                showContentPage();
                hideProgress();
                List<NewsNotificationModel.RecordsBean> list1 = new ArrayList<>();
                list1 = response.getRecords();
                if (list1.size() == 0) {
                    myToast(getString(R.string.app_nomore));
                    page--;
                } else {
                    list.addAll(list1);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });*/

    }

    @Override
    protected void updateView() {
        titleView.setTitle("动态通知");
    }
}
