package com.im.myim.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cy.dialog.BaseDialog;
import com.im.myim.R;
import com.im.myim.activity.NewsDetailActivity;
import com.im.myim.activity.UserDetailActivity;
import com.im.myim.model.Fragment2Model;
import com.im.myim.popupwindow.PhotoShowDialog;
import com.im.myim.utils.BuySvipDialog;
import com.im.myim.utils.CommonUtil;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 动态
 */
public class Fragment2Adapter extends RecyclerView.Adapter<Fragment2Adapter.Fragmnet2ViewHolder> {
    private LayoutInflater mInflater;
    private List<Fragment2Model> mPostList;
    Activity mContext;
    BaseDialog mDialog;
    public Fragment2Adapter(Activity context, BaseDialog dialog, List<Fragment2Model> postList) {
        super();
        mContext = context;
        mDialog = dialog;
        mPostList = postList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(Fragmnet2ViewHolder holder, int position) {
        holder.bind(mPostList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    @Override
    public Fragmnet2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Fragmnet2ViewHolder(mInflater.inflate(R.layout.item_fragment2, parent, false));
    }

    public class Fragmnet2ViewHolder extends RecyclerView.ViewHolder {
        private NineGridImageView<String> mNglimg;
        private LinearLayout ll,ll_sex;
        private TextView tv_content,tv_name,tv_sex,tv_vip,tv_info,tv_dianzan,tv_pinglun,tv_zhuanfa,
                tv_xindong;
        private ImageView imageView1,iv_sex;

        private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String s) {
                Glide.with(context)
                        .load(s)
                        .centerCrop()
//                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                        .placeholder(R.mipmap.loading)//加载站位图
                        .error(R.mipmap.zanwutupian)//加载失败
                        .into(imageView);//加载图片
            }

            @Override
            protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
                PhotoShowDialog photoShowDialog = new PhotoShowDialog(context, list, index);
                photoShowDialog.show();
            }
        };

        public Fragmnet2ViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            mNglimg = (NineGridImageView<String>) itemView.findViewById(R.id.mNglimg);
            mNglimg.setAdapter(mAdapter);

            ll = (LinearLayout) itemView.findViewById(R.id.ll);
            ll_sex = (LinearLayout) itemView.findViewById(R.id.ll_sex);

            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_sex = (TextView) itemView.findViewById(R.id.tv_sex);
            tv_vip = (TextView) itemView.findViewById(R.id.tv_vip);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);
            tv_dianzan = (TextView) itemView.findViewById(R.id.tv_dianzan);
            tv_pinglun = (TextView) itemView.findViewById(R.id.tv_pinglun);
            tv_zhuanfa = (TextView) itemView.findViewById(R.id.tv_zhuanfa);
            tv_xindong = (TextView) itemView.findViewById(R.id.tv_xindong);


            imageView1 = (ImageView) itemView.findViewById(R.id.imageView1);

            iv_sex = (ImageView) itemView.findViewById(R.id.iv_sex);





            ll.setOnClickListener(v->{
                //动态详情
                CommonUtil.gotoActivity(mContext, NewsDetailActivity.class);
            });
            imageView1.setOnClickListener(v->{
                //用户详情
                CommonUtil.gotoActivity(mContext, UserDetailActivity.class);
            });
            tv_xindong.setOnClickListener(v->{
                //购买SVIP弹窗
                new BuySvipDialog(mContext, mDialog, true) {
                    @Override
                    public void onCallBack() {

                    }
                };
            });
        }

        public void bind(Fragment2Model model) {
            mNglimg.setImagesData(model.getmImgUrlList());
            tv_content.setText(model.getmContent());


        }
    }
}
