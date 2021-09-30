package com.im.myim.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.im.myim.R;
import com.im.myim.adapter.WaterfallAdapter;
import com.im.myim.base.BaseActivity;
import com.im.myim.model.Fragment4Model;
import com.im.myim.net.URLs;
import com.im.myim.okhttp.CallBackUtil;
import com.im.myim.okhttp.OkhttpUtil;
import com.im.myim.utils.CommonUtil;
import com.im.myim.utils.MyLogger;
import com.im.myim.view.AutoPollRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zyz on 2020/6/3.
 * 绑定手机号
 */
public class BindingPhoneActivity extends BaseActivity {
    String user_phone="";

    EditText editText1,editText2;
    TextView tv_confirm;

    private AutoPollRecyclerView recyclerView;
    ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bindingphone);
        /*mImmersionBar.reset()
                .statusBarColor(R.color.background)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();*/
        mImmersionBar.reset().init();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initView() {
        editText1 = findViewByID_My(R.id.editText1);
        editText1.setText(localUserInfo.getPhonenumber());
        editText2 = findViewByID_My(R.id.editText2);
        tv_confirm = findViewByID_My(R.id.tv_confirm);

        recyclerView = findViewByID_My(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,  StaggeredGridLayoutManager.VERTICAL));

        setData();
        WaterfallAdapter adapter= new WaterfallAdapter();
        recyclerView.setAdapter(adapter);
        adapter.replaceAll(BindingPhoneActivity.this,list);
        recyclerView.start();

        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                // true: consume touch event
                // false: dispatch touch event
                return true;
            }
        });
    }

    @Override
    protected void initData() {
    }
    private void setData(){
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
        while(mylist.size() < imageUrls.length) {
            int num = rd.nextInt(imageUrls.length);
            if (!mylist.contains(num)) {
                mylist.add(num); //往集合里面添加数据。
            }
        }
        for (int i = 0; i < imageUrls.length; i++) {
            MyLogger.i(">>>>>>"+mylist.get(i));
            list.add(imageUrls[mylist.get(i)]);
        }

        /*for (String url : imageUrls) {
            list.add(url);
        }*/
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

            case R.id.tv_confirm:
                //提交
                /*if (match()) {
                    tv_confirm.setClickable(false);
                    showProgress(true, "正在绑定手机号，请稍候...");
                    params.put("u_token", localUserInfo.getToken());
                    params.put("user_phone", user_phone);
                    RequestChage(params);//修改
                }*/

                ActivityUtils.finishAllActivitiesExceptNewest();//结束除最新之外的所有 Activity
                CommonUtil.gotoActivity(BindingPhoneActivity.this, MainActivity.class, true);
                break;
        }

    }
    private boolean match() {
        user_phone = editText1.getText().toString().trim();
        if (TextUtils.isEmpty(user_phone)) {
            myToast("请输入手机号");
            return false;
        }
        return true;
    }
    /**
     * 修改信息
     *
     * @param params
     */
    private void RequestChage(Map<String, String> params) {
        OkhttpUtil.okHttpPost(URLs.BindingPhone, params, headerMap, new CallBackUtil<String>() {
            @Override
            public String onParseResponse(Call call, Response response) {
                return null;
            }

            @Override
            public void onFailure(Call call, Exception e, String err) {
                hideProgress();
                tv_confirm.setClickable(true);
                myToast(err);
            }

            @Override
            public void onResponse(String response) {
                hideProgress();
                tv_confirm.setClickable(true);

                showProgress(true, "正在获取个人信息，请稍候...");
                Map<String, String> params = new HashMap<>();
                params.put("u_token", localUserInfo.getToken());
                requestCenter(params);

            }
        });
    }
    private void requestCenter(Map<String, String> params) {
        OkhttpUtil.okHttpPost(URLs.Fragment4, params, headerMap, new CallBackUtil<Fragment4Model>() {
            @Override
            public Fragment4Model onParseResponse(Call call, Response response) {
                return null;
            }

            @Override
            public void onFailure(Call call, Exception e, String err) {
                hideProgress();
                myToast(err);
            }

            @Override
            public void onResponse(Fragment4Model response) {

                localUserInfo.setUserHash(response.getUser_info().getUserHash());
                //保存userid
                localUserInfo.setUserId(response.getUser_info().getUserId());
                //保存电话号码
                localUserInfo.setPhoneNumber(response.getUser_info().getUserPhone());
                //保存昵称
                localUserInfo.setNickname(response.getUser_info().getUserName());
                //保存头像
                localUserInfo.setUserImage(response.getUser_info().getHeadPortrait());
                //保存y_store_id
                localUserInfo.setBelongid(response.getUser_info().getYStoreId());
                //保存职位
                localUserInfo.setUserJob(response.getUser_info().getIsDistri()+"");//为1、有分配权限

                hideProgress();
                CommonUtil.gotoActivityWithFinishOtherAll(BindingPhoneActivity.this, MainActivity.class, true);
            }
        });
    }


    @Override
    protected void updateView() {
        titleView.setVisibility(View.GONE);
    }
}
