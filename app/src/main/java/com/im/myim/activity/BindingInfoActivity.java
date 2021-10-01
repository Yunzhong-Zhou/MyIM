package com.im.myim.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.im.myim.R;
import com.im.myim.adapter.WaterfallAdapter;
import com.im.myim.base.BaseActivity;
import com.im.myim.model.Fragment4Model;
import com.im.myim.model.UpFileModel;
import com.im.myim.net.URLs;
import com.im.myim.okhttp.CallBackUtil;
import com.im.myim.okhttp.OkhttpUtil;
import com.im.myim.utils.CommonUtil;
import com.im.myim.utils.FileUtil;
import com.im.myim.utils.MyChooseImages;
import com.im.myim.utils.MyLogger;
import com.im.myim.view.AutoPollRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import okhttp3.Call;
import okhttp3.Response;

import static com.im.myim.utils.MyChooseImages.REQUEST_CODE_CAPTURE_CAMEIA;
import static com.im.myim.utils.MyChooseImages.REQUEST_CODE_PICK_IMAGE;

/**
 * Created by zyz on 2020/6/3.
 * 绑定信息
 */
public class BindingInfoActivity extends BaseActivity {
    String user_phone="";

    ArrayList<File> listFiles = new ArrayList<>();
    ImageView imageView1;
    EditText editText1,editText2,editText3;
    TextView tv_nan,tv_nv,tv_confirm;

    TimePickerView pvTime1;

    private AutoPollRecyclerView recyclerView;
    ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bindinginfo);
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
        imageView1 = findViewByID_My(R.id.imageView1);
        editText1 = findViewByID_My(R.id.editText1);
        editText2 = findViewByID_My(R.id.editText2);
        editText3 = findViewByID_My(R.id.editText3);
        tv_nan = findViewByID_My(R.id.tv_nan);
        tv_nv = findViewByID_My(R.id.tv_nv);
        tv_confirm = findViewByID_My(R.id.tv_confirm);

        recyclerView = findViewByID_My(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,  StaggeredGridLayoutManager.VERTICAL));

        setData();
        WaterfallAdapter adapter= new WaterfallAdapter();
        recyclerView.setAdapter(adapter);
        adapter.replaceAll(BindingInfoActivity.this,list);
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
            case R.id.imageView1:
                //头像
                MyChooseImages.showPhotoDialog(BindingInfoActivity.this);
                break;
            case R.id.tv_nan:
                //男
                tv_nan.setBackgroundResource(R.drawable.yuanjiao_18_fense);
                tv_nv.setBackgroundResource(R.drawable.yuanjiaobiankuang_18_baise);
                break;
            case R.id.tv_nv:
                //女
                tv_nan.setBackgroundResource(R.drawable.yuanjiaobiankuang_18_baise);
                tv_nv.setBackgroundResource(R.drawable.yuanjiao_18_fense);
                break;
            case R.id.editText2:
                //选择日期
                setDate("请选择出生日期", editText2, editText2.getText().toString().trim(), false);
                break;
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
                CommonUtil.gotoActivity(BindingInfoActivity.this, MainActivity.class, true);
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
    //预约时间
    private void setDate(String string, TextView textView, String date, boolean isNongLi) {
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH);
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //分钟
        int minute = calendar.get(Calendar.MINUTE);
        //秒
        int second = calendar.get(Calendar.SECOND);

        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        if (!date.equals("")) {
            try {
                String[] strArr = date.split("-");//拆分日期 得到年月日
                selectedDate.set(Integer.valueOf(strArr[0]), Integer.valueOf(strArr[1]) - 1, Integer.valueOf(strArr[2]));
            } catch (IllegalStateException e) {
                // Only fullscreen activities can request orientation
                e.printStackTrace();
            }

        }

        //正确设置方式 原因：注意事项有说明
//        startDate.set(year, month, day);
        startDate.set(1900, 1, 1);

        //当前时间加3天
//        calendar.add(Calendar.YEAR, 100);
        endDate.set(year, month, day);
        /*endDate.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));*/


        pvTime1 = new TimePickerBuilder(BindingInfoActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                textView.setText(CommonUtil.date2ymd(date));
                textView.setText( TimeUtils.date2String(date,"yyyy-MM-dd"));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(15)//滚轮文字大小
                .setTitleSize(16)//标题文字大小
                .setTitleText(string)//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setLunarCalendar(isNongLi)//农历开关
                .setTitleColor(getResources().getColor(R.color.black2))//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.pink))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.pink))//取消按钮文字颜色
                .setTitleBgColor(getResources().getColor(R.color.black5))//标题背景颜色 Night mode
                .setBgColor(getResources().getColor(R.color.white))//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();

        Dialog mDialog = pvTime1.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime1.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }
        pvTime1.show();
    }
    /**
     * 修改信息
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
                CommonUtil.gotoActivityWithFinishOtherAll(BindingInfoActivity.this, MainActivity.class, true);
            }
        });
    }


    @Override
    protected void updateView() {
        titleView.setVisibility(View.GONE);
    }

    /**
     * *****************************************选择图片********************************************
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            File imgfile = null;
            String imgpath = null;
            Uri uri = null;
            switch (requestCode) {
                case REQUEST_CODE_CAPTURE_CAMEIA:
                    //相机
                    uri = Uri.parse("");
                    uri = Uri.fromFile(new File(MyChooseImages.imagepath));
                    imgpath = uri.getPath();
                    MyLogger.i(">>>>>>>>>选取的文件路径：" + imgpath + ">>>>>后缀名：" + FileUtils.getFileExtension(imgpath));
                    break;
                case REQUEST_CODE_PICK_IMAGE:
                    //相册
                    uri = data.getData();
                    imgpath = FileUtil.getPath(this, uri);
                    MyLogger.i(">>>>>>>>>选取的文件路径：" + imgpath + ">>>>>后缀名：" + FileUtils.getFileExtension(imgpath));
                    break;

            }
            if (imgpath != null) {

                //压缩
                Bitmap bitmap = BitmapFactory.decodeFile(imgpath);
                //如果是拍照，则旋转
                if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
                    bitmap = FileUtil.rotaingImageView(ImageUtils.getRotateDegree(imgpath), bitmap);
                }
                imgfile = FileUtil.bytesToImageFile(BindingInfoActivity.this,
                        ImageUtils.compressByQuality(bitmap, 50));

                imageView1.setImageBitmap(bitmap);

                //上传文件
                /*showProgress(true, getString(R.string.app_loading1));
                listFiles.clear();
                listFiles.add(imgfile);
                params.clear();
                params.put("sn", "773EDB6D2715FACF9C93354CAC5B1A3372872DC4D5AC085867C7490E9984D33E");
                RequestUpFile(params, listFiles, "picture");*/
            }
        }

    }

    /**
     * 上传文件 list 方式
     *
     * @param params
     * @param fileList
     * @param fileKey
     */
    private void RequestUpFile(Map<String, String> params, List<File> fileList, String fileKey) {
        OkhttpUtil.okHttpUploadListFile(URLs.UpFile, params, fileList, fileKey, "image", headerMap, new CallBackUtil<UpFileModel>() {
            @Override
            public UpFileModel onParseResponse(Call call, Response response) {
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
            public void onResponse(UpFileModel response) {
//                myToast("头像修改成功");
                /*for (String s : response.getList()) {
                    head_portrait = s;
                }*/
            }
        });
    }
}
