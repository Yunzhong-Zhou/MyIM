package com.im.myim.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cy.dialog.BaseDialog;
import com.im.myim.R;
import com.im.myim.adapter.WaterfallAdapter;
import com.im.myim.base.BaseActivity;
import com.im.myim.model.CodeModel;
import com.im.myim.model.LoginModel;
import com.im.myim.model.UpgradeModel;
import com.im.myim.net.URLs;
import com.im.myim.okhttp.CallBackUtil;
import com.im.myim.okhttp.OkhttpUtil;
import com.im.myim.utils.CommonUtil;
import com.im.myim.utils.MyLogger;
import com.im.myim.view.AutoPollRecyclerView;
import com.maning.updatelibrary.InstallUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by fafukeji01 on 2017/4/25.
 * 登录
 */
public class LoginActivity extends BaseActivity {
    String code = "";
    private EditText editText1, editText2;
    private TextView textView1, textView2;
    private ImageView image_wechat;
    private AutoPollRecyclerView recyclerView;

    ArrayList<String> list = new ArrayList<>();

    private String phonenum = "", password = "";

    private TimeCount time;

    ImageView iv_gouxuan;
    boolean isGouXuan = false;

    //更新
    UpgradeModel model_up;

    IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mImmersionBar.reset().init();
        //        findViewById(R.id.headView).setPadding(0, (int) CommonUtil.getStatusBarHeight(this), 0, 0);
//        CommonUtil.setMargins(findViewByID_My(R.id.headView),0, (int) CommonUtil.getStatusBarHeight(this), 0, 0);

        setSwipeBackEnable(false); //主 activity 可以调用该方法，禁止滑动删除

        //注册微信api
        api = WXAPIFactory.createWXAPI(this, "wx43d4928b7bbf4d5c", false);
        api.registerApp("wx43d4928b7bbf4d5c");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (time != null)
            time.cancel();
        if(recyclerView != null){
            recyclerView.stop();
        }
    }

    @Override
    protected void initView() {
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象

        editText1 = findViewByID_My(R.id.editText1);
        editText2 = findViewByID_My(R.id.editText2);

        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);

        iv_gouxuan = findViewByID_My(R.id.iv_gouxuan);
        image_wechat = findViewByID_My(R.id.image_wechat);

        recyclerView = findViewByID_My(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,  StaggeredGridLayoutManager.VERTICAL));

        setData();
        WaterfallAdapter adapter= new WaterfallAdapter();
        recyclerView.setAdapter(adapter);
        adapter.replaceAll(LoginActivity.this,list);
        recyclerView.start();
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                // true: consume touch event
                // false: dispatch touch event
                return true;
            }
        });

        /*ArrayList<Integer> imageIds = new ArrayList<>();
        int[] ids = {R.drawable.p,R.drawable.p,R.drawable.p,R.drawable.p,R.drawable.p,R.drawable.p,R.drawable.p,R.drawable.p,R.drawable.p,R.drawable.p,R.drawable.p,R.drawable.p};
        for(int i = 0 ; i < ids.length;i++){
            imageIds.add(ids[i]);
        }
        MyLogger.i(">>>"+imageIds.size());
        CommonAdapter<ArrayList<Integer>> mAdapter = new CommonAdapter<ArrayList<Integer>>
                (LoginActivity.this, R.layout.picture_image_grid_item, Collections.singletonList(imageIds)) {
            @Override
            protected void convert(ViewHolder holder, ArrayList<Integer> model, int position) {

                ImageView imageView1 = holder.getView(R.id.ivPicture);
                imageView1.setImageResource(ids[position]);
            }
        };
        recyclerView.setAdapter(mAdapter);*/

    }

    @Override
    protected void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("type", "1");
        RequestUpgrade(params);//检查更新




        /*byte[] mBytes = null;
        String mString = "{阿达大as家阿sdf什顿附asd件好久}";
        AES mAes = new AES();
        try {
            mBytes = mString.getBytes("UTF-8");
        } catch (Exception e) {
            Log.i("qing", "MainActivity----catch");
        }
        String enString = mAes.encrypt(mBytes);
        MyLogger.i("加密后：" + enString);
        String deString = mAes.decrypt("P9ezA6lsRKVID383Rg5mwQ==");
        MyLogger.i("解密后：" + deString);*/
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
        switch (v.getId()) {
            case R.id.textView1:
                //获取验证码
                phonenum = editText1.getText().toString().trim();
                if (TextUtils.isEmpty(phonenum)) {
                    myToast("请输入手机号");
                } else {
                    showProgress(true, "正在获取短信验证码...");
                    textView1.setClickable(false);
                    HashMap<String, String> params = new HashMap<>();
                    params.put("user_phone", phonenum);
//                    params.put("type", "1");
                    RequestCode(params);//获取验证码
                }
                break;

            case R.id.textView2:
                //确认登录
                /*if (match()) {
                    textView2.setClickable(false);
                    this.showProgress(true, "正在登录，请稍候...");
                    params.put("user_phone", phonenum);
                    params.put("vcode", password);
                    params.put("t_token", "");
                    params.put("head_portrait", "");
                    params.put("action", "1");//1为验证码登陆 2为第三方登陆
                    //测试数据
//                    params.put("user_phone", "18203048656");
//                    params.put("vcode", "155119");
//                    params.put("t_token", "");
//                    params.put("action", "1");//1为验证码登陆 2为第三方登陆
//                    RequestLogin(params);//登录
                }*/
                CommonUtil.gotoActivity(LoginActivity.this, BindingInfoActivity.class, false);
                break;
            case R.id.image_wechat:
                //微信登录
                if (!api.isWXAppInstalled()) {
                    myToast("您的设备未安装微信客户端");
                } else {
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    api.sendReq(req);
                    finish();
                }
                break;
            case R.id.iv_gouxuan:
                //勾选图片
                isGouXuan = !isGouXuan;
                if (isGouXuan) {
                    iv_gouxuan.setImageResource(R.mipmap.ic_xuanzhong);
                } else {
                    iv_gouxuan.setImageResource(R.mipmap.ic_weixuan);
                }
                break;
            case R.id.tv_tiaoli:
                Bundle bundle = new Bundle();
                bundle.putString("url", URLs.HOST + "/single/h5/register?user_hash="+localUserInfo.getUserHash());
                CommonUtil.gotoActivityWithData(LoginActivity.this, WebContentActivity.class, bundle, false);
                break;
        }
    }

    //登录
    private void RequestLogin(Map<String, String> params) {
        OkhttpUtil.okHttpPost(URLs.Login, params, headerMap, new CallBackUtil<LoginModel>() {
            @Override
            public LoginModel onParseResponse(Call call, Response response) {
                return null;
            }

            @Override
            public void onFailure(Call call, Exception e, String err) {
                hideProgress();
                textView2.setClickable(true);
//                myToast("密码错误，请重新输入");
                if (!err.equals("")) {
                    myToast(err);
                }
            }

            @Override
            public void onResponse(LoginModel response) {
                textView2.setClickable(true);
                hideProgress();
                //判断一下是否为技师端
                if (response.getUser_info().getIsTechn() == 1) {
                    //保存Token
                    localUserInfo.setToken(response.getUser_info().getUToken());
                    if (response.getUser_info().getUserPhone() != null && !response.getUser_info().getUserPhone().equals("0")) {

                        localUserInfo.setUserHash(response.getUser_info().getUserHash());
                        //保存userid
                        localUserInfo.setUserId(response.getUser_info().getUserId());
                        //保存电话号码
                        localUserInfo.setPhoneNumber(response.getUser_info().getUserPhone());
                        //保存是否认证
//                localUserInfo.setIsVerified(response.getIs_certification() + "");//1 认证 2 未认证
                        //保存昵称
                        localUserInfo.setNickname(response.getUser_info().getUserName());
                        //保存头像
                        localUserInfo.setUserImage(response.getUser_info().getHeadPortrait());
                        //保存y_store_id
                        localUserInfo.setBelongid(response.getUser_info().getYStoreId());
                        //保存职位
                        localUserInfo.setUserJob(response.getUser_info().getIsDistri()+"");//为1、有分配权限

                        CommonUtil.gotoActivity(LoginActivity.this, MainActivity.class, true);
                    } else {
                        CommonUtil.gotoActivity(LoginActivity.this, BindingInfoActivity.class, false);
                    }
                } else {
                    showToast("请使用技师端账号登录");
                }
            }
        });
    }

    private void RequestCode(Map<String, String> params) {
        OkhttpUtil.okHttpPost(URLs.Code, params, headerMap, new CallBackUtil<CodeModel>() {
            @Override
            public CodeModel onParseResponse(Call call, Response response) {
                return null;
            }

            @Override
            public void onFailure(Call call, Exception e, String err) {
                hideProgress();
                textView1.setClickable(true);
                if (!err.equals("")) {
                    showToast(err);
                }
            }

            @Override
            public void onResponse(CodeModel response) {
                hideProgress();
                textView1.setClickable(true);
                time.start();//开始计时
                myToast(getString(R.string.app_sendcode_hint));
                editText2.setText(response.getV_code());
            }
        });
    }

    private boolean match() {
        phonenum = editText1.getText().toString().trim();
        if (TextUtils.isEmpty(phonenum)) {
            myToast("请输入手机号");
            return false;
        }
        password = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            myToast("请输入验证码");
            return false;
        }
        if (!isGouXuan) {
            myToast("请阅读并同意《用户协议》");
            return false;
        }

        return true;
    }

    @Override
    protected void updateView() {
        titleView.setVisibility(View.GONE);
    }

    //获取验证码倒计时
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            textView1.setText(getString(R.string.app_reacquirecode));
            textView1.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            textView1.setClickable(false);
            textView1.setText(millisUntilFinished / 1000 + getString(R.string.app_codethen));
        }
    }
    /*//屏蔽返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }*/

    private void RequestUpgrade(Map<String, String> params) {
        OkhttpUtil.okHttpPost(URLs.Upgrade, params, headerMap, new CallBackUtil<UpgradeModel>() {
            @Override
            public UpgradeModel onParseResponse(Call call, Response response) {
                return null;
            }

            @Override
            public void onFailure(Call call, Exception e, String err) {
//                hideProgress();
//                myToast(err);
            }

            @Override
            public void onResponse(UpgradeModel response) {
//                hideProgress();
                model_up = response;
                if (Integer.valueOf(CommonUtil.getVersionCode(LoginActivity.this)) < Integer.valueOf(response.getVersion_code())) {
//                    handler1.sendEmptyMessage(0);
                    showUpdateDialog();
                } else {
//                    showToast("已经是最新版，无需更新");
                }
            }
        });
    }

    //显示是否要更新的对话框
    private void showUpdateDialog() {
        dialog.contentView(R.layout.dialog_upgrade)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT))
                .animType(BaseDialog.AnimInType.CENTER)
                .canceledOnTouchOutside(true)
                .dimAmount(0.8f)
                .show();
        TextView textView1 = dialog.findViewById(R.id.textView1);
        TextView textView2 = dialog.findViewById(R.id.textView2);
        TextView textView3 = dialog.findViewById(R.id.textView3);
        TextView textView4 = dialog.findViewById(R.id.textView4);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                        /*Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(model_up.getUrl());
                        intent.setData(content_url);
                        startActivity(intent);*/
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);    //进度条，在下载的时候实时更新进度，提高用户友好度
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setCancelable(false);//点击外部不消失，返回键没用
//        progressDialog.setCanceledOnTouchOutside(false);//点击外部不消失，返回键有用
                    progressDialog.setTitle(getString(R.string.update_hint3));
                    progressDialog.setMessage(getString(R.string.update_hint4));
                    progressDialog.setProgress(0);
                    progressDialog.show();

                    //下载APK
                    InstallUtils.with(LoginActivity.this)
                            //必须-下载地址
                            .setApkUrl(model_up.getUrl())
                            //非必须-下载保存的文件的完整路径+/name.apk，使用自定义路径需要获取读写权限
//                                    .setApkPath(Constants.APK_SAVE_PATH)
                            //非必须-下载回调
                            .setCallBack(new InstallUtils.DownloadCallBack() {
                                @Override
                                public void onStart() {
                                    //下载开始
                                }

                                @Override
                                public void onComplete(final String path) {
                                    progressDialog.cancel();
                                    //下载完成
                                    //先判断有没有安装权限---适配8.0
                                    //如果不想用封装好的，可以自己去实现8.0适配
                                    InstallUtils.checkInstallPermission(LoginActivity.this, new InstallUtils.InstallPermissionCallBack() {
                                        @Override
                                        public void onGranted() {
                                            //去安装APK
                                            //一加手机8.0碰到了安装解析失败问题请添加下面判断
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                //先获取是否有安装未知来源应用的权限
                                                boolean haveInstallPermission = LoginActivity.this.getPackageManager().canRequestPackageInstalls();
                                                if (!haveInstallPermission) {
                                                    //跳转设置开启允许安装
                                                    Uri packageURI = Uri.parse("package:" + LoginActivity.this.getPackageName());
                                                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                                                    startActivityForResult(intent, 1000);
                                                    return;
                                                }
                                            }
                                            InstallUtils.installAPK(LoginActivity.this, path, new InstallUtils.InstallCallBack() {
                                                @Override
                                                public void onSuccess() {
                                                    myToast(getString(R.string.update_hint5));
                                                }

                                                @Override
                                                public void onFail(Exception e) {
                                                    myToast(getString(R.string.update_hint6) + e.toString());
                                                }
                                            });
                                        }

                                        @Override
                                        public void onDenied() {
                                            //弹出弹框提醒用户
                                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                                                    .setTitle(getString(R.string.update_hint7))
                                                    .setMessage(getString(R.string.update_hint8))
                                                    .setNegativeButton(getString(R.string.app_cancel), null)
                                                    .setPositiveButton(getString(R.string.update_hint9), new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            //打开设置页面
                                                            InstallUtils.openInstallPermissionSetting(LoginActivity.this, new InstallUtils.InstallPermissionCallBack() {
                                                                @Override
                                                                public void onGranted() {
                                                                    //去安装APK
                                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                        //先获取是否有安装未知来源应用的权限
                                                                        boolean haveInstallPermission = LoginActivity.this.getPackageManager().canRequestPackageInstalls();
                                                                        if (!haveInstallPermission) {
                                                                            //跳转设置开启允许安装
                                                                            Uri packageURI = Uri.parse("package:" + LoginActivity.this.getPackageName());
                                                                            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                                                                            startActivityForResult(intent, 1000);
                                                                            return;
                                                                        }
                                                                    }
                                                                    InstallUtils.installAPK(LoginActivity.this, path, new InstallUtils.InstallCallBack() {
                                                                        @Override
                                                                        public void onSuccess() {
                                                                            myToast(getString(R.string.update_hint5));
                                                                        }

                                                                        @Override
                                                                        public void onFail(Exception e) {
                                                                            myToast(getString(R.string.update_hint6) + e.toString());
                                                                        }
                                                                    });
                                                                }

                                                                @Override
                                                                public void onDenied() {
                                                                    //还是不允许咋搞？
                                                                    finish();
//                                                                            Toast.makeText(MainActivity.this, "不允许安装咋搞？强制更新就退出应用程序吧！", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    })
                                                    .create();
                                            alertDialog.show();
                                        }
                                    });

                                }

                                @Override
                                public void onLoading(long total, long current) {
                                    //下载中
                                    progressDialog.setMax((int) total);
                                    progressDialog.setProgress((int) current);
                                }

                                @Override
                                public void onFail(Exception e) {
                                    //下载失败
                                }

                                @Override
                                public void cancle() {
                                    //下载取消
                                }
                            })
                            //开始下载
                            .startDownload();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.update_hint1),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        code = getIntent().getStringExtra("code");
        MyLogger.i(">>>>>>>" + code);
        if (code != null && !code.equals("")) {

            this.showProgress(true, "正在获取微信登录参数，请稍候...");
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token"
                    + "?appid=" + "wx43d4928b7bbf4d5c"
                    + "&secret=" + "40629341d0e10ab69fbfe4a81c9d8a06"
                    + "&code=" + code
                    + "&grant_type=authorization_code";
            requestWeChat1(url);

            getIntent().removeExtra("code");
           /* getIntent().removeExtra("code");
            this.showProgress(true, "正在登录，请稍候...");
            Map<String, String> params = new HashMap<>();
            params.put("user_phone", phonenum);
            params.put("vcode", password);
            params.put("t_token", code);
            params.put("action", "2");//1为验证码登陆 2为第三方登陆
            RequestWeChatLogin(params);//微信登录*/
        }
    }

    //获取微信数据1
    private void requestWeChat1(String string) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(string)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseInfo = response.body().string();
                MyLogger.i(">>>>>>>>>微信数据1:\n" + responseInfo);
                String access_token = "";
                String openId = "";
                String refresh_token = "";
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo);
                    refresh_token = jsonObject.getString("refresh_token");
                    access_token = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");

                    String url = "https://api.weixin.qq.com/sns/userinfo"
                            + "?access_token=" + access_token
                            + "&openid=" + openId;
                    requestWeChat2(url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                hideProgress();
            }
        });

    }

    //获取微信数据2
    private void requestWeChat2(String string) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(string)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseInfo = response.body().string();
                MyLogger.i(">>>>>>>>>微信数据2:\n" + responseInfo);
				/*
				{
  "openid": "OPENID",
  "nickname": "NICKNAME",
  "sex": 1,
  "province": "PROVINCE",
  "city": "CITY",
  "country": "COUNTRY",
  "headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
  "privilege": ["PRIVILEGE1", "PRIVILEGE2"],
  "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
}
				* */

                String openid = "";
                String nickname = "";
                String sex = "";
                String city = "";
                String headimgurl = "";
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo);
                    openid = jsonObject.getString("openid");
                    nickname = jsonObject.getString("nickname");
                    headimgurl = jsonObject.getString("headimgurl");

//                    hideProgress();
//                    showProgress(true, "正在登录，请稍候...");
                    Map<String, String> params = new HashMap<>();
                    params.put("user_phone", phonenum);
                    params.put("vcode", password);
                    params.put("t_token", openid);
                    params.put("head_portrait", headimgurl);
                    params.put("action", "2");//1为验证码登陆 2为第三方登陆
                    RequestLogin(params);//微信登录

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                hideProgress();
            }
        });

    }
}
