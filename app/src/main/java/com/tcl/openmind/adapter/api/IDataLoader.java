package com.tcl.openmind.adapter.api;

/**
 * Created by shengyuan on 16-12-19.
 */

public interface IDataLoader {

    int TYPE_LOADING_MORE = -1;
    int NOMAL_ITEM = 1;
    
    void clearData();

    void loadingStart();

    void loadingEnd();
}
