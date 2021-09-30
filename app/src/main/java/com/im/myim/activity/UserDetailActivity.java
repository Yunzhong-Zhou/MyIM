package com.im.myim.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cy.dialog.BaseDialog;
import com.im.myim.R;
import com.im.myim.base.BaseActivity;
import com.im.myim.utils.CommonUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Mr.Z on 2021/9/30.
 */
public class UserDetailActivity extends BaseActivity {
    List<String> list_more = new ArrayList<>();
    List<String> list_jubao = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar.reset().init();
        setContentView(R.layout.activity_userdetail);
        findViewByID_My(R.id.headView).setPadding(0, (int) CommonUtil.getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initView() {

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
        dialog.findViewById(R.id.textView1).setOnClickListener(v->{

        });
        dialog.findViewById(R.id.textView2).setOnClickListener(v->{

        });
        dialog.findViewById(R.id.textView3).setOnClickListener(v->{

        });
        dialog.findViewById(R.id.textView4).setOnClickListener(v->{

        });
        dialog.findViewById(R.id.textView5).setOnClickListener(v->{

        });
        dialog.findViewById(R.id.dismiss).setOnClickListener(v->{
            dialog.dismiss();
        });
    }
}
