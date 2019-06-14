package com.jaden.myglidedemo.myglide;

import android.content.Context;

public class MyGlide {

    public static BitmapRequest with(Context context) {
        return new BitmapRequest(context);
    }
}
