package com.jaden.myglidedemo.myglide;

import java.util.concurrent.LinkedBlockingQueue;

public class RequestManger {

    private static RequestManger requestManger = new RequestManger();

    private LinkedBlockingQueue<BitmapRequest> requestQueue = new LinkedBlockingQueue<>();

    private BitmapDispatcher[] dispatchers;

    private RequestManger() {
        start();
    }

    private void start() {
        stop();
        startAllDispatcher();
    }

    public static RequestManger get() {
        return requestManger;
    }

    public void addBitmapRequest(BitmapRequest bitmapRequest) {
        if (bitmapRequest == null) {
            return;
        }

        if (!requestQueue.contains(bitmapRequest)) {
            requestQueue.add(bitmapRequest);
        }
    }

    public void startAllDispatcher() {
        //获取手机支持的单个应用最大的线程数
        int threndCount = Runtime.getRuntime().availableProcessors();
        dispatchers = new BitmapDispatcher[threndCount];
        for (int i = 0; i < threndCount; i++) {
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(requestQueue);
            bitmapDispatcher.start();
            dispatchers[i] = bitmapDispatcher;
        }
    }

    private void stop() {
        if (dispatchers != null && dispatchers.length > 0) {
            for (BitmapDispatcher dispatcher : dispatchers) {
                if (!dispatcher.isInterrupted()) {
                    dispatcher.interrupt();
                }
            }
        }
    }
}