package com.study.eric.studyproject.bean;

import android.view.View;

import java.io.Serializable;

/**
 * 基础TabHost的数据项类
 */
public class TabState implements Serializable {
    private Class clazz;
    private int imgNormal;
    private int imgSelected;
    private int content;
    private Serializable bundleData;

    public TabState(Class clazz, int imgSelected, int imgNormal, int content) {
        this.clazz = clazz;
        this.content = content;
        this.imgNormal = imgNormal;
        this.imgSelected = imgSelected;
    }

    public TabState(Class clazz, int imgSelected, int imgNormal, int content,
                    Serializable bundleData) {
        this(clazz, imgSelected, imgNormal, content);
        this.bundleData = bundleData;
    }

    public int getImgSelected() {
        return imgSelected;
    }

    public int getImgNormal() {
        return imgNormal;
    }

    public Class getClazz() {
        return clazz;
    }

    public Serializable getBundleData() {
        return bundleData;
    }

    public int getContent() {
        return content;
    }
}
