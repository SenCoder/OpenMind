package com.tcl.openmind.ui.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by shengyuan on 16-12-19.
 */

public abstract class BaseFragment extends Fragment {

    public abstract void showProgressDialog();

    public abstract void hideProgressDialog();

    protected void loadDate() {

    }
}
