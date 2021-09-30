package com.im.myim.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.im.myim.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Mr.Z on 2021/9/29.
 */
public class WaterfallAdapter extends RecyclerView.Adapter<WaterfallAdapter.BaseViewHolder> {
    private ArrayList<String> dataList = new ArrayList<>();
    Activity mContext;
    public void replaceAll(Activity context,ArrayList<String> list) {
        mContext = context;
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }
    @Override
    public WaterfallAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image, parent, false));
    }
    @Override
    public void onBindViewHolder(WaterfallAdapter.BaseViewHolder holder, int position) {
//        holder.setData(dataList.get(position));
        String data = dataList.get(position % dataList.size());
        holder.setData(data);
    }
    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
//        return dataList != null ? dataList.size() : 0;
    }
    public class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }
        void setData(Object data) {
        }
    }
    private class OneViewHolder extends BaseViewHolder {
        private ImageView ivImage;
        public OneViewHolder(View view) {
            super(view);
            ivImage = (ImageView) view.findViewById(R.id.ivImage);
            int width = ((Activity) ivImage.getContext()).getWindowManager().getDefaultDisplay().getWidth();
            ViewGroup.LayoutParams params = ivImage.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            params.width = width/2;
            params.height =  (int) (400 + Math.random() * 600) ;
            ivImage.setLayoutParams(params);
        }
        @Override
        void setData(Object data) {
            if (data != null) {
                String text = (String) data;
                if (!mContext.isFinishing()) {
                    Glide.with(mContext).load(text)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.mipmap.zanwutupian)
                            .into(ivImage);
                }


                /*//随机图片对应R文件的int值：mArray[index]，实例Drawable类
                Drawable drawable =getContext().getResources().getDrawable(mArray[index]);
                //设置图片
                ivBg.setImageDrawable(drawable);*/

            }
        }
    }
}
