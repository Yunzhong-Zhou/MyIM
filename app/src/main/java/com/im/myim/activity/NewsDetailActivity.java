package com.im.myim.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cy.dialog.BaseDialog;
import com.im.myim.R;
import com.im.myim.base.BaseActivity;
import com.im.myim.model.NewsDetailModel;
import com.im.myim.model.NewsNotificationModel;
import com.im.myim.net.URLs;
import com.im.myim.okhttp.CallBackUtil;
import com.im.myim.okhttp.OkhttpUtil;
import com.im.myim.popupwindow.PhotoShowDialog;
import com.im.myim.utils.BuySvipDialog;
import com.im.myim.utils.CommonUtil;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
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
 * 动态详情
 */
public class NewsDetailActivity extends BaseActivity {
    NineGridImageView mNglimg;

    List<String> list_more = new ArrayList<>();
    List<String> list_jubao = new ArrayList<>();
    private LinearLayout linearLayout1, linearLayout2;
    private TextView textView1, textView2;
    private View view1, view2;

    int type = 1;
    private RecyclerView recyclerView;
    List<NewsDetailModel> list = new ArrayList<>();
    CommonAdapter<NewsDetailModel> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);
    }

    @Override
    protected void initView() {
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

        mNglimg = findViewByID_My(R.id.mNglimg);
        NineGridImageViewAdapter<String> adapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String photo) {
                Glide.with(context)
                        .load(photo)
                        .centerCrop()
//                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                        .placeholder(R.mipmap.loading)//加载站位图
                        .error(R.mipmap.zanwutupian)//加载失败
                        .into(imageView);//加载图片
            }

            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }

            @Override
            protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
                super.onItemImageClick(context, imageView, index, list);
                PhotoShowDialog photoShowDialog = new PhotoShowDialog(context, list, index);
                photoShowDialog.show();
            }

        };
        mNglimg.setAdapter(adapter);




        linearLayout1 = findViewByID_My(R.id.linearLayout1);
        linearLayout2 = findViewByID_My(R.id.linearLayout2);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        view1 = findViewByID_My(R.id.view1);
        view2 = findViewByID_My(R.id.view2);
        List<String> list_img = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list_img.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%253A%252F%252Fdingyue.ws.126.net%252F2021%252F0611%252F46e685e4j00qui1730026c000hs00vlc.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1635512470&t=3d62d6f30ab4a89c9b86b7635f6cffd0");
        }
        mNglimg.setImagesData(list_img);

        recyclerView = findViewByID_My(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new CommonAdapter<NewsDetailModel>
                (NewsDetailActivity.this, R.layout.item_newsdetail, list) {
            @Override
            protected void convert(ViewHolder holder, NewsDetailModel model, int position) {
                LinearLayout ll_pinglun = holder.getView(R.id.ll_pinglun);
                if (type == 1) ll_pinglun.setVisibility(View.VISIBLE);
                else ll_pinglun.setVisibility(View.GONE);
                ImageView imageView1 = holder.getView(R.id.imageView1);
                Glide.with(mContext)
                        .load("")
                        .centerCrop()
//                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                        .placeholder(R.mipmap.loading)//加载站位图
                        .error(R.mipmap.zanwutupian)//加载失败
                        .into(imageView1);//加载图片

            }
        };
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        list_more.add("取消关注");
        list_more.add("举报");
        list_more.add("取消");

        list_jubao.add("诈骗欺诈");
        list_jubao.add("恶意骚扰");
        list_jubao.add("色情信息");
        list_jubao.add("性别不符");
        list_jubao.add("垃圾广告");
        list_jubao.add("政治敏感");
        list_jubao.add("取消");
        requestServer();

        for (int i = 0; i < 20; i++) {
            list.add(new NewsDetailModel());
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
    public void onClick(View v) {
        /*Drawable drawable1 = getResources().getDrawable(R.mipmap.down_green);//选中-蓝色
        Drawable drawable2 = getResources().getDrawable(R.mipmap.down_black);//未选-灰色
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());*/
        switch (v.getId()) {
            case R.id.linearLayout1:
                textView1.setTextColor(getResources().getColor(R.color.black1));
                textView2.setTextColor(getResources().getColor(R.color.black3));
//                textView1.setTextSize(18);
//                textView2.setTextSize(14);
//                textView1.setTypeface(null, Typeface.BOLD);
//                textView2.setTypeface(null, Typeface.NORMAL);
//                textView1.setCompoundDrawables(null, null, drawable1, null);
//                textView2.setCompoundDrawables(null, null, drawable2, null);
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.INVISIBLE);

                type = 1;
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.linearLayout2:
                textView1.setTextColor(getResources().getColor(R.color.black3));
                textView2.setTextColor(getResources().getColor(R.color.black1));
//                textView1.setTextSize(14);
//                textView2.setTextSize(18);
//                textView1.setTypeface(null, Typeface.NORMAL);
//                textView2.setTypeface(null, Typeface.BOLD);
//                textView1.setCompoundDrawables(null, null, drawable2, null);
//                textView2.setCompoundDrawables(null, null, drawable1, null);
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.VISIBLE);

                type = 2;
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.imageView1:
                //用户详情
                CommonUtil.gotoActivity(this, UserDetailActivity.class);
                break;
            case R.id.tv_xindong:
                //购买SVIP弹窗
                new BuySvipDialog(this, dialog, true) {
                    @Override
                    public void onCallBack() {

                    }
                };
                break;
        }
    }
    @Override
    protected void updateView() {
        titleView.setTitle("动态详情");
        titleView.setRightBtn(R.mipmap.ic_sandian,v->{
            dialogList("更多操作", list_more);
        });
    }
    /**
     * 选择弹窗
     */
    private void dialogList(String title, List<String> list) {
        dialog.contentView(R.layout.dialog_list_center)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT))
                .animType(BaseDialog.AnimInType.BOTTOM)
                .canceledOnTouchOutside(true)
                .gravity(Gravity.CENTER)
                .dimAmount(0.5f)
                .show();
        TextView tv_title = dialog.findViewById(R.id.tv_title);
        tv_title.setText(title);
        RecyclerView rv_list = dialog.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<String> adapter = new CommonAdapter<String>
                (NewsDetailActivity.this, R.layout.item_help, list) {
            @Override
            protected void convert(ViewHolder holder, String model, int position) {
                TextView tv = holder.getView(R.id.textView1);
                tv.setText(model);
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                dialog.dismiss();
                switch (list.get(position)) {
                    case "取消关注":
                        showToast("确定取消关注对方吗？", "取消关注后你将接受不到对方的消息，且在好友列表互相看不到对方。"
                                , v -> {
                                    //取消
                                    dialog.dismiss();
                                }, v -> {
                                    //确定


                                });
                        break;
                    case "举报":
                        dialogList("请选择举报原因", list_jubao);
                        break;

                    case "取消":

                        break;

                    default:
                        //举报原因

                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
        rv_list.setAdapter(adapter);
    }
}
