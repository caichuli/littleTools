package com.cjq.littletools.entity;

import java.util.UUID;

public class SingleTool {
    private String mName;
    private int mImage = -1;
    private String mFragment;
    private boolean isEnabled = true;


    public SingleTool(String name) {
        mName = name;
    }

    public SingleTool(String name, int image, String fragment, boolean isEnabled) {
        mName = name;
        mImage = image;
        mFragment = fragment;
        this.isEnabled = isEnabled;
    }

    public SingleTool(String name, int image, String fragment) {
        mName = name;
        mImage = image;
        mFragment = fragment;
    }

    public SingleTool(String name, String fragment) {
        mName = name;
        mFragment = fragment;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getFragment() {
        return mFragment;
    }

    public void setFragment(String fragment) {
        mFragment = fragment;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

}
