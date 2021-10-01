package com.im.myim.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cy.cyflowlayoutlibrary.FlowLayout;
import com.cy.cyflowlayoutlibrary.FlowLayoutAdapter;
import com.cy.dialog.BaseDialog;
import com.im.myim.R;
import com.im.myim.base.BaseActivity;
import com.im.myim.model.CommonModel;
import com.im.myim.popupwindow.PhotoShowDialog;
import com.im.myim.utils.CommonUtil;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Mr.Z on 2021/9/30.
 */
public class UserDetailActivity extends BaseActivity {
    List<String> list_more = new ArrayList<>();
    List<String> list_jubao = new ArrayList<>();

    //基本标签
    List<CommonModel> list_jiben = new ArrayList<>();
    FlowLayout flowLayout1;
    FlowLayoutAdapter<CommonModel> flowLayoutAdapter1;
    //横向图片
    List<String> list_img = new ArrayList<>();
    RecyclerView rv1;
    CommonAdapter<String> ca1;
    //认证信息
    List<CommonModel> list_renzheng = new ArrayList<>();
    RecyclerView rv2;
    CommonAdapter<CommonModel> ca2;
    //基本资料
    FlowLayout flowLayout2;
    FlowLayoutAdapter<String> flowLayoutAdapter2;

    //我的标签
    FlowLayout flowLayout3;
    FlowLayoutAdapter<String> flowLayoutAdapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar.reset().init();
        setContentView(R.layout.activity_userdetail);
        findViewByID_My(R.id.headView).setPadding(0, (int) CommonUtil.getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initView() {
        //刷新
        setSpringViewMore(false);//不需要加载更多
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
        //基本标签
        flowLayout1 = findViewByID_My(R.id.flowLayout1);
        flowLayoutAdapter1 = new FlowLayoutAdapter<CommonModel>(list_jiben) {
            @Override
            public void bindDataToView(FlowLayoutAdapter.ViewHolder holder, int position, CommonModel bean) {
                LinearLayout ll = holder.getView(R.id.ll);
                ImageView iv = holder.getView(R.id.iv);
                iv.setImageDrawable(getResources().getDrawable(bean.getImg()));
                TextView tv = holder.getView(R.id.tv);
                tv.setText(bean.getName());
//                tv.setTextColor(getResources().getColor(R.color.white));
                switch (position) {
                    case 0:
                        ll.setBackgroundResource(R.drawable.yuanjiao_10_lanse);
                        break;
                    case 1:
                        ll.setBackgroundResource(R.drawable.yuanjiao_10_fense);
                        break;
                    case 2:
                        ll.setBackgroundResource(R.drawable.yuanjiao_10_zise);
                        break;
                    case 3:
                        ll.setBackgroundResource(R.drawable.yuanjiao_10_hongse);
                        break;
                    default:
                        break;
                }

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
        flowLayout1.setAdapter(flowLayoutAdapter1);

        //横向图片
        rv1 = findViewByID_My(R.id.rv1);
        LinearLayoutManager llm1 = new LinearLayoutManager(UserDetailActivity.this);
        llm1.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        rv1.setLayoutManager(llm1);
        ca1 = new CommonAdapter<String>
                (UserDetailActivity.this, R.layout.item_img_60_60, list_img) {
            @Override
            protected void convert(ViewHolder holder, String model, int position) {
                ImageView iv = holder.getView(R.id.iv);
                Glide.with(UserDetailActivity.this).load(model)
                        .centerCrop()
//                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                        .placeholder(R.mipmap.loading)//加载站位图
                        .error(R.mipmap.zanwutupian)//加载失败
                        .into(iv);//加载图片
            }
        };
        ca1.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                PhotoShowDialog photoShowDialog = new PhotoShowDialog(UserDetailActivity.this, list_img, i);
                photoShowDialog.show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
        rv1.setAdapter(ca1);

        setData();
        ca1.notifyDataSetChanged();

        //认证信息
        rv2 = findViewByID_My(R.id.rv2);
        rv2.setLayoutManager(new GridLayoutManager(UserDetailActivity.this, 4));
        ca2 = new CommonAdapter<CommonModel>
                (UserDetailActivity.this, R.layout.item_renzheng, list_renzheng) {
            @Override
            protected void convert(ViewHolder holder, CommonModel model, int position) {
                holder.setText(R.id.tv, model.getName());

                ImageView iv = holder.getView(R.id.iv);
                //设置图片
                iv.setImageDrawable(getResources().getDrawable(model.getImg()));
            }
        };
        rv2.setAdapter(ca2);

        //基本资料
        flowLayout2 = findViewByID_My(R.id.flowLayout2);
        flowLayoutAdapter2 = new FlowLayoutAdapter<String>(list_jubao) {
            @Override
            public void bindDataToView(FlowLayoutAdapter.ViewHolder holder, int position, String bean) {
//                                holder.setText(R.id.tv,bean);

                TextView tv = holder.getView(R.id.tv);
                tv.setText(bean);
//                tv.setTextColor(getResources().getColor(R.color.black1));
//                tv.setBackgroundResource(R.drawable.yuanjiao_3_huise);
            }

            @Override
            public void onItemClick(int position, String bean) {
//                        showToast("点击" + position);
            }

            @Override
            public int getItemLayoutID(int position, String bean) {
                return R.layout.item_flowlayout1;
            }
        };
        flowLayout2.setAdapter(flowLayoutAdapter2);

        //我的标签
        flowLayout3 = findViewByID_My(R.id.flowLayout3);
        flowLayoutAdapter3 = new FlowLayoutAdapter<String>(list_more) {
            @Override
            public void bindDataToView(FlowLayoutAdapter.ViewHolder holder, int position, String bean) {
//                                holder.setText(R.id.tv,bean);

                TextView tv = holder.getView(R.id.tv);
                tv.setText(bean);
//                tv.setTextColor(getResources().getColor(R.color.black1));
//                tv.setBackgroundResource(R.drawable.yuanjiao_3_huise);
            }

            @Override
            public void onItemClick(int position, String bean) {
//                        showToast("点击" + position);
            }

            @Override
            public int getItemLayoutID(int position, String bean) {
                return R.layout.item_flowlayout1;
            }
        };
        flowLayout3.setAdapter(flowLayoutAdapter3);


    }

    @Override
    protected void initData() {
        list_more.add("推荐给好友");
        list_more.add("举报");
        list_more.add("拉黑");
        list_more.add("取消");

        list_jubao.add("诈骗欺诈");
        list_jubao.add("恶意骚扰");
        list_jubao.add("色情信息");
        list_jubao.add("性别不符");
        list_jubao.add("垃圾广告");
        list_jubao.add("政治敏感");
        list_jubao.add("取消");

        list_jiben.add(new CommonModel("0", R.mipmap.ic_nan));
        list_jiben.add(new CommonModel("0", R.mipmap.ic_zhuanshi));
        list_jiben.add(new CommonModel("0", R.mipmap.ic_xin_white1));
        list_jiben.add(new CommonModel("SVIP", R.mipmap.ic_king));

        flowLayoutAdapter1.notifyDataSetChanged();
        flowLayoutAdapter2.notifyDataSetChanged();
        flowLayoutAdapter3.notifyDataSetChanged();


        list_renzheng.add(new CommonModel("真人未认证", R.mipmap.ic_renzheng1_0));
        list_renzheng.add(new CommonModel("实名未认证", R.mipmap.ic_renzheng2_0));
        list_renzheng.add(new CommonModel("手机未认证", R.mipmap.ic_renzheng3_0));
        list_renzheng.add(new CommonModel("学历未认证", R.mipmap.ic_renzheng4_0));
        list_renzheng.add(new CommonModel("车辆未认证", R.mipmap.ic_renzheng5_0));
        list_renzheng.add(new CommonModel("房产未认证", R.mipmap.ic_renzheng6_0));
        ca2.notifyDataSetChanged();
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
            list_img.add(imageUrls[mylist.get(i)]);
        }

        /*for (String url : imageUrls) {
            list.add(url);
        }*/
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;
            case R.id.right_btn:
                //更多弹窗
                dialogList("更多操作", list_more);
                break;
            case R.id.tv_shouhu:
                //守护TA
                dialogShouHu();
                break;

        }
    }

    @Override
    protected void updateView() {
        titleView.setVisibility(View.GONE);
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
                (UserDetailActivity.this, R.layout.item_help, list) {
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
                    case "举报":
                        dialogList("请选择举报原因", list_jubao);
                        break;
                    case "推荐给好友":
                        dialog_Share();
                        break;
                    case "拉黑":
                        showToast("确定要拉黑对方吗？", "拉黑后你将接受不到对方的消息，且在好友列表互相看不到对方。"
                                , v -> {
                                    //取消
                                    dialog.dismiss();
                                }, v -> {
                                    //确定

                                });
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

    /**
     * 分享弹窗
     */
    private void dialog_Share() {
        dialog.contentView(R.layout.dialog_share)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT))
                .animType(BaseDialog.AnimInType.BOTTOM)
                .canceledOnTouchOutside(true)
                .gravity(Gravity.BOTTOM)
                .dimAmount(0.5f)
                .show();
        dialog.findViewById(R.id.textView1).setOnClickListener(v -> {

        });
        dialog.findViewById(R.id.textView2).setOnClickListener(v -> {

        });
        dialog.findViewById(R.id.textView3).setOnClickListener(v -> {

        });
        dialog.findViewById(R.id.textView4).setOnClickListener(v -> {

        });
        dialog.findViewById(R.id.textView5).setOnClickListener(v -> {

        });
        dialog.findViewById(R.id.dismiss).setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    /**
     * 守护弹窗
     */
    private void dialogShouHu() {
        dialog.contentView(R.layout.dialog_common)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT))
                .animType(BaseDialog.AnimInType.BOTTOM)
                .canceledOnTouchOutside(true)
                .gravity(Gravity.CENTER)
                .dimAmount(0.5f)
                .show();
        ImageView imageView1 = dialog.findViewById(R.id.imageView1);
        TextView textView1 = dialog.findViewById(R.id.textView1);
        TextView textView2 = dialog.findViewById(R.id.textView2);

        dialog.findViewById(R.id.linearLayout).setOnClickListener(v -> {
            dialog.dismiss();
            CommonUtil.gotoActivity(UserDetailActivity.this, ShouHuListActivity.class, false);
        });
        dialog.findViewById(R.id.textView4).setOnClickListener(v -> {
            dialog.dismiss();
        });
    }
}
