package com.study.eric.studyproject.event;

/**
 * Created by Administrator on 2017/2/3.
 */

public class MainTabEvent {
    private boolean tag;
    public boolean getTag(){
        return tag;
    }
    public MainTabEvent(boolean tag) {
        this.tag = tag;
    }
}
