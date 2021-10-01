package com.im.myim.model;

import java.io.Serializable;

/**
 * Created by Mr.Z on 2021/10/1.
 */
public class CommonModel implements Serializable {
    private String name;
    private Integer img;

    public CommonModel(String name, Integer img) {
        this.name = name;

        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }
}
