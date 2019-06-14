package com.jaden.myglidedemo.myglide;

import android.content.Context;
import android.widget.ImageView;

import com.jaden.myglidedemo.utils.MD5Util;

import java.lang.ref.SoftReference;

public class BitmapRequest {

    private String url;

    private int resID;

    private SoftReference<ImageView> imageView;

    private RequestListener requestListener;

    private String urlMd5;

    private Context context;

    public BitmapRequest(Context context) {
        this.context = context;
    }

    public BitmapRequest load(String url) {
        this.url = url;
        this.urlMd5 = MD5Util.md5Encrypt32Lower(url);
        return this;
    }

    public BitmapRequest loading(int resId) {
        this.resID = resId;
        return this;
    }

    public void into(ImageView imageView) {
        imageView.setTag(this.urlMd5);
        this.imageView = new SoftReference<>(imageView);
        RequestManger.get().addBitmapRequest(this);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public void setImageView(SoftReference<ImageView> imageView) {
        this.imageView = imageView;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }

    public void setRequestListener(RequestListener requestListener) {
        this.requestListener = requestListener;
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public void setUrlMd5(String urlMd5) {
        this.urlMd5 = urlMd5;
    }
}
