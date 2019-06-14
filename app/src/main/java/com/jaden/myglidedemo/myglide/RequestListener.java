package com.jaden.myglidedemo.myglide;

import android.graphics.Bitmap;

public interface RequestListener {
    boolean onSuccess(Bitmap bitmap);
    boolean onFailure();
}
