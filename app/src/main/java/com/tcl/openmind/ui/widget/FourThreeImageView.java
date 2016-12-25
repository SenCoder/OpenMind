package com.tcl.openmind.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by yuansheng on 24/12/2016.
 */

public class FourThreeImageView extends ImageView {

    public FourThreeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FourThreeImageView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int customHeight = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) * 3/4 , MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, customHeight);
    }
}
