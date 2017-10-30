package com.example.tom.memorygamecustom;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tom on 18/10/2017.
 */

public class ImagesManager {
   public static final  ImagesManager imgs = new ImagesManager();
     public Handler handler;
     public ThreadPoolExecutor imagesThreadPool;
    private static int  CORE_NUMBER = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        private final BlockingQueue<Runnable> WorkQueue;
    private ImagesManager(){
    handler=new Handler(Looper.getMainLooper());
        WorkQueue= new LinkedBlockingQueue<Runnable>();
    imagesThreadPool= new ThreadPoolExecutor(CORE_NUMBER,CORE_NUMBER,KEEP_ALIVE_TIME,KEEP_ALIVE_TIME_UNIT,WorkQueue);
    };
    public void startCopyingImages(Thread t,final Button chosenView1, final String filesPath){

        imagesThreadPool.execute(t);

    }


}
