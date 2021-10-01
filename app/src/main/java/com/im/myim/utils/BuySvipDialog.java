package com.im.myim.utils;

import android.app.Activity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cy.dialog.BaseDialog;
import com.im.myim.R;
import com.im.myim.activity.SVIPActivity;
import com.im.myim.activity.WebContentActivity;
import com.im.myim.net.URLs;

import androidx.annotation.NonNull;

/**
 * Created by Mr.Z on 2021/7/29.
 * 心动弹窗
 */
public abstract class BuySvipDialog {
    /**
     *
     * @param activity 用于判断是否销毁，不能用Content
     * @param dialog  不用再次初始化
     * @param isShow  是否显示次数已完
     */
    public BuySvipDialog(Activity activity, BaseDialog dialog, Boolean isShow) {
        if (!activity.isFinishing()) {
            if (dialog.isShowing() == false) {
                dialog.contentView(R.layout.dialog_buysvip)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .gravity(Gravity.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                if (isShow) {
                    dialog.findViewById(R.id.textView1).setVisibility(View.VISIBLE);
                    dialog.findViewById(R.id.tv_more).setVisibility(View.VISIBLE);
                } else {
                    dialog.findViewById(R.id.textView1).setVisibility(View.GONE);
                    dialog.findViewById(R.id.tv_more).setVisibility(View.GONE);
                }

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

                showTxtHint(activity, textView, "3天免费试用，之后12元/月，您可以在设置中取消订阅,否则自动续订，确认即代表您同意%1$s");
                dialog.findViewById(R.id.rl1).setOnClickListener(v -> {
                    //免费
                    ll1.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_fense_4);
                    ll2.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_huise_4);
                    ll3.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_huise_4);

                    tv_time1.setTextColor(activity.getResources().getColor(R.color.pink));
                    tv_time2.setTextColor(activity.getResources().getColor(R.color.grey));
                    tv_time3.setTextColor(activity.getResources().getColor(R.color.grey));

                    tv_price1.setTextColor(activity.getResources().getColor(R.color.pink));
                    tv_price2.setTextColor(activity.getResources().getColor(R.color.grey));
                    tv_price3.setTextColor(activity.getResources().getColor(R.color.grey));

                    tv_money1.setTextColor(activity.getResources().getColor(R.color.white));
                    tv_money2.setTextColor(activity.getResources().getColor(R.color.grey));
                    tv_money3.setTextColor(activity.getResources().getColor(R.color.grey));

                    tv_money1.setBackgroundResource(R.drawable.yuanjiao_10_fense_bottom);
                    tv_money2.setBackgroundResource(R.color.transparent);
                    tv_money3.setBackgroundResource(R.color.transparent);

                    tv_shiyong.setVisibility(View.VISIBLE);

                    showTxtHint(activity, textView, "3天免费试用，之后12元/月，您可以在设置中取消订阅,否则自动续订，确认即代表您同意%1$s");

                });
                dialog.findViewById(R.id.rl2).setOnClickListener(v -> {
                    //1个月
                    ll1.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_huise_4);
                    ll2.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_fense_4);
                    ll3.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_huise_4);

                    tv_time1.setTextColor(activity.getResources().getColor(R.color.grey));
                    tv_time2.setTextColor(activity.getResources().getColor(R.color.pink));
                    tv_time3.setTextColor(activity.getResources().getColor(R.color.grey));

                    tv_price1.setTextColor(activity.getResources().getColor(R.color.grey));
                    tv_price2.setTextColor(activity.getResources().getColor(R.color.pink));
                    tv_price3.setTextColor(activity.getResources().getColor(R.color.grey));

                    tv_money1.setTextColor(activity.getResources().getColor(R.color.grey));
                    tv_money2.setTextColor(activity.getResources().getColor(R.color.white));
                    tv_money3.setTextColor(activity.getResources().getColor(R.color.grey));

                    tv_money1.setBackgroundResource(R.color.transparent);
                    tv_money2.setBackgroundResource(R.drawable.yuanjiao_10_fense_bottom);
                    tv_money3.setBackgroundResource(R.color.transparent);

                    tv_shiyong.setVisibility(View.INVISIBLE);
                    showTxtHint(activity, textView, "1个月内使用，之后12元/月，您可以在设置中取消订阅,否则自动续订，确认即代表您同意%1$s");
                });
                dialog.findViewById(R.id.rl3).setOnClickListener(v -> {
                    //3个月
                    ll1.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_huise_4);
                    ll2.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_huise_4);
                    ll3.setBackgroundResource(R.drawable.yuanjiaobiankuang_10_fense_4);

                    tv_time1.setTextColor(activity.getResources().getColor(R.color.grey));
                    tv_time2.setTextColor(activity.getResources().getColor(R.color.grey));
                    tv_time3.setTextColor(activity.getResources().getColor(R.color.pink));

                    tv_price1.setTextColor(activity.getResources().getColor(R.color.grey));
                    tv_price2.setTextColor(activity.getResources().getColor(R.color.grey));
                    tv_price3.setTextColor(activity.getResources().getColor(R.color.pink));

                    tv_money1.setTextColor(activity.getResources().getColor(R.color.grey));
                    tv_money2.setTextColor(activity.getResources().getColor(R.color.grey));
                    tv_money3.setTextColor(activity.getResources().getColor(R.color.white));

                    tv_money1.setBackgroundResource(R.color.transparent);
                    tv_money2.setBackgroundResource(R.color.transparent);
                    tv_money3.setBackgroundResource(R.drawable.yuanjiao_10_fense_bottom);

                    tv_shiyong.setVisibility(View.INVISIBLE);
                    showTxtHint(activity, textView, "3个月内使用，之后12元/月，您可以在设置中取消订阅,否则自动续订，确认即代表您同意%1$s");
                });

                dialog.findViewById(R.id.tv_more).setOnClickListener(v -> {
                    //跳转SVIP页面
                    CommonUtil.gotoActivity(activity, SVIPActivity.class, false);
                });
                dialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
                    dialog.dismiss();
                    //支付宝支付
                });

                dialog.findViewById(R.id.dismiss).setOnClickListener(v -> {
                    dialog.dismiss();
                });
            }
        }
    }

    private void showTxtHint(Activity activity, TextView textView, String s) {
        //文本提示
        String text = String.format(s, "《自动续费协议》");
        int index[] = new int[1];
        index[0] = text.indexOf("《自动续费协议》");

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ClickableSpan() {
                          @Override
                          public void onClick(@NonNull View widget) {
                              Bundle bundle = new Bundle();
                              bundle.putString("url", URLs.HOST + "/single/h5/register?user_hash=" + LocalUserInfo.getInstance(activity).getUserHash());
                              CommonUtil.gotoActivityWithData(activity, WebContentActivity.class, bundle, false);
                          }

                          @Override
                          public void updateDrawState(@NonNull TextPaint ds) {
                              super.updateDrawState(ds);
                              ds.setColor(activity.getResources().getColor(R.color.blue));
//                              new ForegroundColorSpan(getResources().getColor(R.color.green));
                          }
                      },
                index[0],
                index[0] + "《自动续费协议》".length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(style);
    }

    public abstract void onCallBack();
}
