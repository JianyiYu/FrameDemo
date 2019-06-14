package com.jaden.myglidedemo.myglide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

public class BitmapDispatcher extends Thread {

    private Handler handler = new Handler(Looper.getMainLooper());

   private LinkedBlockingQueue<BitmapRequest> requestQueue;

   public BitmapDispatcher(LinkedBlockingQueue requestQueue) {
       this.requestQueue = requestQueue;
   }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            try {
                //
                BitmapRequest br = requestQueue.take();
                //设置占位图片
                showLoadingImage(br);
                Bitmap bitmap = findBitmap(br);
                showImageView(br, bitmap);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageView(final BitmapRequest br, final Bitmap bitmap) {
       if (bitmap != null && br.getImageView() != null
               && br.getUrlMd5().equals(br.getImageView().getTag())) {
           final ImageView imageView = br.getImageView();
           handler.post(new Runnable() {
               @Override
               public void run() {
                   imageView.setImageBitmap(bitmap);
                   if (br.getRequestListener() != null) {
                       RequestListener listener = br.getRequestListener();
                       listener.onSuccess(bitmap);
                   }
               }
           });
       }
    }

    private Bitmap findBitmap(BitmapRequest br) {
       Bitmap bitmap = downloadBitmap(br.getUrl());
       return bitmap;
    }

    private Bitmap downloadBitmap(String uri) {
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(uri);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            inputStream = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    private void showLoadingImage(BitmapRequest br) {
       if (br.getResID() > 0 && br.getImageView() != null) {
           final int resId = br.getResID();
           final ImageView imageView = br.getImageView();
           handler.post(new Runnable() {
               @Override
               public void run() {
                   imageView.setImageResource(resId);
               }
           });
       }
    }
}
