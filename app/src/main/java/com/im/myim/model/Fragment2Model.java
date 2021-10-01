package com.im.myim.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Z on 2021/10/1.
 */
public class Fragment2Model implements Serializable {
    private String mContent;
    private List<String> mImgUrlList;

    public Fragment2Model(String mContent, List<String> mImgUrlList) {
        this.mContent = mContent;
        this.mImgUrlList = mImgUrlList;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public List<String> getmImgUrlList() {
        return mImgUrlList;
    }

    public void setmImgUrlList(List<String> mImgUrlList) {
        this.mImgUrlList = mImgUrlList;
    }
}
